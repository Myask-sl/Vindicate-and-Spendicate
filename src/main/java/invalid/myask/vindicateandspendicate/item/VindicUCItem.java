package invalid.myask.vindicateandspendicate.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class VindicUCItem extends VindicItem{
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.uncommon;
    }
}
