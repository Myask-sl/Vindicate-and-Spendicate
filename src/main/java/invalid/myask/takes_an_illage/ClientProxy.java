package invalid.myask.takes_an_illage;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.items.ItemXBow;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    protected void registerLoads() {
        CrossbowHelper.registerLoad(Items.arrow, ItemXBow.iconArrow);
        CrossbowHelper.registerLoad(Items.fireworks, ItemXBow.iconRocket);
        if (Loader.isModLoaded("etfuturum")) {
            Item tipped = GameRegistry.findItem("etfuturum", "tipped_arrow");
            CrossbowHelper.registerLoad(tipped, ItemXBow.iconTippedArrow);
            Item spectral = GameRegistry.findItem("etfuturum", "spectral_arrow");
            if (spectral != null) CrossbowHelper.registerLoad(spectral, ItemXBow.iconSpectralArrow);
        }
    }
}
