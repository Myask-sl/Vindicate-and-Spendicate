package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.client.entity.RenderIllager;
import invalid.myask.vindicateandspendicate.client.tileentity.RenderBell;
import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;
import invalid.myask.vindicateandspendicate.entity.illager.EntityVindicator;
import invalid.myask.vindicateandspendicate.item.ItemXBow;
import invalid.myask.vindicateandspendicate.tileentity.TileEntityBell;

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

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        RenderingRegistry.registerBlockHandler(RenderBell.instance);
        ClientRegistry.registerTileEntity(TileEntityBell.class, "village_bell", RenderBell.instance);

        RenderingRegistry.registerEntityRenderingHandler(EntityPillager.class, RenderIllager.instance);
        RenderingRegistry.registerEntityRenderingHandler(EntityVindicator.class, RenderIllager.instance);
        //RenderingRegistry.registerEntityRenderingHandler(EntityWanderingTrader.class, RenderIllager.instance);
        //RenderingRegistry.registerEntityRenderingHandler(EntityWanderingTrillager.class, RenderIllager.instance);
    }
}
