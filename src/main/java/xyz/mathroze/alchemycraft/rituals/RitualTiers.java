package xyz.mathroze.alchemycraft.rituals;

import net.minecraft.block.Block;
import xyz.mathroze.alchemycraft.utils.BlockArea;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caleb on 6/20/17.
 */
public class RitualTiers {
    private static Map<Integer, BlockArea> tiers = new HashMap<Integer, BlockArea>();

    public static void addTier(int tier, BlockArea blocks) {
        tiers.put(tier, blocks);
    }

    public static BlockArea getTier(int tier) {
        return tiers.get(tier);
    }
}
