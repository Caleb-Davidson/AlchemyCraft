package xyz.mathroze.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import xyz.mathroze.alchemycraft.References;

/**
 * Created by caleb on 6/5/17.
 */
public class ItemPhilosophersStone extends Item {

    public ItemPhilosophersStone(String unlocalizedName) {
        this.setMaxStackSize(1);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MOD_ID, unlocalizedName));
    }
}
