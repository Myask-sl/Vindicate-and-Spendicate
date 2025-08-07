package invalid.myask.takes_an_illage.enchantments;

import invalid.myask.takes_an_illage.Config;
import net.minecraft.enchantment.EnumEnchantmentType;

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
