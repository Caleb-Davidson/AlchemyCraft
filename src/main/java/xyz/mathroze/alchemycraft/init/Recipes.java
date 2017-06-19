package xyz.mathroze.alchemycraft.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 6/16/17.
 */
public class Recipes {

    public static void registerCraftingRecipes() {
        Log.verbose("Registering Crafting Recipes");

        GameRegistry.addRecipe(new ItemStack(Items.itemRitualChalk), new Object[] {"G  ", " B ", "  G", 'G', net.minecraft.init.Items.GOLD_NUGGET, 'B', net.minecraft.init.Items.BLAZE_ROD});
        GameRegistry.addRecipe(new ItemStack(Items.itemPhilosophersStone), new Object[] {"RGR", "GDG", "RGR", 'R', net.minecraft.init.Items.REDSTONE, 'G', net.minecraft.init.Items.GLOWSTONE_DUST, 'D', net.minecraft.init.Items.DIAMOND});
        GameRegistry.addRecipe(new ItemStack(Blocks.blockAlchemicBasin), new Object[] {"I I", "I I", "IBI", 'I', net.minecraft.init.Items.IRON_INGOT, 'B', net.minecraft.init.Blocks.IRON_BLOCK});
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.blockPlantFiber), new Object[] {"LLL", "LLL", "LLL", 'L', "plantFiber"}));
    }
}
