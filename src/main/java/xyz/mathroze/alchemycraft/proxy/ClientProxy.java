package xyz.mathroze.alchemycraft.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.init.Renderers;
import xyz.mathroze.alchemycraft.init.Blocks;
import xyz.mathroze.alchemycraft.init.Fluids;
import xyz.mathroze.alchemycraft.init.Items;

/**
 * Created by caleb on 5/16/17.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        Fluids.registerModels();
        Blocks.registerRenders();
        Items.registerRenders();
        Renderers.registerRenderers();
    }
}
