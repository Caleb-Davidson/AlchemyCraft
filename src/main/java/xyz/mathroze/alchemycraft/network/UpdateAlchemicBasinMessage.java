package xyz.mathroze.alchemycraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.tileentity.TileEntityAlchemicBasin;

/**
 * Created by caleb on 6/16/17.
 */
public class UpdateAlchemicBasinMessage implements IMessage {
    public UpdateAlchemicBasinMessage(){}

    private BlockPos pos;
    private FluidStack fluidStack;
    private boolean nullFluid;

    public UpdateAlchemicBasinMessage(FluidStack fluidStack, BlockPos pos) {
        this.fluidStack = fluidStack;
        this.pos = pos;
        this.nullFluid = fluidStack == null;
    }

    @Override public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeBoolean(nullFluid);
        if (!nullFluid) {
            NBTTagCompound fluidTag = new NBTTagCompound();
            fluidStack.writeToNBT(fluidTag);
            ByteBufUtils.writeTag(buf, fluidTag);
        }
    }

    @Override public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(
                buf.readInt(),
                buf.readInt(),
                buf.readInt()
        );
        nullFluid = buf.readBoolean();
        if (!nullFluid)
            fluidStack = FluidStack.loadFluidStackFromNBT(ByteBufUtils.readTag(buf));
    }

    @SideOnly(Side.CLIENT)
    public static class UpdateAlchemicBasinMessageHandler implements IMessageHandler<UpdateAlchemicBasinMessage, IMessage> {
        @Override public IMessage onMessage(UpdateAlchemicBasinMessage message, MessageContext ctx) {
            final FluidStack fluidStack = message.fluidStack;
            final BlockPos pos = message.pos;
            final boolean nullFluid = message.nullFluid;
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    TileEntityAlchemicBasin te = (TileEntityAlchemicBasin)Minecraft.getMinecraft().world.getTileEntity(pos);
                    if (nullFluid)
                        te.fluidTank.setFluid(null);
                    else
                        te.fluidTank.setFluid(fluidStack);
                }
            });
            // No response packet
            return null;
        }
    }
}
