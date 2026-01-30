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
        int result  = preferNewItem(slot, oldStack, newStack);
        if (result == 0) result = preferNewTag(slot, oldStack, newStack);
        if (result == 0) result = preferNewDurability(slot, oldStack, newStack);
        return result;
    }

    /**
     *
     * @param slot slot
     * @param oldStack in-slot
     * @param newStack on-ground
     * @return + for prefer new, - for prefer old, 0 for no preference [falls down to next check]. Note -99 is chosen for null, so "don't even pick up" is <-100 preferItem return
     */
    default int preferNewItem(int slot, ItemStack oldStack, ItemStack newStack) {
        return (newStack != null ? preferItem(slot, newStack.getItem()) : -99)
             - (oldStack != null ? preferItem(slot, oldStack.getItem()) : -99);
    }

    //default boolean dislikeNew(int slot, ItemStack newStack) {
    //    return preferItem(slot, newStack.getItem()) < 0;
    //}

    /* //note: removed because we can just return -100 for "prefer Nothing to This"
     * Decide preference.
     * @param oldStack equipped
     * @param newStack on-ground
     * @return -1 if new null and old nonnull, 1 if old null and new nonnull, 0 if both null or both nonnnull
     */
    //default int preferNonNull(ItemStack oldStack, ItemStack newStack){
    //    return ((oldStack == null) ? 1 : 0) + ((newStack == null) ? -1 : 0);
    //}

    default int preferItem(int slot, Item item) {return 0;}

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
     * Make a preference decision on durability grounds. Default impl prefers unbreakable to any finite durability...
     * though coming after preferNewTag means they'd both have to be tagged to get this far.
     * @param slot
     * @param oldStack
     * @param newStack
     * @return + for prefer newStack, - for prefer oldStack, 0 for no preference/fallthrough
     */
    default int preferNewDurability(int slot, ItemStack oldStack, ItemStack newStack) {
        int result;
        if (isItemBreakable(oldStack)) {
            if (isItemBreakable(newStack))
                result = (newStack.getMaxDamage() - newStack.getItemDamage()) -
                    (oldStack.getMaxDamage() - oldStack.getItemDamage());
            else result = 1;  //prefer unbreakable to any finite durability
        } else {
            if (isItemBreakable(newStack))
                result = -1;  //prefer unbreakable to any finite durability
            else result = 0;
        }
        return result;
    }

    default boolean isItemBreakable(ItemStack stack) {
        return !(stack.hasTagCompound() && stack.getTagCompound().getBoolean("Unbreakable"));
    }
}
