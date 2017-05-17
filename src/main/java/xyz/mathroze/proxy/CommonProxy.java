package xyz.mathroze.proxy;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.mathroze.tileentity.TileEntityAlchemicBasin;
import xyz.mathroze.utils.Log;

/**
 * Created by caleb on 5/16/17.
 */
public class CommonProxy {

    public void registerRenders() {

    }

    public void registerTileEntities() {
        Log.verbose("Registering Tile Entity: " + TileEntityAlchemicBasin.TILE_ENTITY_ID);
        GameRegistry.registerTileEntity(TileEntityAlchemicBasin.class, TileEntityAlchemicBasin.TILE_ENTITY_ID);
    }
}
