package xyz.mathroze.alchemycraft.rituals;

import net.minecraftforge.fluids.FluidStack;

/**
 * Created by caleb on 5/30/17.
 */
public class RitualNone extends Ritual {

    RitualNone() {
        super(1, 0, null, null, true, null, null);
    }

    @Override
    public boolean canProceed(FluidStack fluid) {
        return false;
    }
}
