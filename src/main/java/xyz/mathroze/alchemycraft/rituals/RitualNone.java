package xyz.mathroze.alchemycraft.rituals;

/**
 * Created by caleb on 5/30/17.
 */
public class RitualNone extends Ritual {

    RitualNone() {
        super(1, 0, null, 0, null, true, null, null);
    }

    @Override
    public boolean proceed() {
        return false;
    }
}
