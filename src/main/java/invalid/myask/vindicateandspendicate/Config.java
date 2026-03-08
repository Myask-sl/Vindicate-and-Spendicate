package invalid.myask.vindicateandspendicate;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    private static final boolean VANILLA_FALSE = true; //stuff I want to test that in vanilla will not be in

    public static int enchid_quickcharge = 67;
    public static int enchid_multishot = 68;
    public static int enchid_piercing = 69;
    public static int enchid_multishot_y = 70;
    public static int enchid_dualshot = 71;
    public static int enchid_dualshot_y = 72;

    public static int max_level_piercing = 4;
    public static int max_level_quickcharge = 3;
    public static int max_level_multishot = 1;
    public static int max_level_dualshot = 1;

    public static int crossbow_base_charge_ticks = 25;
    public static int ticks_per_quickcharge = 5;

    public static boolean craftable_crossbow = true;
    public static boolean crossbow_enchants_enable = true;
    public static boolean multishot_y_enable = VANILLA_FALSE;
    public static boolean dualshot_enable = VANILLA_FALSE;
    public static boolean random_crossbow_damage = true; // Javalike
    public static double multishot_spread = Math.PI / 18;
    public static boolean add_to_vanilla_tabs = false;
    public static boolean legacy_console_fireworks_tab = true;
    public static boolean fireworks_impact_fuse = true; //like Java
    public static float rocket_init_v_magnitude = 0.75F;

    public static boolean permit_multishot_damage_same = VANILLA_FALSE; //TODO not in yet. Vanilla hasn't done it.

    public static boolean modern_redundant_fireworks_nbt = false;
    public static boolean creative_pregen_fireworks_extravagant = false;
    public static boolean damage_per_multishot = false; //damage launcher per multishot: vanilla true

    public static long wandering_trader_init = 24000;
    public static long wandering_trader_period = 24000;
    public static long wandering_trader_variance = 0;
    public static float wandering_trader_chance_base = 0.25F;
    public static float wandering_trader_chance_bonus = 0.25F;
    public static int wandering_trader_bonus_multiplier_cap = 2;

    public static long patrol_init = 132000;
    public static long patrol_period = 12000;
    public static long patrol_variance = 1200;
    public static int patrol_bonus_multiplier_cap = 0;
    public static float patrol_chance_base = 0.2F;
    public static float patrol_chance_bonus = 0.2F;
    public static int patrol_min_followers = 0; //Bedrock 1
    public static int patrol_max_followers = 5;

    public static double vindicator_base_damage = 5; //Bedrock 8
    public static boolean vindicator_johnny_persists = true; //Bedrock false: renaming a Johnny won't de-crazy 'em

    public static boolean omens_in_bottles = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);
        // TODO: all configurables
        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
