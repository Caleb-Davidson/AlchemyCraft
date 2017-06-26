package xyz.mathroze.alchemycraft.plugins.jei;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.init.Blocks;
import xyz.mathroze.alchemycraft.plugins.jei.rituals.RitualRecipeCategory;
import xyz.mathroze.alchemycraft.plugins.jei.rituals.RitualRecipeHandler;
import xyz.mathroze.alchemycraft.rituals.Ritual;
import xyz.mathroze.alchemycraft.rituals.RitualFactory;
import xyz.mathroze.alchemycraft.rituals.RitualRecipe;
import xyz.mathroze.alchemycraft.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caleb on 6/6/17.
 */
@JEIPlugin
public class JEIAlchemyCraftPlugin extends BlankModPlugin {

    @Override
    public void register(IModRegistry registry) {
        Log.verbose("Registering JEI Plugin");
        IJeiHelpers helpers = registry.getJeiHelpers();

        registry.addRecipeCategories(
                new RitualRecipeCategory(helpers.getGuiHelper())
        );

        registry.addRecipeHandlers(
                new RitualRecipeHandler()
        );

//        List list = new ArrayList();
//        list.add(new RitualRecipe(RitualFactory.getRitual(Block.getBlockFromName("gold_block"))));
//        list.add(new RitualRecipe(RitualFactory.getRitual(Block.getBlockFromName("leaves"))));

        List<RitualRecipe> list = new ArrayList<RitualRecipe>();
        for (Ritual ritual : RitualFactory.getAllRituals()) {
            list.add(new RitualRecipe(ritual));
        }
        registry.addRecipes(
                list
        );

        registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.blockAlchemicBasin), References.RITUAL_RECIPE_CATEGORY_NAME);
    }
}
