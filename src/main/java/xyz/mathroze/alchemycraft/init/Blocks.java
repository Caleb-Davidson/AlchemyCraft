package xyz.mathroze.alchemycraft.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.blocks.BlockAlchemicBasin;
import xyz.mathroze.alchemycraft.blocks.BlockPlantFiber;
import xyz.mathroze.alchemycraft.blocks.BlockRitualSymbol;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 5/16/17.
 */
public class Blocks {

    public static Block blockAlchemicBasin;
    public static Block blockRitualSymbol;
    public static Block blockPlantFiber;

    public static void init() {
        Log.debug("Initializing Blocks");
        blockAlchemicBasin = new BlockAlchemicBasin("alchemicBasin");
        blockRitualSymbol = new BlockRitualSymbol("ritualSymbol");
        blockPlantFiber = new BlockPlantFiber("plantFiber");
    }

    public static void registerBlocks() {
        Log.debug("Registering Blocks");
        registerBlock(blockAlchemicBasin);
        registerBlock(blockRitualSymbol);
        registerBlock(blockPlantFiber);
    }

    private static void registerBlock(Block block) {
        Log.verbose("Registering Block: " + block.getUnlocalizedName());
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        Log.debug("Registering Block Renders");
        registerRender(blockAlchemicBasin);
        registerRender(blockRitualSymbol);
        registerRender(blockPlantFiber);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block) {
        Log.verbose("Registering Render For Block: " + block.getUnlocalizedName());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(new ResourceLocation(References.MOD_ID, block.getUnlocalizedName().substring(5)),
                "inventory"));
    }
}
