package xyz.mathroze.alchemycraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.items.ItemPhilosophersStone;
import xyz.mathroze.alchemycraft.network.AlchemyCraftPacketHandler;
import xyz.mathroze.alchemycraft.network.UpdateAlchemicBasinMessage;
import xyz.mathroze.alchemycraft.rituals.Ritual;
import xyz.mathroze.alchemycraft.rituals.RitualFactory;
import xyz.mathroze.alchemycraft.utils.Log;

import java.util.Random;

/**
 * Created by caleb on 5/16/17.
 */
public class TileEntityAlchemicBasin extends TileEntity implements ITickable, ICapabilityProvider {

    public static final String TILE_ENTITY_ID = References.MOD_ID + ":alchemicBasin";
    public static final int CAPACITY = 10000;
    private static String NBT_PROGRESS = "Progress";
    private static String NBT_TANK = "Tank";
    private static Random rand = new Random();

    public FluidTank fluidTank;
    private int progress;
    private Ritual ritual;

    public TileEntityAlchemicBasin() {
        this.progress = 0;
        this.fluidTank = new FluidTank(CAPACITY);
        this.ritual = RitualFactory.getRitual(null);
    }

    @Override
    public void update() {
        if (ritual.canProceed(fluidTank.getFluid())) {
            ///// Ritual is proceeding /////

            progress = (progress + 1) % ritual.getTransformationTicks();
            spawnParticles((float) progress / ritual.getTransformationTicks());
            fluidTank.drain(ritual.getDrainAmount(), true);

            if (progress == 0) {
                ///// Ritual finished /////

                world.setBlockState(pos.up(), ritual.getEndBlockResult().getDefaultState());
                if (!ritual.isBlockInfusion())
                    fluidTank.setFluid(new FluidStack(ritual.getEndFluidResult(), fluidTank.getFluidAmount()));
                ritual = RitualFactory.getRitual(null);
                if (!world.isRemote)
                    AlchemyCraftPacketHandler.INSTANCE.sendToAllAround(
                            new UpdateAlchemicBasinMessage(fluidTank.getFluid(), pos),
                            new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128)
                    );
            }
            markDirty();

        } else if (progress != 0) {
            ///// Ritual was cancelled /////

            progress = 0;
            ritual = RitualFactory.getRitual(null);
            markDirty();
        } else {
            ///// No ritual /////

        }
    }

    public void interact(EntityPlayer player, World world, BlockPos pos) {
        ///// Attempting to start a ritual /////
        Log.verbose("Checking for Philosophers Stone");
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemPhilosophersStone) {
            Log.verbose("Found one! Getting ritual for: " + world.getBlockState(pos.down()).getBlock().getUnlocalizedName());
            ritual = RitualFactory.getRitual(world.getBlockState(pos.down()).getBlock());
            ritual.setPosition(pos, world);
            progress = 0;
            if (!ritual.canProceed(fluidTank.getFluid()))
                ritual = RitualFactory.getRitual(null);
            return;
        }

        ///// Interacting with Tank /////
        Log.verbose("Checking for capability");
        IFluidHandler capability;
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
            capability = player.getHeldItemMainhand().getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        else
            return;
        Log.verbose("Found capability");

        Log.verbose("Checking if container has fluid");
        FluidStack fluid = capability.drain(remainingCapacity(), false);

        ///// Fill the other container /////
        if (fluid == null) {
            Log.verbose("Attempting to fill container");
            int potFill = capability.fill(fluidTank.drain(fluidTank.getFluidAmount(), false), false);
            Log.verbose("Container will accept " + potFill + "mb of available " + fluidTank.getFluidAmount() + "mb");
            Log.verbose("Performing Drain");
            capability.fill(fluidTank.drain(potFill, true), true);
            Log.verbose("FluidTank now at " + fluidTank.getFluidAmount() + " / " + fluidTank.getCapacity());
            if (!world.isRemote)
                markDirty();
        }

        ///// Drain the other container /////
        else {
            Log.verbose("Attempting to drain container");
            Log.verbose("Checking if fluid is compatible");
            if (fluidTank.getFluid() == null || fluidTank.getFluid().isFluidEqual(fluid)) {
                Log.verbose("Filling " + fluid.amount + "mb");
                fluidTank.fill(capability.drain(fluid, true), true);
                Log.verbose("FluidTank now at " + fluidTank.getFluidAmount() + " / " + fluidTank.getCapacity() + "mb");
                if (!world.isRemote)
                    markDirty();
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.progress = nbt.getInteger(NBT_PROGRESS);
        Log.verbose("Reading tank NBT tag with fluid: " + nbt.getCompoundTag(NBT_TANK).getString("FluidName") +
                " and amount: " + nbt.getCompoundTag(NBT_TANK).getInteger("Amount"));
        fluidTank.readFromNBT(nbt.getCompoundTag(NBT_TANK));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);

        nbt.setInteger(NBT_PROGRESS, this.progress);
        NBTTagCompound tankNBT = new NBTTagCompound();
        fluidTank.writeToNBT(tankNBT);
        Log.verbose("Writing tank NBT tag with fluid: " + tankNBT.getString("FluidName") +
            "and amount: " + tankNBT.getInteger("Amount"));
        nbt.setTag(NBT_TANK, tankNBT);

        return nbt;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) fluidTank;
        return super.getCapability(capability, facing);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return new SPacketUpdateTileEntity(this.pos, metadata, nbt);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    private void spawnParticles(float progressPercentage) {
        double particles = Math.max(progressPercentage * 3, 1.0);
        particles *= particles;
        for (int i = 0; i < particles; i ++) {
            float xOffset = rand.nextFloat() * 3 - 1.5f;
            float zOffset = rand.nextFloat() * 3 - 1.5f;
            float yOffset = rand.nextFloat() * 3 - 1.5f;
            world.spawnParticle(
                    EnumParticleTypes.ENCHANTMENT_TABLE,
                    pos.getX() + 0.5,
                    pos.getY() + 1 + yOffset,
                    pos.getZ() + 0.5,
                    xOffset * (1 + progressPercentage),
                    0,
                    zOffset * (1 + progressPercentage)
            );
        }
    }

    private int remainingCapacity() {
        return CAPACITY - fluidTank.getFluidAmount();
    }

    public float ritualProgress() {
        return ((float)progress / ritual.getTransformationTicks()) * 100;
    }

    public boolean ritualInProgress() {
        return progress != 0;
    }
}
