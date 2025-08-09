package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class VindicateRecipes {

    public static void register() {
        if (Config.craftable_crossbow) GameRegistry.addRecipe(
            new ShapedOreRecipe(
                VindicateItems.XBOW,
                "SIS",
                "tht",
                " S ",
                'S', "stickWood",
                'I', "ingotIron",
                't', Items.string,
                'h', Blocks.tripwire_hook));
    }
}
