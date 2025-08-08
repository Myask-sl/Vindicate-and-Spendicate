package invalid.myask.takes_an_illage;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import invalid.myask.takes_an_illage.items.ItemXBow;

public class IllageItems {

    public static CreativeTabs TAB;

    public static ItemXBow XBOW;

    public static void register() {
        if (!Config.addToVanillaTabs) TAB = new CreativeTabs("takesAnIllage") {
            @Override
            public Item getTabIconItem() {
                return XBOW;
            }
        };

        XBOW = new ItemXBow();
        XBOW.setUnlocalizedName("crossbow")
            .setTextureName(TakesAnIllage.MODID + ":crossbow");
        registerAnItem(XBOW, CreativeTabs.tabCombat);
    }

    static void registerAnItem(Item item, CreativeTabs vanillaTab) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5), TakesAnIllage.MODID);
        if (Config.addToVanillaTabs) item.setCreativeTab(vanillaTab);
        else item.setCreativeTab(TAB);
    }
}
