package xyz.mathroze.rituals;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xyz.mathroze.blocks.BlockAlchemicBasin;
import xyz.mathroze.init.Blocks;

/**
 * Created by caleb on 5/30/17.
 */
public class RitualIronToGold extends Ritual {

    RitualIronToGold() {
        transformationTicks = 400;
        drainAmount = 10;
        endResult = net.minecraft.init.Blocks.GOLD_BLOCK;
        fluid = FluidRegistry.getFluidStack("water", 1);
        blockArea = new BlockArea();
        blockArea.setBlocks(new Block[][][]{
                {
                        {null, null, null},
                        {null, Block.getBlockFromName("gold_block"), null},
                        {null, null, null}
                },
                {
                        {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol")},
                        {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:alchemicBasin"), Block.getBlockFromName("alchemycraft:ritualSymbol")},
                        {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol")}
                },
                {
                        {null, null, null},
                        {null, Block.getBlockFromName("iron_block"), null},
                        {null, null, null}
                }
        });
    }

    @Override
    public boolean canProceed(FluidStack fluid) {
        boolean canProceed = fluid != null && fluid.amount >= drainAmount;
        if (canProceed) {
            for (int y = -1; y <= 1; y++) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        canProceed = canProceed && blockArea.isNeededBlockAtPosition(x, y, z, world.getBlockState(epicenter.add(x, y, z)).getBlock());
                    }
                }
            }
        }
        return canProceed;

//        return world.getBlockState(epicenter.up()).getBlock() == net.minecraft.init.Blocks.IRON_BLOCK &&
//                world.getBlockState(epicenter.down()).getBlock() == net.minecraft.init.Blocks.GOLD_BLOCK &&
//                world.getBlockState(epicenter.north()).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.south()).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.east()).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.west()).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.add(1, 0, 1)).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.add(-1, 0, 1)).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.add(1, 0, -1)).getBlock() == Blocks.blockRitualSymbol &&
//                world.getBlockState(epicenter.add(-1, 0, -1)).getBlock() == Blocks.blockRitualSymbol &&
//                this.fluid.isFluidEqual(fluid) && fluid != null && fluid.amount >= drainAmount;
    }
}
