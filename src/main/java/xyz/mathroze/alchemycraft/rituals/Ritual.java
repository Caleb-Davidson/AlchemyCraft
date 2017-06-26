package xyz.mathroze.alchemycraft.rituals;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xyz.mathroze.alchemycraft.network.AlchemyCraftPacketHandler;
import xyz.mathroze.alchemycraft.network.UpdateRitualFinishedMessage;
import xyz.mathroze.alchemycraft.utils.BlockArea;
import xyz.mathroze.alchemycraft.utils.Log;

import java.util.Random;

/**
 * Created by caleb on 5/30/17.
 */
public class Ritual {

    private static Random rand = new Random();

    public BlockPos epicenter;
    BlockArea ritualBlocks;
    BlockArea tierBlocks;
    FluidStack neededFluid;
    Block endBlockResult;
    Fluid endFluidResult;
    boolean isFluidInfusion;
    private World world;
    private int drainPerTick;
    private int progress;
    private int transformationTicks;
    private int tier;
    private boolean completed;

    public Ritual (int transformationTicks, int drainPerTick, Fluid neededFluid, int tier, BlockArea ritualBlocks, boolean isFluidInfusion, Block endBlockResult, Fluid endFluidResult) {
        this.progress = 0;
        this.completed = false;
        this.isFluidInfusion = isFluidInfusion;
        this.transformationTicks = transformationTicks;
        this.drainPerTick = drainPerTick;
        this.neededFluid = neededFluid == null ? null : new FluidStack(neededFluid, Math.max(drainPerTick * transformationTicks, 1));
        this.endBlockResult = endBlockResult;
        this.endFluidResult = endFluidResult;
        this.ritualBlocks = ritualBlocks;
        this.tierBlocks = RitualTiers.getTier(tier);
        this.tier = tier;
    }

    public Ritual(Ritual copyFrom) {
        this.progress = copyFrom.progress;
        this.completed = copyFrom.completed;
        this.isFluidInfusion = copyFrom.isFluidInfusion;
        this.transformationTicks = copyFrom.transformationTicks;
        this.drainPerTick = copyFrom.drainPerTick;
        this.neededFluid = copyFrom.neededFluid;
        this.endBlockResult = copyFrom.endBlockResult;
        this.endFluidResult = copyFrom.endFluidResult;
        this.ritualBlocks = copyFrom.ritualBlocks;
        this.tierBlocks = copyFrom.tierBlocks;
        this.tier = copyFrom.tier;
    }

//    public Ritual(NBTTagCompound nbt) {
//        this.progress = nbt.getInteger("progress");
//        this.completed = nbt.getBoolean("completed");
//        this.isFluidInfusion = nbt.getBoolean("isFluidInfusion");
//        this.transformationTicks = nbt.getInteger("transformationTicks");
//        this.drainPerTick = nbt.getInteger("drainPerTick");
//        this.neededFluid = FluidRegistry.getFluidStack(nbt.getString("neededFluid"), Math.max(drainPerTick * transformationTicks, 1));
//        this.endBlockResult = Block.getBlockFromName(nbt.getString("endBlockResult"));
//        this.endFluidResult = FluidRegistry.getFluid(nbt.getString("endFluidResult"));
//        this.ritualBlocks = Ri;
//        this.tierBlocks = copyFrom.tierBlocks;
//    }

    public void setPosition(BlockPos pos, World world) {
        this.epicenter = pos;
        this.world = world;
    }

    private boolean canProceed(FluidStack withFluid) {
        return withFluid != null &&
                neededFluid.isFluidEqual(withFluid) &&
                withFluid.amount >= drainPerTick &&
                tierBlocks.isInWorldAtPosition(epicenter, world) &&
                ritualBlocks.isInWorldAtPosition(epicenter, world);
    }

    /**
     *
     * @return True if the ritual did proceed, false otherwise
     */
    public boolean proceed() {
        if (completed)
            return false;

        TileEntity entity = world.getTileEntity(epicenter);
        IFluidHandler capability;
        if (entity != null && entity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
            capability = entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        else {
            Log.error("Ritual epicenter entity at: " + epicenter.toString() + " does not have a fluid capability.");
            return false;
        }

        if (!canProceed(capability.drain(Integer.MAX_VALUE, false)))
            return false;

        capability.drain(drainPerTick, true);
        progress = (progress + 1) % transformationTicks;
        spawnParticles((float) progress / transformationTicks);

        ///// Ritual is finished /////
        if (progress == 0) {
            completed = true;
            world.setBlockState(epicenter.up(), endBlockResult.getDefaultState());
            if (isFluidInfusion) {
                if (capability.drain(Integer.MAX_VALUE, false) != null)
                    capability.fill(
                        new FluidStack(endFluidResult, capability.drain(Integer.MAX_VALUE, true).amount),
                        true
                    );
            }
            if (!world.isRemote)
                AlchemyCraftPacketHandler.INSTANCE.sendToAllAround(
                        new UpdateRitualFinishedMessage(capability.drain(Integer.MAX_VALUE, false), epicenter),
                        new NetworkRegistry.TargetPoint(world.provider.getDimension(), epicenter.getX(), epicenter.getY(), epicenter.getZ(), 128)
                );
        }
        return true;
    }

    private void spawnParticles(float progressPercentage) {
        double particles = Math.max(progressPercentage * 3, 1.0);
        particles *= particles;
        for (int i = 0; i < particles; i ++) {
            float xOffset = rand.nextFloat() * 3 - 1.5f;
            float yOffset = rand.nextFloat() * 3 - 1.5f;
            float zOffset = rand.nextFloat() * 3 - 1.5f;
            world.spawnParticle(
                    EnumParticleTypes.ENCHANTMENT_TABLE,
                    epicenter.getX() + 0.5,
                    epicenter.getY() + 1 + yOffset,
                    epicenter.getZ() + 0.5,
                    xOffset * (1 + progressPercentage),
                    0,
                    zOffset * (1 + progressPercentage)
            );
        }
    }

    public float progress() {
        return ((float)progress / transformationTicks) * 100;
    }

    public Block getFocusBlock() {
        return ritualBlocks.getBlockAt(0, -1, 0);
    }

//    public NBTTagCompound serializeToNBT() {
//        NBTTagCompound nbt = new NBTTagCompound();
//        nbt.setInteger("progress", progress);
//        nbt.setBoolean("completed", completed);
//        nbt.setBoolean("isFluidInfusion", isFluidInfusion);
//        nbt.setInteger("transformationTicks", transformationTicks);
//        nbt.setInteger("drainPerTick", drainPerTick);
//        nbt.setString("neededFluid", neededFluid.getUnlocalizedName());
//        nbt.setString("endBlockResult", endBlockResult.getUnlocalizedName());
//        nbt.setString("focusBlock", getFocusBlock().getUnlocalizedName());
//        nbt.setInteger("tier", tier);
//        return nbt;
//    }
}
