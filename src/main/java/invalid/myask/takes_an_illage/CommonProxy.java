package invalid.myask.takes_an_illage;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import ganymedes01.etfuturum.ModItems;
import invalid.myask.takes_an_illage.api.CrossbowHelper;

public class CommonProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        TakesAnIllage.LOG.info("I am " + TakesAnIllage.MODNAME + " at version " + Tags.VERSION);

        IllageItems.register();
        IllageEnchantments.register();
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        registerLoads();
        IllageRecipes.register();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}

    protected void registerLoads() {
        CrossbowHelper.registerLoad(Items.arrow, null);
        CrossbowHelper.registerLoad(Items.fireworks, null);
        if (Loader.isModLoaded("etfuturum")) {
            CrossbowHelper.registerLoad(ModItems.TIPPED_ARROW.get(), null);
            Item spectral = GameRegistry.findItem("etfuturum", "spectral_arrow");
            if (spectral != null) CrossbowHelper.registerLoad(spectral, null);
        }
    }
}
