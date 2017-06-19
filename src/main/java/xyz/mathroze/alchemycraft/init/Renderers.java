package xyz.mathroze.alchemycraft.init;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.renderers.AlchemicBasinRenderer;
import xyz.mathroze.alchemycraft.tileentity.TileEntityAlchemicBasin;

/**
 * Created by caleb on 6/17/17.
 */
public class Renderers {

    @SideOnly(Side.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAlchemicBasin.class, new AlchemicBasinRenderer());
    }
}
