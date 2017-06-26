package xyz.mathroze.alchemycraft.utils;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by caleb on 6/6/17.
 */
public class BlockArea {
    private Block[][][] blocks;

    public BlockArea(Block[][][] blocks) {
        this.blocks = blocks;
    }
    public BlockArea() {
        blocks = new Block[3][3][3];
    }
    public BlockArea(BlockArea copyFrom) {
        blocks = copyFrom.blocks;
    }
    public BlockArea(List<String> blockNames) {
        assert blockNames.size() >= 27;
        blocks = new Block[3][3][3];
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                for (int z = 0; z <= 2; z++) {
                    String name = blockNames.get(y * 9 + x * 3 + z);
                    blocks[2-y][x][z] = name == null ? null : Block.getBlockFromName(name);
//                    if (name == null)
//                        blocks[y][x][z] = null;
//                    else
//                        blocks[y][x][z] = Block.getBlockFromName(name);
                }
            }
        }
    }

    private boolean isNeededBlockAtPosition(int x, int y, int z, Block block) {
        return blocks[y + 1][x + 1][z + 1] == null || blocks[y + 1][x + 1][z + 1] == block;
    }

    public Block getBlockAt(int x, int y, int z) {
        return blocks[y + 1][x + 1][z + 1];
    }

    public void setBlockAt(int x, int y, int z, Block block) {
        blocks[y + 1][x + 1][z + 1] = block;
    }

    public boolean isInWorldAtPosition(BlockPos pos, World world) {
        boolean matches = true;
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    matches = matches && isNeededBlockAtPosition(x, y, z, world.getBlockState(pos.add(x, y, z)).getBlock());
                    if (!matches) {
                        Log.verbose("Block Area did not match block: " + world.getBlockState(pos.add(x, y, z)).getBlock().getUnlocalizedName() + " at position x: " + x + " y: " + y + " z: " + z);
                        x = y = z = 2;
                    }
                }
            }
        }
        return matches;
    }

    @Override
    public int hashCode() {
        int result = 7;
        for (Block[][] block2d: blocks) {
            for (Block[] block1d: block2d) {
                for (Block block: block1d) {
                    if (block != null)
                        result = result * 19 + block.hashCode();
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BlockArea))
            return false;
        BlockArea other = (BlockArea) object;
        boolean matches = true;
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                for (int z = 0; z <= 2; z++) {
                    matches = matches && blocks[y][x][z].equals(other.blocks[y][x][z]);
                    if (!matches)
                        x = y = z = 3;
                }
            }
        }
        return matches;
    }
}
