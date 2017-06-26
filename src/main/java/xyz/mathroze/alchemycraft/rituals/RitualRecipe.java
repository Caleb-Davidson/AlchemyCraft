package xyz.mathroze.alchemycraft.rituals;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by caleb on 6/6/17.
 */
public class RitualRecipe {

    public ItemStack inputItemStackOne;
    public ItemStack inputItemStackTwo;
    public FluidStack inputFluidStack;
    public ItemStack outputItemStack;
    public FluidStack outputFluidStack;
    public boolean isFluidInfusion;

    public RitualRecipe(Ritual ritual) {
        inputItemStackOne = new ItemStack(ritual.ritualBlocks.getBlockAt(0, 1, 0));
        inputItemStackTwo = new ItemStack(ritual.ritualBlocks.getBlockAt(0, -1, 0));
        inputFluidStack = ritual.neededFluid;
        outputItemStack = new ItemStack(ritual.endBlockResult);
        if (ritual.endFluidResult != null)
            outputFluidStack = new FluidStack(ritual.endFluidResult, 1);
        isFluidInfusion = ritual.isFluidInfusion;
    }
}
