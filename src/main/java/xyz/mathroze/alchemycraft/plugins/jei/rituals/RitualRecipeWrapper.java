package xyz.mathroze.alchemycraft.plugins.jei.rituals;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.mathroze.alchemycraft.rituals.RitualRecipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caleb on 6/6/17.
 */
public class RitualRecipeWrapper extends BlankRecipeWrapper {

    RitualRecipe theRecipe;

    public RitualRecipeWrapper (RitualRecipe theRecipe) {
        this.theRecipe = theRecipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {

        List list = new ArrayList();
        list.add(theRecipe.inputItemStackOne);
        list.add(theRecipe.inputItemStackTwo);
        ingredients.setInputs(ItemStack.class, list);
        ingredients.setInput(FluidStack.class, theRecipe.inputFluidStack);
//        if (theRecipe.outputItemStack != null)
            ingredients.setOutput(ItemStack.class, theRecipe.outputItemStack);
//        else
//            ingredients.setOutput(FluidStack.class, theRecipe.outputFluidStack);
    }
}
