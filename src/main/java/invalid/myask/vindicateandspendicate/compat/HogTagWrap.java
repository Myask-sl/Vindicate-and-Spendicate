package invalid.myask.vindicateandspendicate.compat;

import cpw.mods.fml.common.Loader;
import invalid.myask.vindicateandspendicate.item.ItemXBow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class HogTagWrap {
    public static final HogTagWrap instance;
    static {
        if (Loader.isModLoaded("hogutils"))
            instance = new HogUtilsLoaded();
        else instance = new HogTagWrap();
    }

    public int taggedPillagerWeaponPreference(Item item) {
        if (item instanceof ItemXBow) return 2;
        else if (item instanceof ItemAxe) return 1;
        return 0;
    }

    public int taggedVindicatorWeaponPreference(Item item) {
        if (item instanceof ItemAxe) return 2;
        else if (item instanceof ItemXBow) return 1;
        return 0;
    }
}
