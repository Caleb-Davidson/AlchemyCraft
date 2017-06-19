package xyz.mathroze.alchemycraft.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import xyz.mathroze.alchemycraft.tileentity.TileEntityAlchemicBasin;
import xyz.mathroze.alchemycraft.utils.RenderUtil;

/**
 * Created by caleb on 6/13/17.
 */
public class AlchemicBasinRenderer extends TileEntitySpecialRenderer<TileEntityAlchemicBasin> {

    @Override
    public void renderTileEntityAt(TileEntityAlchemicBasin tile, double x, double y, double z, float partialTick, int destroyStage) {
        if (tile.fluidTank.getFluid() != null) {
            float fluidHeight = (((float)tile.fluidTank.getFluidAmount() / tile.fluidTank.getCapacity()) * 1.25f) - 0.5f;
            RenderUtil.renderFluidCuboid(tile.fluidTank.getFluid(), tile.getPos(), x, y+0.125f, z, 0.875f, fluidHeight, 0.875f);
        }
    }
}
