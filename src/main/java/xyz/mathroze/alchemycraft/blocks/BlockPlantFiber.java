package xyz.mathroze.alchemycraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.utils.Log;

import java.util.Random;

/**
 * Created by caleb on 6/6/17.
 */
public class BlockPlantFiber extends Block {

    public BlockPlantFiber(String unlocalizedName) {
        super(Material.LEAVES);
        Log.verbose("Initializing BlockPlantFiber with unlocalized name: " + unlocalizedName);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MOD_ID, unlocalizedName));
        this.setHardness(0.2F);
        this.setHarvestLevel("axe", 0);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }
}
