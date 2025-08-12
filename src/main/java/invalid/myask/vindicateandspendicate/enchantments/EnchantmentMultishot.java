package invalid.myask.vindicateandspendicate.enchantments;

import invalid.myask.vindicateandspendicate.Config;
import net.minecraft.enchantment.Enchantment;

public class EnchantmentMultishot extends EnchantmentCrossbow {

    public EnchantmentMultishot(int id, int weight) {
        super(id, weight);
        setName("xbow.multishot");
    }

    @Override
    public int getMaxLevel() {
        return Config.max_level_multishot;
    }

    @Override
    public boolean canApplyTogether(Enchantment other) {
        return !(other instanceof EnchantmentPiercing);
    }
}
