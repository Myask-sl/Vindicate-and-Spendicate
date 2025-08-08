package invalid.myask.takes_an_illage.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import xonin.backhand.api.core.IOffhandInventory;

public class BackhandWrapper {

    static Boolean isLoaded;
    static {
        isLoaded = Loader.isModLoaded("backhand");
    }

    public static ItemStack getOffhand(IInventory inventory) {
        if (isLoaded && inventory instanceof IOffhandInventory backhandable) {
            return backhandable.backhand$getOffhandItem();
        } else return null;
    }

    public static int getOffhandIndex(IInventory inventory) {
        if (isLoaded && inventory instanceof IOffhandInventory backhandable) {
            return backhandable.backhand$getOffhandSlot();
        } else return -1;
    }
}
