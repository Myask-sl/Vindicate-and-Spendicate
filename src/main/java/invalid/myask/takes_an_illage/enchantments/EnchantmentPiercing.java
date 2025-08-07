package invalid.myask.takes_an_illage.enchantments;

import invalid.myask.takes_an_illage.Config;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantmentPiercing extends EnchantmentCrossbow {
    public EnchantmentPiercing(int id, int weight) {
        super(id, weight);
        setName("xbow.pierce");
    }

    @Override
    public int getMaxLevel() {
        return Config.max_level_piercing;
    }

    @Override
    public int getMinEnchantability(int level) {
        return 1 + (level - 1) * 10;
    }
}
