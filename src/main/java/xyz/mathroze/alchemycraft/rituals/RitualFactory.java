package xyz.mathroze.alchemycraft.rituals;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by caleb on 6/5/17.
 */
public class RitualFactory {

    private static Ritual none = new RitualNone();
    private static Ritual ironToGold = new Ritual(400, 10,
            FluidRegistry.getFluidStack("alchemicslurry", 4000),
            new BlockArea(new Block[][][]{
                {
                        {null, null, null},
                        {null, Block.getBlockFromName("gold_block"), null},
                        {null, null, null}
                },
                {
                        {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol")},
                        {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:alchemicBasin"), Block.getBlockFromName("alchemycraft:ritualSymbol")},
                        {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol")}
                },
                {
                        {null, null, null},
                        {null, Block.getBlockFromName("iron_block"), null},
                        {null, null, null}
                }
            }),
            true, Block.getBlockFromName("gold_block"), null);
    private static Ritual waterToSlurry = new Ritual(100, 0,
            FluidRegistry.getFluidStack("water", 0),
            new BlockArea(new Block[][][]{
                    {
                            {null, null, null},
                            {null, Block.getBlockFromName("leaves"), null},
                            {null, null, null}
                    },
                    {
                            {null, null, null},
                            {null, Block.getBlockFromName("alchemycraft:alchemicBasin"), null},
                            {null, null, null}
                    },
                    {
                            {null, null, null},
                            {null, Block.getBlockFromName("alchemycraft:plantFiber"), null},
                            {null, null, null}
                    }
            }),
            false, Block.getBlockFromName("air"), FluidRegistry.getFluid("alchemicslurry"));

    public static Ritual getRitual(Block focus) {
        if (focus == null)
            return none;
        if (focus == Block.getBlockFromName("gold_block"))
            return ironToGold;
        if (focus == Block.getBlockFromName("leaves"))
            return waterToSlurry;
        return none;
    }
}
