package xyz.mathroze.rituals;

import net.minecraftforge.fluids.FluidStack;

/**
 * Created by caleb on 5/30/17.
 */
public class RitualNone extends Ritual {


    @Override
    public boolean canProceed(FluidStack fluid) {
        return false;
    }
}
