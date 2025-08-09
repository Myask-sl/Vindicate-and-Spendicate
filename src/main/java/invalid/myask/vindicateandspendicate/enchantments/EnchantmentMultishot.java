package invalid.myask.vindicateandspendicate.enchantments;

import invalid.myask.vindicateandspendicate.Config;

public class EnchantmentMultishot extends EnchantmentCrossbow {

    public EnchantmentMultishot(int id, int weight) {
        super(id, weight);
        setName("xbow.multishot");
    }

    @Override
    public int getMaxLevel() {
        return Config.max_level_multishot;
    }
}
