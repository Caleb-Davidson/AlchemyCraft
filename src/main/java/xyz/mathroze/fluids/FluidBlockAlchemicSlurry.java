package xyz.mathroze.fluids;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.init.Fluids;


/**
 * Created by caleb on 6/5/17.
 */
public class FluidBlockAlchemicSlurry extends BlockFluidClassic {

    public FluidBlockAlchemicSlurry(String unlocalizedName) {
        super(Fluids.ALCHEMIC_SLURRY, new MaterialLiquid(MapColor.GREEN));
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(References.MOD_ID, unlocalizedName));
    }
}
