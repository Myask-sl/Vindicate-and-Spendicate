package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        VindicateAndSpendicate.LOG.info("I am " + VindicateAndSpendicate.MODNAME + " at version " + Tags.VERSION);

        VindicateItems.register();
        VindicateEnchantments.register();
        VindicateEntities.register();
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        registerLoads();
        VindicateRecipes.register();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}

    protected void registerLoads() {
        CrossbowHelper.registerLoad(Items.arrow, null);
        CrossbowHelper.registerLoad(Items.fireworks, null);
        if (Loader.isModLoaded("etfuturum")) {
            Item tipped = GameRegistry.findItem("etfuturum", "tipped_arrow");
            CrossbowHelper.registerLoad(tipped, null);
            Item spectral = GameRegistry.findItem("etfuturum", "spectral_arrow");
            if (spectral != null) CrossbowHelper.registerLoad(spectral, null);
        }
    }
}
