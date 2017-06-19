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
//    public FluidStack outputFluidStack;

    public RitualRecipe(Ritual ritual) {
        inputItemStackOne = new ItemStack(ritual.blockArea.getBlockAt(0, 1, 0));
        inputItemStackTwo = new ItemStack(ritual.blockArea.getBlockAt(0, -1, 0));
        inputFluidStack = ritual.getNeededFluid();
        outputItemStack = new ItemStack(ritual.getEndBlockResult());

//        outputFluidStack = new FluidStack(ritual.getEndFluidResult(), 0);
    }
}
