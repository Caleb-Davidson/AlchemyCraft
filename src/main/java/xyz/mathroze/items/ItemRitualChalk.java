package xyz.mathroze.items;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.init.Blocks;

/**
 * Created by caleb on 5/25/17.
 */
public class ItemRitualChalk extends Item {

    private static final Block blockRitualSymbol = Blocks.blockRitualSymbol;
    public ItemRitualChalk(String unlocalizedName) {
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MOD_ID, unlocalizedName));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (stack.stackSize != 0 && playerIn.canPlayerEdit(pos, facing, stack) && blockRitualSymbol.canPlaceBlockAt(worldIn, pos.up())) {
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos.add(0, 1, 0), blockRitualSymbol.getDefaultState());
                stack.damageItem(1, playerIn);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
}
