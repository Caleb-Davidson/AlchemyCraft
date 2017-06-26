package xyz.mathroze.alchemycraft.rituals;

import net.minecraft.block.Block;
import xyz.mathroze.alchemycraft.utils.BlockArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caleb on 6/5/17.
 */
public class RitualFactory {

    private static Ritual none = new RitualNone();
    private static Map<Block, Ritual> rituals = new HashMap<Block, Ritual>();

    public static void addRitual(Ritual ritual) {
        rituals.put(ritual.getFocusBlock(), ritual);
    }

    public static Ritual getRitual(Block focus) {
        Ritual ritual = rituals.get(focus);
        if (ritual == null)
            return none;
        return new Ritual(ritual);
    }

    public static List<Ritual> getAllRituals() {
        return new ArrayList<Ritual>(rituals.values());
    }
}
