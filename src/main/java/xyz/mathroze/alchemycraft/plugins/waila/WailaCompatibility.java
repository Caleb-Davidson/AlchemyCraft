package xyz.mathroze.alchemycraft.plugins.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import xyz.mathroze.alchemycraft.blocks.BlockAlchemicBasin;

/**
 * Created by caleb on 6/20/17.
 */
public class WailaCompatibility {

    public static void load(IWailaRegistrar registrar) {
        RitualProgressProvider provider = new RitualProgressProvider();
        registrar.registerBodyProvider(provider, BlockAlchemicBasin.class);
    }
}
