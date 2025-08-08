package invalid.myask.takes_an_illage.items;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import ganymedes01.etfuturum.ModItems;
import invalid.myask.takes_an_illage.Config;
import invalid.myask.takes_an_illage.TakesAnIllage;
import invalid.myask.takes_an_illage.api.CrossbowHelper;

public class ItemXBow extends Item {

    public IIcon iconCocked;
    public static IIcon iconArrow, iconSpectralArrow, iconTippedArrow, iconRocket, iconBlank;

    public ItemXBow() {
        setMaxDamage(465);
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return pass == 1 ? CrossbowHelper.getLoadIcon(stack)
            : percentPulled(stack, player, useRemaining) >= 100 ? iconCocked : itemIcon;
    }

    /**
     * Actually "when use ticks run out"
     *
     * @param stack before stack
     * @param world world
     * @param user  user
     * @return after stack
     */
    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer user) {
        return switch (getLoad(stack)) {
            case 0, 1, 2, 3 -> {
                CrossbowHelper.launchProjectile(stack, world, user, null);
                yield stack.attemptDamageItem(1, user.getRNG()) ? null : stack;
            }
            /* case -1 */ default -> {
                int slot = CrossbowHelper.findProjectile(user.inventory);
                if (slot != -1) {
                    NBTTagCompound nbt = stack.getTagCompound();
                    if (nbt == null) {
                        nbt = new NBTTagCompound();
                        stack.setTagCompound(nbt);
                    }
                    NBTTagList projlist;
                    if (nbt.hasKey("charged_projectile")) projlist = nbt.getTagList("charged_projectile", 10);
                    else {
                        projlist = new NBTTagList();
                        nbt.setTag("charged_projectile", projlist);
                    }
                    ItemStack ammoSource = user.inventory.getStackInSlot(slot);
                    if (user.capabilities.isCreativeMode
                        || EnchantmentHelper.getEnchantmentLevel(EnchantmentArrowInfinite.infinity.effectId, stack) > 0) {
                        ammoSource = ammoSource.copy();
                        ammoSource.stackSize = 1;
                    } else ammoSource = ammoSource.splitStack(1);

                    projlist.appendTag(ammoSource.writeToNBT(new NBTTagCompound()));
                }
                yield stack;
            }
        };
    }

    public int percentPulled(ItemStack stack, EntityPlayer player, int useRemaining) {
        int maxUse = getMaxItemUseDuration(stack);
        return 100 * (int) (getLoad(stack) >= 0 ? 1 : (maxUse - useRemaining) / ((float) maxUse));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return Config.crossbow_base_charge_ticks
            - EnchantmentHelper.getEnchantmentLevel(Config.enchid_quickcharge, stack) * Config.ticks_per_quickcharge;
    }

    protected int getLoad(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            NBTTagList projlist = nbt.getTagList("charged_projectile", 10);
            if (projlist != null) {
                nbt = projlist.getCompoundTagAt(projlist.tagCount() - 1);
                ItemStack load = ItemStack.loadItemStackFromNBT(nbt);
                if (load != null) {
                    Item loadItem = load.getItem();
                    if (loadItem == Items.arrow) return 0;
                    if (loadItem == Items.fireworks) return 3;
                    if (loadItem != null && Loader.isModLoaded("etfuturum")) {
                        if (loadItem == ModItems.TIPPED_ARROW.get()) return 2;
                        // if (loadItem == ModItems.SPECTRAL_ARROW.get()) return 1; //uh...not there yet?
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);
        iconCocked = register.registerIcon(getIconString() + "_cocked");
        if (iconArrow == null) {
            iconArrow = register.registerIcon(TakesAnIllage.MODID + ":crossbow_arrow");
            iconSpectralArrow = register.registerIcon(TakesAnIllage.MODID + ":crossbow_spectral_arrow");
            iconTippedArrow = register.registerIcon(TakesAnIllage.MODID + ":crossbow_tipped_arrow");
            iconRocket = register.registerIcon(TakesAnIllage.MODID + ":crossbow_rocket");
            iconBlank = register.registerIcon(TakesAnIllage.MODID + ":blank");
        }
    }
}
