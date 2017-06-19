package xyz.mathroze.alchemycraft.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by caleb on 6/5/17.
 */
public class FluidAlchemicSlurry extends Fluid {

    public FluidAlchemicSlurry(String unlocalizedName) {
        super(unlocalizedName, new ResourceLocation("alchemycraft:blocks/alchemicSlurry_still"), new ResourceLocation("alchemycraft:blocks/alchemicSlurry_flow"));
    }

    @Override
    public int getColor() {
        return 0xFF00FF00;
    }
}
