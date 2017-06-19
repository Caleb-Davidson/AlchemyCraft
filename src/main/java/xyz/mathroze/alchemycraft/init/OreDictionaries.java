package xyz.mathroze.alchemycraft.init;

import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by caleb on 6/16/17.
 */
public class OreDictionaries {

    public static void registerOreDictionaries() {
        registerPlantOreDictionary();
    }

    public static void registerPlantOreDictionary() {
        OreDictionary.registerOre("plantFiber", net.minecraft.init.Blocks.LEAVES);
        OreDictionary.registerOre("plantFiber", net.minecraft.init.Blocks.RED_FLOWER);
    }
}
