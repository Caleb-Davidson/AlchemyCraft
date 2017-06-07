package xyz.mathroze.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.tileentity.TileEntityAlchemicBasin;
import xyz.mathroze.utils.Log;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by caleb on 5/16/17.
 */
public class BlockAlchemicBasin extends Block implements ITileEntityProvider {


    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 10);

    public BlockAlchemicBasin(String unlocalizedName) {
        super(Material.IRON);
        Log.verbose("Initializing BlockAlchemicBasin with unlocalized name: " + unlocalizedName);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MOD_ID, unlocalizedName));
        this.setHardness(2);
        this.setResistance(10);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityAlchemicBasin) {
            ((TileEntityAlchemicBasin) te).interact(player, world, pos);
            return true;
        }
        return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        TileEntityAlchemicBasin entity = new TileEntityAlchemicBasin();
        //entity.setParentBlock(this);
        return entity;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{LEVEL});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LEVEL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LEVEL, meta);
    }
}
