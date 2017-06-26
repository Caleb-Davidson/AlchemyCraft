package xyz.mathroze.alchemycraft.plugins.jei.rituals;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.mathroze.alchemycraft.References;

/**
 * Created by caleb on 6/6/17.
 */
public class RitualRecipeCategory extends BlankRecipeCategory<RitualRecipeWrapper> {

    static IDrawable background;
    static IDrawableAnimated arrow;

    public RitualRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(new ResourceLocation(References.MOD_ID, "textures/gui/jei_ritual.png"),
              0, 0, 135, 65, 0, 0, 0, 0);
        IDrawableStatic drawableArrow = guiHelper.createDrawable(new ResourceLocation(References.MOD_ID, "textures/gui/jei_ritual.png"),
                135, 0, 18, 18);
        arrow = guiHelper.createAnimatedDrawable(drawableArrow, 30, IDrawableAnimated.StartDirection.LEFT, false);
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
    public void drawAnimations(Minecraft minecraft) {
        arrow.draw(minecraft, 58, 24);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RitualRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 40, 5);
        recipeLayout.getItemStacks().set(0, recipeWrapper.theRecipe.inputItemStackOne);

        recipeLayout.getItemStacks().init(1, true, 40, 42);
        recipeLayout.getItemStacks().set(1, recipeWrapper.theRecipe.inputItemStackTwo);

        recipeLayout.getFluidStacks().init(2, true, 6, 6, 18, 53, 1, false, null);
        recipeLayout.getFluidStacks().set(2, recipeWrapper.theRecipe.inputFluidStack);

        if (recipeWrapper.theRecipe.outputItemStack.getItem() != null) {
            recipeLayout.getItemStacks().init(3, false, 77, 24);
            recipeLayout.getItemStacks().set(3, recipeWrapper.theRecipe.outputItemStack);
        }

        if (recipeWrapper.theRecipe.isFluidInfusion) {
            recipeLayout.getFluidStacks().init(4, false, 111, 6, 18, 53, 1, false, null);
            recipeLayout.getFluidStacks().set(4, recipeWrapper.theRecipe.outputFluidStack);
        }


    }
}
