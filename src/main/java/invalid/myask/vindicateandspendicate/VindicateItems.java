package invalid.myask.vindicateandspendicate;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import invalid.myask.vindicateandspendicate.item.ItemXBow;
import invalid.myask.vindicateandspendicate.item.VindicUCItem;
import invalid.myask.vindicateandspendicate.util.FireworksBuilder;

public class VindicateItems {

    public static CreativeTabs TAB;
    public static CreativeTabs FIREWORKS_TAB;

    public static ItemXBow XBOW;

    public static final Item TOTEM_DYING = new VindicUCItem().setNames("totem_dying");
    public static final Item OMEN_BOTTLE = new VindicUCItem().setNames("ominous_bottle");


    public static void register() {
        if (!Config.add_to_vanilla_tabs) TAB = new CreativeTabs("vindicateandspendicate") {
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
                if (Config.modern_redundant_fireworks_nbt) {
                    nbtBang.setString("shape", "concussion");
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
        if (Config.legacy_console_fireworks_tab) FIREWORKS_TAB = new CreativeTabs("legacyfireworks") {
            final ItemStack fly1_ltblue_smball_twinkle = (new FireworksBuilder(1))
                .withNonfadeExplosion((byte) 0xC, (byte) 0, true, false).build();
            final ItemStack fly2_green_creeper = (new FireworksBuilder(2))
                .withNonfadeExplosion((byte) 0x2, (byte) 3).build();
            final ItemStack fly2_red_burst_fadeto_orange = (new FireworksBuilder(2))
                .withFadeExplosion((byte) 0x1, (byte) 0xE, (byte) 4).build();
            final ItemStack fly3_magenta_burst_fadeto_blue_twinkle = (new FireworksBuilder(3))
                .withFadeExplosion((byte) 0xD, (byte) 0x4, (byte) 4).build();
            final ItemStack fly2_yellow_star_fadeto_orange_trail = (new FireworksBuilder(2))
                .withFadeExplosion((byte) 0xC, (byte) 0xE, (byte) 2, false, true).build();

            @Override
            public Item getTabIconItem() {
                return Items.fireworks;
            }

            @Override
            public void displayAllReleventItems(List<ItemStack> tabbedItems) {
                tabbedItems.add(fly1_ltblue_smball_twinkle);
                tabbedItems.add(fly2_green_creeper);
                tabbedItems.add(fly2_red_burst_fadeto_orange);
                tabbedItems.add(fly3_magenta_burst_fadeto_blue_twinkle);
                tabbedItems.add(fly2_yellow_star_fadeto_orange_trail);
                super.displayAllReleventItems(tabbedItems);
            }
        };

        XBOW = new ItemXBow();
        XBOW.setNames("crossbow");
        registerAnItem(XBOW, CreativeTabs.tabCombat);
        registerAnItem(TOTEM_DYING, CreativeTabs.tabMisc);
        registerAnItem(OMEN_BOTTLE, CreativeTabs.tabMisc);
    }

    static void registerAnItem(Item item, CreativeTabs vanillaTab) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5), VindicateAndSpendicate.MODID);
        //substring(5) trims off the "item." that it adds
        if (Config.add_to_vanilla_tabs) item.setCreativeTab(vanillaTab);
        else item.setCreativeTab(TAB);
    }
}
