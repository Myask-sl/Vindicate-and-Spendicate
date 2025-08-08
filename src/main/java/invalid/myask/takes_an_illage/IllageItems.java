package invalid.myask.takes_an_illage;


import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.takes_an_illage.items.ItemXBow;
import net.minecraft.item.Item;

public class IllageItems {
    public static ItemXBow XBOW;

    public static void register() {
        XBOW = new ItemXBow();
            XBOW.setUnlocalizedName("crossbow").setTextureName(TakesAnIllage.MODID + ":crossbow");
        registerAnItem(XBOW);
    }

    static void registerAnItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(6), TakesAnIllage.MODID);
    }
}
