package xyz.mathroze.rituals;

import net.minecraft.block.Block;

/**
 * Created by caleb on 6/5/17.
 */
public class RitualFactory {

    private static Ritual ironToGold = new RitualIronToGold();
    private static Ritual none = new RitualNone();

    public static Ritual getRitual(Block endResult) {
        if (endResult == null)
            return none;
        if (endResult == net.minecraft.init.Blocks.GOLD_BLOCK)
            return ironToGold;
        return none;
    }
}
