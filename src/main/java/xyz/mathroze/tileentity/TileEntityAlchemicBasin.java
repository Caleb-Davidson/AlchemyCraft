package xyz.mathroze.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.blocks.BlockAlchemicBasin;
import xyz.mathroze.utils.Log;

/**
 * Created by caleb on 5/16/17.
 */
public class TileEntityAlchemicBasin extends TileEntity implements ITickable, ICapabilityProvider {

    public static final String TILE_ENTITY_ID = References.MOD_ID + ":alchemicBasin";

    private static String NBT_PROGRESS = "Progress";
    private static String NBT_TANK = "Tank";

    private int progress;
    private FluidTank fluidTank;
    private BlockAlchemicBasin parentBlock;

    public TileEntityAlchemicBasin(BlockAlchemicBasin parentBlock) {
        this.progress = 0;
        this.fluidTank = new FluidTank(10000);
        this.parentBlock = parentBlock;
    }

    @Override
    public void update() {
        progress = (progress + 1) % 100;
//        Log.verbose("I've been updated!");
    }

    public void interact(EntityPlayer player, World world, BlockPos pos) {
        if (world.isRemote)
            return;
        Log.verbose("Checking for capability");
        IFluidHandler capability;
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
            capability = player.getHeldItemMainhand().getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        else
            return;
        Log.verbose("Found capability");

        if (player.getHeldItemMainhand().hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            Log.verbose("Checking if container has fluid");
            FluidStack fluid = capability.drain(1000, false);
            if (fluid == null) {
                Log.verbose("Attempting to fill container");
                int potFill = capability.fill(fluidTank.drain(1000, false), false);
                Log.verbose("Container will accept " + potFill + "mb of available " + fluidTank.getFluidAmount() + "mb");
                if (potFill == 1000) {
                    Log.verbose("Performing Drain");
                    capability.fill(fluidTank.drain(1000, true), true);
                    Log.verbose("FluidTank now at " + fluidTank.getFluidAmount() + " / " + fluidTank.getCapacity());
                    setParentBlockState(world, pos, fluidTank.getFluidAmount() / 1000);
                    markDirty();
                }
            }
            else {
                Log.verbose("Attempting to drain container");
                Log.verbose("Checking if fluid is compatible");
                if (fluidTank.getFluid() == null || fluidTank.getFluid().isFluidEqual(fluid)) {
                    Log.verbose("Filling " + fluid.amount + "mb");
                    if (fluid.amount == 1000 && fluidTank.getFluidAmount() + fluid.amount <= fluidTank.getCapacity()) {
                        fluidTank.fill(capability.drain(1000, true), true);
                        Log.verbose("FluidTank now at " + fluidTank.getFluidAmount() + " / " + fluidTank.getCapacity() + "mb");
                        setParentBlockState(world, pos, fluidTank.getFluidAmount() / 1000);
                        markDirty();
                    }
                }
            }
        }
    }

    private void setParentBlockState(World world, BlockPos pos, int state) {
        Log.verbose("Setting parentBlock: " + parentBlock.getUnlocalizedName() + " at position " + pos.toString() + " to state " + state);
        world.setBlockState(pos, parentBlock.getDefaultState().withProperty(parentBlock.LEVEL, state));
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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
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

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock() || oldState == newSate;
    }
}
