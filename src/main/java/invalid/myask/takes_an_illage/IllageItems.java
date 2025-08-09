package invalid.myask.takes_an_illage;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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

            @Override
            public void displayAllReleventItems(List<ItemStack> tabbedItems) { //[sic] relevant
                ItemStack firework = new ItemStack(Items.fireworks);
                NBTTagCompound nbt = new NBTTagCompound(),
                    nbtFireworks = new NBTTagCompound(),
                    nbtLargeBall = new NBTTagCompound();
                NBTTagList nbtExplosions = new NBTTagList();
                int[] colors = new int[] {MapColor.blueColor.colorValue, MapColor.yellowColor.colorValue};
                firework.setTagCompound(nbt);
                nbt.setTag("Fireworks", nbtFireworks);
                nbtFireworks.setTag("Explosions", nbtExplosions);
                nbtLargeBall.setString("shape", "large_ball");
                nbtLargeBall.setIntArray("colors", colors);
                for (int j = 1; j <= 7; j += 6 ) {
                    nbtExplosions.appendTag(nbtLargeBall);
                    for (int i = 1; i <= 3; i++) {
                        nbtFireworks.setInteger("Flight", i);
                        tabbedItems.add(firework.copy());
                    }
                    if (j == 7) break;
                    nbtExplosions.appendTag(nbtLargeBall);
                    nbtExplosions.appendTag(nbtLargeBall);
                    nbtExplosions.appendTag(nbtLargeBall);
                    nbtExplosions.appendTag(nbtLargeBall);
                    nbtExplosions.appendTag(nbtLargeBall);
                }
                super.displayAllReleventItems(tabbedItems);
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
