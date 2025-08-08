package invalid.myask.takes_an_illage;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.ModItems;
import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.items.ItemXBow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.


    @Override
    protected void registerLoads() {
        CrossbowHelper.registerLoad(Items.arrow, ItemXBow.iconArrow);
        CrossbowHelper.registerLoad(Items.fireworks, ItemXBow.iconRocket);
        if (Loader.isModLoaded("etfuturum")) {
            CrossbowHelper.registerLoad(ModItems.TIPPED_ARROW.get(), ItemXBow.iconTippedArrow);
            Item spectral = GameRegistry.findItem("etfuturum","spectral_arrow");
            if (spectral != null)
                CrossbowHelper.registerLoad(spectral, ItemXBow.iconSpectralArrow);
        }
    }
}
