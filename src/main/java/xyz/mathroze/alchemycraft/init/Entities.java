package xyz.mathroze.alchemycraft.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.mathroze.alchemycraft.tileentity.TileEntityAlchemicBasin;
import xyz.mathroze.alchemycraft.tileentity.TileEntityRitualController;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 6/23/17.
 */
public class Entities {

    public static void registerEntities() {
        Log.verbose("Registering Tile Entities");
        registerTileEntities();
    }

    private static void registerTileEntities() {
        Log.verbose("Registering Tile Entity: " + TileEntityAlchemicBasin.TILE_ENTITY_ID);
        GameRegistry.registerTileEntity(TileEntityAlchemicBasin.class, TileEntityAlchemicBasin.TILE_ENTITY_ID);

        Log.verbose("Registering Tile Entity: " + TileEntityRitualController.TILE_ENTITY_ID);
        GameRegistry.registerTileEntity(TileEntityRitualController.class, TileEntityRitualController.TILE_ENTITY_ID);
    }
}
