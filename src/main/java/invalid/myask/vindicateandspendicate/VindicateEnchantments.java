package invalid.myask.vindicateandspendicate;

import net.minecraft.enchantment.Enchantment;

import invalid.myask.vindicateandspendicate.enchantments.*;

public class VindicateEnchantments {

    public static Enchantment MULTISHOT, MULTISHOT_Y, PIERCING, QUICK_CHARGE, DUALSHOT, DUALSHOT_Y;

    public static void register() {
        if (Config.crossbow_enchants_enable) {
            MULTISHOT = new EnchantmentMultishot(Config.enchid_multishot, 2);
            if (Config.multishot_y_enable) {
                MULTISHOT_Y = new EnchantmentMultishot(Config.enchid_multishot_y, 1);
                MULTISHOT_Y.setName("xbow.multishot.y");
            }
            PIERCING = new EnchantmentPiercing(Config.enchid_piercing, 10);
            QUICK_CHARGE = new EnchantmentQuickCharge(Config.enchid_quickcharge, 5);
            if (Config.dualshot_enable) {
                DUALSHOT = new EnchantmentDualshot(Config.enchid_dualshot, 1);
                if (Config.multishot_y_enable) {
                    DUALSHOT_Y = new EnchantmentDualshot(Config.enchid_dualshot_y, 1);
                    DUALSHOT_Y.setName("xbow.dualshot.y");
                }
            }
        }
    }
}
