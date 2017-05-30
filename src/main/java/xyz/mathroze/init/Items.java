package xyz.mathroze.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.items.ItemRitualChalk;
import xyz.mathroze.utils.Log;

/**
 * Created by caleb on 5/25/17.
 */
public class Items {

    public static Item itemChalk;

    public static void init() {
        itemChalk = new ItemRitualChalk("ritualChalk");
    }

    public static void registerItems() {
        registerItem(itemChalk);
    }

    private static void registerItem(Item item) {
        Log.verbose("Registering item: " + item.getUnlocalizedName());
        GameRegistry.register(item);
    }

    @SideOnly(Side.CLIENT)
    public static void RegisterRenders() {
        registerRender(itemChalk);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item) {
        registerRender(item, 0, item.getUnlocalizedName().substring(5));
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Item item, int metadata, String filename) {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(
                new ResourceLocation(
                        References.MOD_ID, filename), "inventory"));
        Log.verbose("Registered Render for " + item.getUnlocalizedName());
    }
}
