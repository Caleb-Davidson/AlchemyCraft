package xyz.mathroze.rituals;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import xyz.mathroze.utils.Log;

/**
 * Created by caleb on 5/30/17.
 */
public class Ritual {

    Ritual (int transformationTicks, int drainAmount, FluidStack neededFluid, BlockArea blockArea, boolean isBlockInfusion, Block endBlockResult, Fluid endFluidResult) {
        this.transformationTicks = transformationTicks;
        this.drainAmount = drainAmount;
        this.neededFluid = neededFluid;
        this.blockArea = blockArea;
        this.isBlockInfusion = isBlockInfusion;
        this.endBlockResult = endBlockResult;
        this.endFluidResult = endFluidResult;
    }

    private BlockPos epicenter;
    private World world;
    private FluidStack neededFluid;
    private BlockArea blockArea;
    private int drainAmount;
    private int transformationTicks;
    private boolean isBlockInfusion;
    private Block endBlockResult;
    private Fluid endFluidResult;

    public boolean canProceed(FluidStack withFluid) {
        boolean canProceed = withFluid != null && withFluid.amount >= drainAmount;
        if (canProceed) {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        canProceed = canProceed && blockArea.isNeededBlockAtPosition(x, y, z, world.getBlockState(epicenter.add(x, y, z)).getBlock());
                        if (!canProceed) {
                            Log.verbose("Ritual did not match block: " + world.getBlockState(epicenter.add(x, y, z)).getBlock().getUnlocalizedName() + " at position x: " + x + " y: " + y + " z: " + z);
                            x = y = z = 2;
                        }
                    }
                }
            }
        }
        return canProceed;
    }

    public void setPosition(BlockPos pos, World world) {
        this.epicenter = pos;
        this.world = world;
    }

    public int getDrainAmount() {
        return drainAmount;
    }

    public int getTransformationTicks() {
        return transformationTicks;
    }

    public boolean isBlockInfusion() {
        return isBlockInfusion;
    }

    public Block getEndBlockResult() {
        return endBlockResult;
    }

    public Fluid getEndFluidResult() {
        return endFluidResult;
    }
}
