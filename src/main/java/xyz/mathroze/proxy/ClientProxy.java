package xyz.mathroze.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.init.Blocks;
import xyz.mathroze.init.Items;

/**
 * Created by caleb on 5/16/17.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        Blocks.registerRenders();
        Items.RegisterRenders();
    }
}
