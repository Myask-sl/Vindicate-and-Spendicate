package invalid.myask.vindicateandspendicate.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.Entity;

public class UndertowWrapper {
    public static UndertowWrapper instance;
    static {
        if (Loader.isModLoaded("undertow"))
            instance = new UndertowLoaded();
        else instance = new UndertowWrapper();
    }

    public boolean isTrident(Entity e) {
        return false;
    }
}
