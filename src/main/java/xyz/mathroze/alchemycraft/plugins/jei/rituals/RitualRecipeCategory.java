package xyz.mathroze.alchemycraft.plugins.jei.rituals;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.util.ResourceLocation;
import xyz.mathroze.alchemycraft.References;

/**
 * Created by caleb on 6/6/17.
 */
public class RitualRecipeCategory extends BlankRecipeCategory<RitualRecipeWrapper> {

    static IDrawable background;
    static IDrawable fluidOverlay;

    public RitualRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/furnace.png"),
                5, 5, 165, 75);
        fluidOverlay = guiHelper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/enchanting_table.png"),
                80, 14, 20, 60);
    }

    @Override
    public String getUid() {
        return References.RITUAL_RECIPE_CATEGORY_NAME;
    }

    @Override
    public String getTitle() {
        return "Alchemic Ritual";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RitualRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 50, 12);
        recipeLayout.getItemStacks().set(0, recipeWrapper.theRecipe.inputItemStackOne);

        recipeLayout.getItemStacks().init(1, true, 50, 48);
        recipeLayout.getItemStacks().set(1, recipeWrapper.theRecipe.inputItemStackTwo);

        recipeLayout.getFluidStacks().init(2, true, 10, 5, 20, 60, 10000, true, null);
        recipeLayout.getFluidStacks().set(2, recipeWrapper.theRecipe.inputFluidStack);

        recipeLayout.getItemStacks().init(3, false, 110, 29);
        recipeLayout.getItemStacks().set(3, recipeWrapper.theRecipe.outputItemStack);
    }
}
