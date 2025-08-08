package invalid.myask.takes_an_illage;

import net.minecraft.enchantment.Enchantment;

import invalid.myask.takes_an_illage.enchantments.*;

public class IllageEnchantments {
    public static Enchantment MULTISHOT, MULTISHOT_Y, PIERCING, QUICK_CHARGE;
    public static void register() {
        if (Config.crossbow_enchants_enable) {
            MULTISHOT = new EnchantmentMultishot(Config.enchid_multishot, 2);
            if (Config.multishot_y_enable)
                MULTISHOT_Y = new EnchantmentMultishot(Config.enchid_multishot_y, 1);
            PIERCING = new EnchantmentPiercing(Config.enchid_piercing, 10);
            QUICK_CHARGE = new EnchantmentQuickCharge(Config.enchid_quickcharge, 5);
        }
    }
}
