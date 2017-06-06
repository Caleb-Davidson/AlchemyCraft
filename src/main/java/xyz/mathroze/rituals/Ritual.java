package xyz.mathroze.rituals;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by caleb on 5/30/17.
 */
public abstract class Ritual {

    class BlockArea {
        Block[][][] blocks = new Block[3][3][3];

        public void setBlocks(Block[][][] blocks) {
            this.blocks = blocks;
        }

        boolean isNeededBlockAtPosition(int x, int y, int z, Block block) {
            return blocks[y + 1][x + 1][z + 1] == null || blocks[y + 1][x + 1][z + 1] == block;
        }
    }

    BlockPos epicenter;
    World world;
    FluidStack fluid;
    BlockArea blockArea;
    public int drainAmount;
    public int transformationTicks;
    public Block endResult;

    public void setPosition(BlockPos pos, World world) {
        this.epicenter = pos;
        this.world = world;
    }

    public abstract boolean canProceed(FluidStack fluid);
}
