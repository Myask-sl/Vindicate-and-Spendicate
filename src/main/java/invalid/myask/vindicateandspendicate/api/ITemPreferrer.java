package invalid.myask.vindicateandspendicate.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

//TODO: move to CommonCowTools
public interface ITemPreferrer {
    /**
     * For implementing special item preferences.
     * @param oldStack equipped
     * @param newStack pickup?
     * @return + for new, - for old, 0 for indifferent (fallback)
     */
    default int preferNewItemStack(int slot, ItemStack oldStack, ItemStack newStack) {
        int result = dislikeNew(slot, newStack) ? -1 : 0;
        if (result == 0) preferNonNull(oldStack, newStack);
        if (result == 0) preferNewItem(slot, oldStack.getItem(), newStack.getItem());
        if (result == 0) result = preferNewTag(slot, oldStack, newStack);
        if (result == 0) result = preferNewDurability(slot, oldStack, newStack);
        return result;
    }

    default boolean dislikeNew(int slot, ItemStack newStack) {
        return false;
    }

    /**
     * Decide preference.
     * @param oldStack equipped
     * @param newStack on-ground
     * @return -1 if new null and old nonnull, 1 if old null and new nonnull, 0 if both null or both nonnnull
     */
    default int preferNonNull(ItemStack oldStack, ItemStack newStack){
        return ((oldStack == null) ? 1 : 0) + ((newStack == null) ? -1 : 0);
    }

    default int preferNewItem(int slot, Item oldItem, Item newItem) {
        return 0;
    }

    /**
     * for enchants, usually, but also prefers named over unnamed
     * @param slot equip-slot
     * @param oldStack equipped
     * @param newStack on-ground
     * @return
     */
    default int preferNewTag(int slot, ItemStack oldStack, ItemStack newStack) {
        return (oldStack.hasTagCompound() ? -1 : 0) +
            (newStack.hasTagCompound() ? 1 : 0);
    }

    /**
     *
     * @param slot
     * @param oldStack
     * @param newStack
     * @return
     */
    default int preferNewDurability(int slot, ItemStack oldStack, ItemStack newStack) {
        int result;
        if (oldStack.isItemStackDamageable()) {
            if (newStack.isItemStackDamageable()) //checking unbreaking, at this point
                result = (newStack.getMaxDamage() - newStack.getItemDamage()) -
                    (oldStack.getMaxDamage() - oldStack.getItemDamage());
            else result = 1;
        } else {
            if (newStack.isItemStackDamageable()) //checking unbreaking, at this point
                result = -1;
            else result = 0;
        }
        return result;
    }
}
