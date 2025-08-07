package invalid.myask.takes_an_illage;

import net.minecraft.enchantment.Enchantment;

import invalid.myask.takes_an_illage.enchantments.*;

public class IllageEnchantments {
    public static Enchantment MULTISHOT, PIERCING, QUICK_CHARGE;
    public static void register() {
        if (Config.crossbow_enchants_enable) {
            MULTISHOT = new EnchantmentMultishot(Config.enchid_multishot, 2);
            PIERCING = new EnchantmentPiercing(Config.enchid_piercing, 10);
            QUICK_CHARGE = new EnchantmentQuickCharge(Config.enchid_quickcharge, 5);
        }
    }
}
