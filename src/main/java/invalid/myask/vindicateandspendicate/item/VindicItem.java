package invalid.myask.vindicateandspendicate.item;

import invalid.myask.vindicateandspendicate.VindicateAndSpendicate;
import net.minecraft.item.Item;

public class VindicItem extends Item {
    public Item setNames(String namebase) {
        setUnlocalizedName(namebase);
        setTextureName(VindicateAndSpendicate.MODID + ":" + namebase);
        return this;
    }
}
