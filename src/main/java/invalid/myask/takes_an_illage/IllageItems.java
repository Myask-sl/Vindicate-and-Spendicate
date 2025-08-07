package invalid.myask.takes_an_illage;


import invalid.myask.takes_an_illage.items.ItemXBow;
import net.minecraft.item.Item;

public class IllageItems {
    public static Item XBOW;

    public static void register() {
        XBOW = new ItemXBow().setUnlocalizedName("crossbow");
    }
}
