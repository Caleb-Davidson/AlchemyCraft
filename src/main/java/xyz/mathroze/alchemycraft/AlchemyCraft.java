package xyz.mathroze.alchemycraft;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = AlchemyCraft.MOD_ID,
        name = AlchemyCraft.MOD_NAME,
        version = AlchemyCraft.VERSION
)
public class AlchemyCraft {

    public static final String MOD_ID = "alchemycraft";
    public static final String MOD_NAME = "AlchemyCraft";
    public static final String VERSION = "1.10.2-1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }
}
