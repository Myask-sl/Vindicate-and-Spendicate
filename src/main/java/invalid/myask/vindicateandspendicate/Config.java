package invalid.myask.vindicateandspendicate;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static int enchid_quickcharge = 67;
    public static int enchid_multishot = 68;
    public static int enchid_piercing = 69;
    public static int enchid_multishot_y = 70;

    public static int max_level_piercing = 4;
    public static int max_level_quickcharge = 3;
    public static int max_level_multishot = 1;

    public static int crossbow_base_charge_ticks = 25;
    public static int ticks_per_quickcharge = 5;

    public static boolean craftable_crossbow = true;
    public static boolean crossbow_enchants_enable = true;
    public static boolean random_crossbow_damage = true; // Javalike
    public static boolean multishot_y_enable = true;
    public static double multishot_spread = Math.PI / 18;
    public static boolean addToVanillaTabs = false;
    public static boolean fireworks_impact_fuse = true; //like Java
    public static float rocket_init_v_magnitude = 0.75F;

    public static boolean permit_multishot_damage_same = true; //TODO not in yet. Vanilla hasn't fixed.

    public static boolean creative_pregen_modern_redundant_fireworks_nbt = false;
    public static boolean creative_pregen_fireworks_extravagant = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        // TODO: all configurables
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
