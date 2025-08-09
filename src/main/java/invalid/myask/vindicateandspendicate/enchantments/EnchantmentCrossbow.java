package invalid.myask.vindicateandspendicate.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

import invalid.myask.takes_an_illage.items.ItemXBow;

public abstract class EnchantmentCrossbow extends Enchantment {

    protected EnchantmentCrossbow(int id, int weight) {
        super(id, weight, EnumEnchantmentType.bow);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemXBow;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 50;
    }
}
