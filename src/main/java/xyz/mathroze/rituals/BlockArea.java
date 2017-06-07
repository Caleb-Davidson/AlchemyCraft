package xyz.mathroze.rituals;

import net.minecraft.block.Block;

/**
 * Created by caleb on 6/6/17.
 */
public class BlockArea {
    Block[][][] blocks = new Block[3][3][3];

    BlockArea(Block[][][] blocks) {
        this.blocks = blocks;
    }

    boolean isNeededBlockAtPosition(int x, int y, int z, Block block) {
        return blocks[y + 1][x + 1][z + 1] == null || blocks[y + 1][x + 1][z + 1] == block;
    }
}
