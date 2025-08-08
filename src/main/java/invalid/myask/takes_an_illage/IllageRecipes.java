package invalid.myask.takes_an_illage;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class IllageRecipes {

    public static void register() {
        if (Config.craftable_crossbow) GameRegistry.addRecipe(
            new ShapedOreRecipe(
                IllageItems.XBOW,
                "SIS",
                "tht",
                " S ",
                'S', "stickWood",
                'I', "ingotIron",
                't', Items.string,
                'h', Blocks.tripwire_hook));
    }
}
