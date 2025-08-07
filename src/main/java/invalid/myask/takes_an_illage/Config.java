package invalid.myask.takes_an_illage;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    public static int enchid_quickcharge = 67;
    public static int enchid_multishot = 68;
    public static int enchid_piercing = 69;

    public static boolean crossbow_enchants_enable = true;

    public static int max_level_piercing = 4;
    public static int max_level_quickcharge = 3;
    public static int max_level_multishot = 1;

    public static int crossbow_base_charge_ticks = 25;
    public static int ticks_per_quickcharge = 5;


    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
