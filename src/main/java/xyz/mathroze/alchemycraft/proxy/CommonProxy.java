package xyz.mathroze.alchemycraft.proxy;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.mathroze.alchemycraft.plugins.waila.Waila;
import xyz.mathroze.alchemycraft.tileentity.TileEntityAlchemicBasin;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 5/16/17.
 */
public class CommonProxy {

    public void registerRenders() {

    }

    public void registerPlugins() {
        Waila.register();
    }
}
