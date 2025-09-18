package invalid.myask.vindicateandspendicate.enchantments;

import invalid.myask.vindicateandspendicate.Config;
import net.minecraft.enchantment.Enchantment;

public class EnchantmentDualshot extends EnchantmentCrossbow {

    public EnchantmentDualshot(int id, int weight) {
        super(id, weight);
        setName("xbow.dualshot");
    }

    @Override
    public int getMaxLevel() {
        return Config.max_level_dualshot;
    }

    @Override
    public boolean canApplyTogether(Enchantment other) {
        return !(other instanceof EnchantmentPiercing) && super.canApplyTogether(other);
    }
}
