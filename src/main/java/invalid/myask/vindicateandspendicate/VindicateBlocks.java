package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import invalid.myask.vindicateandspendicate.block.BlockBell;
import invalid.myask.vindicateandspendicate.tileentity.TileEntityBell;

public class VindicateBlocks {
    public static Block BELL = new BlockBell().setStepSound(Block.soundTypeAnvil).setHardness(5);

    public static void register() {
        registerABlock(BELL, "bell", CreativeTabs.tabDecorations);
        GameRegistry.registerTileEntity(TileEntityBell.class, "vindicate_village_bell");
    }

    static void registerABlock(Block applicant, String nom, CreativeTabs vanillaTab) { //TODO: CommonCow
        GameRegistry.registerBlock(applicant, nom);
        applicant.setCreativeTab(Config.add_to_vanilla_tabs ? vanillaTab : VindicateItems.TAB);
    }
}
