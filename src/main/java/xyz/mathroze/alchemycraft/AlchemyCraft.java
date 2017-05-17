package xyz.mathroze.alchemycraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.mathroze.init.Blocks;
import xyz.mathroze.proxy.CommonProxy;
import xyz.mathroze.utils.Log;

@Mod(
        modid = References.MOD_ID,
        name = References.MOD_NAME,
        version = References.VERSION
)
public class AlchemyCraft {

    @SidedProxy(
            serverSide = References.SERVER_PROXY_CLASS,
            clientSide = References.CLIENT_PROXY_CLASS
    )
    static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Log.verbose("\n***** Initializing AlchemyCraft *****\n" +
                "ModID: " + References.MOD_ID + "\n" +
                "ModName: " + References.MOD_NAME + "\n" +
                "Version: " + References.VERSION + "\n" +
                "**************************************");

        Log.debug("Beginning pre-init phase");

        Log.verbose("Initializing Blocks");
        Blocks.init();
        Log.verbose("Registering Blocks");
        Blocks.registerBlocks();

        Log.verbose("Registering Renders");
        proxy.registerRenders();
        Log.verbose("Registering Tile Entities");
        proxy.registerTileEntities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
