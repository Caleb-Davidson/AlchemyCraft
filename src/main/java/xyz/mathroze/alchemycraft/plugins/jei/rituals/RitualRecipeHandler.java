package xyz.mathroze.alchemycraft.plugins.jei.rituals;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.rituals.RitualRecipe;

/**
 * Created by caleb on 6/6/17.
 */
public class RitualRecipeHandler implements IRecipeHandler<RitualRecipe> {

    @Override
    public Class<RitualRecipe> getRecipeClass() {
        return RitualRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return References.RITUAL_RECIPE_CATEGORY_NAME;
    }

    @Override
    public String getRecipeCategoryUid(RitualRecipe recipe) {
        return References.RITUAL_RECIPE_CATEGORY_NAME;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(RitualRecipe recipe) {
        return new RitualRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(RitualRecipe recipe) {
        return true;
    }
}
