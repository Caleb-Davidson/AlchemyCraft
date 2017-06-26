package xyz.mathroze.alchemycraft.utils;

import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by caleb on 6/21/17.
 */
public class FileUtils {

    private static final String configDirectory = Loader.instance().getConfigDir().getAbsolutePath() + File.separator + "AlchemyCraft" + File.separator;

    public static List<File> getRitualFiles() {
        File ritualDirectory = new File ( configDirectory + "Rituals" + File.separator);
        if (!ritualDirectory.exists())
            ritualDirectory.mkdirs();
        return new ArrayList<File>(Arrays.asList(ritualDirectory.listFiles()));
    }
}
