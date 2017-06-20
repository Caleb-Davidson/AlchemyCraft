package xyz.mathroze.alchemycraft.plugins.waila;


import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 6/20/17.
 */
public class Waila {

    public static void register() {
        if (Loader.isModLoaded("Waila")) {
            Log.verbose("Registering Waila plugin");
            FMLInterModComms.sendMessage("Waila", "register", "xyz.mathroze.alchemycraft.plugins.waila.WailaCompatibility.load");
        }
    }
}
