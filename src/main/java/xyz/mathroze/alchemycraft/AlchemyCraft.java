package xyz.mathroze.alchemycraft;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.relauncher.Side;
import xyz.mathroze.alchemycraft.init.*;
import xyz.mathroze.alchemycraft.network.AlchemyCraftPacketHandler;
import xyz.mathroze.alchemycraft.network.UpdateAlchemicBasinMessage;
import xyz.mathroze.alchemycraft.proxy.CommonProxy;
import xyz.mathroze.alchemycraft.utils.Log;

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
    static {
        FluidRegistry.enableUniversalBucket(); // Must be called before preInit
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Log.verbose("\n***** Initializing AlchemyCraft *****\n" +
                "ModID: " + References.MOD_ID + "\n" +
                "ModName: " + References.MOD_NAME + "\n" +
                "Version: " + References.VERSION + "\n" +
                "**************************************");

        Log.debug("Beginning pre-init phase");

        Log.verbose("Initializing Fluids");
        Fluids.register();

        Log.verbose("Initializing Blocks");
        Blocks.init();
        Log.verbose("Registering Blocks");
        Blocks.registerBlocks();

        Log.verbose("Initializing Items");
        Items.init();
        Log.verbose("Registering Items");
        Items.registerItems();

        Log.verbose("Registering Renders");
        proxy.registerRenders();
        Log.verbose("Registering Tile Entities");
        proxy.registerTileEntities();
        Log.verbose("Registering Plugins");
        proxy.registerPlugins();

        OreDictionaries.registerOreDictionaries();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Recipes.registerCraftingRecipes();
        AlchemyCraftPacketHandler.INSTANCE.registerMessage(UpdateAlchemicBasinMessage.UpdateAlchemicBasinMessageHandler.class, UpdateAlchemicBasinMessage.class, 0, Side.CLIENT);
    }
}
