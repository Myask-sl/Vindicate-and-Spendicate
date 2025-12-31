package invalid.myask.vindicateandspendicate;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import invalid.myask.vindicateandspendicate.items.VindicUCItem;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import invalid.myask.vindicateandspendicate.items.VindicItem;
import invalid.myask.vindicateandspendicate.items.ItemXBow;

public class VindicateItems {

    public static CreativeTabs TAB;

    public static ItemXBow XBOW;

    public static Item TOTEM_DYING = new VindicUCItem().setNames("totem_dying");

    public static void register() {
        if (!Config.addToVanillaTabs) TAB = new CreativeTabs("vindicateandspendicate") {
            @Override
            public Item getTabIconItem() {
                return XBOW;
            }

            @Override
            public void displayAllReleventItems(List<ItemStack> tabbedItems) { //[sic] relevant
                ItemStack firework = new ItemStack(Items.fireworks);
                NBTTagCompound nbt = new NBTTagCompound(),
                    nbtFireworks = new NBTTagCompound(),
                    nbtLargeBall = new NBTTagCompound(),
                    nbtBang = new NBTTagCompound();
                NBTTagList nbtExplosions = new NBTTagList();
                int[] colors = new int[] {MapColor.blueColor.colorValue, MapColor.yellowColor.colorValue};
                firework.setTagCompound(nbt);
                nbt.setTag("Fireworks", nbtFireworks);
                nbtFireworks.setTag("Explosions", nbtExplosions);


                nbtLargeBall.setByte("Type", (byte)1);
                nbtLargeBall.setIntArray("Colors", colors);
                nbtBang.setByte("Type", (byte) -1); //none
                nbtBang.setIntArray("Colors", new int[] {ItemDye.field_150922_c[0], ItemDye.field_150922_c[15]});
                if (Config.creative_pregen_fireworks_extravagant) {
                    nbtLargeBall.setIntArray("FadeColors", colors);
                    nbtLargeBall.setBoolean("Trail", true);
                    nbtLargeBall.setBoolean("Flicker", true);
                }
                if (Config.creative_pregen_modern_redundant_fireworks_nbt) {
                    nbtLargeBall.setString("shape", "large_ball");
                    nbtLargeBall.setIntArray("colors", colors);
                    nbtLargeBall.setIntArray("fade_colors", colors);
                    nbtLargeBall.setBoolean("has_twinkle", true);
                    nbtLargeBall.setBoolean("has_trail", true);
                }
                nbtExplosions.appendTag(nbtLargeBall);
                for (int j = 1; j <= 7; j += 6 ) {
                    for (int i = 1; i <= 3; i++) {
                        nbtFireworks.setInteger("Flight", i);
                        tabbedItems.add(firework.copy());
                    }
                    if (j == 7) break;
                    nbtExplosions.appendTag(nbtBang);
                    nbtExplosions.appendTag(nbtBang);
                    nbtExplosions.appendTag(nbtBang);
                    nbtExplosions.appendTag(nbtBang);
                    nbtExplosions.appendTag(nbtBang);
                    nbtExplosions.appendTag(nbtBang);
                }
                super.displayAllReleventItems(tabbedItems);
            }
        };

        XBOW = new ItemXBow();
        XBOW.setNames("crossbow");
        registerAnItem(XBOW, CreativeTabs.tabCombat);
        registerAnItem(TOTEM_DYING, CreativeTabs.tabMisc);
    }

    static void registerAnItem(Item item, CreativeTabs vanillaTab) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5), VindicateAndSpendicate.MODID);
        //substring(5) trims off the "item." that it adds
        if (Config.addToVanillaTabs) item.setCreativeTab(vanillaTab);
        else item.setCreativeTab(TAB);
    }
}
