package invalid.myask.vindicateandspendicate.enchantments;

import invalid.myask.vindicateandspendicate.Config;

public class EnchantmentQuickCharge extends EnchantmentCrossbow {

    public EnchantmentQuickCharge(int id, int weight) {
        super(id, weight);
        setName("xbow.quickcharge");
    }

    @Override
    public int getMinEnchantability(int level) {
        return 12 + 20 * level;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return level >= 3 ? getMinEnchantability(level) + 10 : 50;
    }

    @Override
    public int getMaxLevel() {
        return Config.max_level_quickcharge;
    }
}
