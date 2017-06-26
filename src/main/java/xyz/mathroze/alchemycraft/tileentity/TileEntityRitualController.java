package xyz.mathroze.alchemycraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.alchemycraft.rituals.Ritual;
import xyz.mathroze.alchemycraft.rituals.RitualEpicenters;
import xyz.mathroze.alchemycraft.rituals.RitualFactory;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 6/20/17.
 */
public class TileEntityRitualController extends TileEntity implements ITickable {

    public static final String TILE_ENTITY_ID = References.MOD_ID + ":ritualController";
    public Ritual ritual;

    public TileEntityRitualController(Ritual ritual) {
        this.ritual = ritual;
        RitualEpicenters.map.put(ritual.epicenter, 0.0f);
    }

    @Override
    public void update() {
        Log.verbose("Hello from the ritual controller");
        RitualEpicenters.map.put(ritual.epicenter, ritual.progress());
        if (!ritual.proceed()) {
            world.removeTileEntity(pos);
            RitualEpicenters.map.remove(ritual.epicenter);
            invalidate();
        }
    }
}
