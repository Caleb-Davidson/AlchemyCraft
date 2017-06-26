package xyz.mathroze.alchemycraft.init;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import xyz.mathroze.alchemycraft.rituals.RitualFactory;
import xyz.mathroze.alchemycraft.rituals.RitualTiers;
import xyz.mathroze.alchemycraft.utils.BlockArea;
import xyz.mathroze.alchemycraft.rituals.Ritual;
import xyz.mathroze.alchemycraft.utils.FileUtils;
import xyz.mathroze.alchemycraft.utils.Log;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caleb on 6/21/17.
 */
public class Rituals {

    public static void registerRituals() {
        addTiers();
        addRituals();
    }

    private static void addTiers() {
        RitualTiers.addTier(0,
            new BlockArea(new Block[][][]{
                {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                {
                    {null, null, null},
                    {null, Block.getBlockFromName("alchemycraft:alchemicBasin"), null},
                    {null, null, null}
                },
                {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                }
            }));
        RitualTiers.addTier(1,
            new BlockArea(new Block[][][]{
                {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                {
                    {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol")},
                    {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:alchemicBasin"), Block.getBlockFromName("alchemycraft:ritualSymbol")},
                    {Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol"), Block.getBlockFromName("alchemycraft:ritualSymbol")}
                },
                {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                }
            }));
    }

    private static void addRituals() {
        List<Ritual> rituals = ritualFromFile();
        for (Ritual ritual : rituals) {
            RitualFactory.addRitual(ritual);
        }

        BlockArea ironRitualArea = new BlockArea();
        ironRitualArea.setBlockAt(0, 1, 0, Block.getBlockFromName("iron_block"));
        ironRitualArea.setBlockAt(0, -1, 0, Block.getBlockFromName("gold_block"));
        RitualFactory.addRitual(new Ritual(400, 10,
                FluidRegistry.getFluid("alchemicslurry"),
                1, ironRitualArea, false,
                Block.getBlockFromName("gold_block"), null));

        BlockArea slurryRitualArea = new BlockArea();
        slurryRitualArea.setBlockAt(0, 1, 0, Block.getBlockFromName("alchemycraft:plantFiber"));
        slurryRitualArea.setBlockAt(0, -1, 0, Block.getBlockFromName("leaves"));
        RitualFactory.addRitual(new Ritual(100, 0,
                FluidRegistry.getFluid("water"),
                0, slurryRitualArea, true,
                Block.getBlockFromName("air"), FluidRegistry.getFluid("alchemicslurry")));
    }

    private static List<Ritual> ritualFromFile() {
        List<File> ritualFiles = FileUtils.getRitualFiles();
        List<Ritual> rituals = new ArrayList<Ritual>();

        for (File file : ritualFiles) {
            if (!(file.getName().matches("Ritual+\\d\\.json")))
                continue;
            Log.verbose("Found ritual file: " + file.getName());

            try {
                JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(file));

                List<String> blocks = new ArrayList<String>(27);
                for (int i = 0; i < 3; i++) {
                    JSONArray array = (JSONArray)((JSONArray) json.get("ritualBlocks")).get(i);
                    for (int j = 0; j < 9; j++) {
                        blocks.add((String)array.get(j));
                    }
                }

                Ritual ritual = new Ritual(
                        ((Long) json.get("transformationTicks")).intValue(),
                        ((Long) json.get("drainPerTick")).intValue(),
                        FluidRegistry.getFluid((String) json.get("neededFluid")),
                        ((Long) json.get("tier")).intValue(),
                        new BlockArea(blocks),
                        (Boolean) json.get("fluidInfusion"),
                        Block.getBlockFromName((String) json.get("blockResult")),
                        FluidRegistry.getFluid((String) json.get("fluidResult"))
                );
                rituals.add(ritual);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rituals;
    }
}
