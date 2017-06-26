package xyz.mathroze.alchemycraft.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.rituals.Ritual;
import xyz.mathroze.alchemycraft.rituals.RitualFactory;
import xyz.mathroze.alchemycraft.tileentity.TileEntityRitualController;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 6/5/17.
 */
public class ItemPhilosophersStone extends Item {

    public ItemPhilosophersStone(String unlocalizedName) {
        this.setMaxStackSize(1);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MOD_ID, unlocalizedName));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity != null && entity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
            Log.verbose("Found te with Fluid! Getting ritual for: " + world.getBlockState(pos.down()).getBlock().getUnlocalizedName());
            Ritual ritual = RitualFactory.getRitual(world.getBlockState(pos.down()).getBlock());
            ritual.setPosition(pos, world);
            world.setTileEntity(player.getPosition(), new TileEntityRitualController(ritual));
            return EnumActionResult.SUCCESS;
        }
        else {
            return EnumActionResult.FAIL;
        }
    }
}
