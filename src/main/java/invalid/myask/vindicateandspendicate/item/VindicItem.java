package invalid.myask.vindicateandspendicate.item;

import invalid.myask.vindicateandspendicate.VindicateAndSpendicate;
import net.minecraft.item.Item;

public interface VindicItem {
    default Item setNames(String namebase) {
        return setUnlocalizedName(namebase).setTextureName(VindicateAndSpendicate.MODID + ":" + namebase);
    }

    Item setTextureName(String s);
    Item setUnlocalizedName(String name);
}
