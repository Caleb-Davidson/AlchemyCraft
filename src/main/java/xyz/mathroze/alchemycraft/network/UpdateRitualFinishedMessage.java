package xyz.mathroze.alchemycraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.utils.Log;

/**
 * Created by caleb on 6/16/17.
 */
public class UpdateRitualFinishedMessage implements IMessage {
    public UpdateRitualFinishedMessage(){}

    private BlockPos pos;
    private FluidStack fluidStack;
    private boolean nullFluid;

    public UpdateRitualFinishedMessage(FluidStack fluidStack, BlockPos pos) {
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
    public static class UpdateRitualFinishedMessageHandler implements IMessageHandler<UpdateRitualFinishedMessage, IMessage> {
        @Override public IMessage onMessage(UpdateRitualFinishedMessage message, MessageContext ctx) {
            final FluidStack fluidStack = message.fluidStack;
            final BlockPos pos = message.pos;
            final boolean nullFluid = message.nullFluid;
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    TileEntity entity = Minecraft.getMinecraft().world.getTileEntity(pos);
                    IFluidHandler capability;
                    if (entity != null && entity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
                        capability = entity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                    else {
                        Log.error("Ritual packet update entity at: " + pos.toString() + " does not have a fluid capability.");
                        return;
                    }
                    capability.drain(Integer.MAX_VALUE, true);
                    if (!nullFluid)
                        capability.fill(fluidStack, true);
                }
            });
            // No response packet
            return null;
        }
    }
}
