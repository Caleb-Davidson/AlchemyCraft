package xyz.mathroze.alchemycraft.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import xyz.mathroze.alchemycraft.References;

/**
 * Created by caleb on 6/16/17.
 */
public class AlchemyCraftPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.NETWORK_CHANNEL_NAME);
}
