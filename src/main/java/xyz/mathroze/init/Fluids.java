package xyz.mathroze.init;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.mathroze.alchemycraft.References;
import xyz.mathroze.fluids.FluidAlchemicSlurry;
import xyz.mathroze.fluids.FluidBlockAlchemicSlurry;
import xyz.mathroze.utils.Log;

/**
 * Created by caleb on 6/5/17.
 */
public class Fluids {

    public static Fluid ALCHEMIC_SLURRY;
    public static Block blockAlchemicSlurry;

    public static void register() {
        ALCHEMIC_SLURRY = new FluidAlchemicSlurry("alchemicSlurry");
        FluidRegistry.registerFluid(ALCHEMIC_SLURRY);
        Log.verbose("Fluid name: " + FluidRegistry.getFluidName(ALCHEMIC_SLURRY));
        if (!FluidRegistry.addBucketForFluid(ALCHEMIC_SLURRY))
            Log.verbose("Unable to add Bucket");
        blockAlchemicSlurry = new FluidBlockAlchemicSlurry("blockAlchemicSlurry");
        GameRegistry.register(blockAlchemicSlurry);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        Item item = new ItemBlock(blockAlchemicSlurry);
        ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(References.MOD_ID + ":" + blockAlchemicSlurry.getUnlocalizedName(), "fluid");
            }
        });
        ModelLoader.setCustomStateMapper(blockAlchemicSlurry, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(References.MOD_ID + ":" + blockAlchemicSlurry.getUnlocalizedName(), "fluid");
            }
        });
    }
}
