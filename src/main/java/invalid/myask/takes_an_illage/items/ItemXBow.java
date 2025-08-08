package invalid.myask.takes_an_illage.items;

import cpw.mods.fml.common.Loader;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import ganymedes01.etfuturum.ModItems;
import invalid.myask.takes_an_illage.Config;
import invalid.myask.takes_an_illage.TakesAnIllage;
import invalid.myask.takes_an_illage.api.CrossbowHelper;

public class ItemXBow extends Item {

    public IIcon[] iconCocked;
    public static IIcon iconArrow, iconSpectralArrow, iconTippedArrow, iconRocket, iconBlank;

    public ItemXBow() {
        setMaxStackSize(1);
        setMaxDamage(465);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityPlayer user, int count) {
        if (getMaxItemUseDuration(stack) - count == pullTime(stack)) {
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
                //TODO: charge SFX?

                projlist.appendTag(ammoSource.writeToNBT(new NBTTagCompound()));
            }
        }
    }

    public int percentPulled(ItemStack stack, EntityPlayer player, boolean using, int useRemaining) {
        int maxUse = pullTime(stack);
        useRemaining = Math.min(useRemaining, maxUse);
        int result = 100 * (int) (getLoad(stack) >= 0 ? 1 : using ? (maxUse - useRemaining) / ((float) maxUse) : 0);
        return result;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer user) {
        if (getLoad(stack) >= 0 && user.getItemInUseDuration() == 0) { //loaded
            CrossbowHelper.launchProjectile(stack, world, user, null);
            return (!user.capabilities.isCreativeMode && stack.attemptDamageItem(1, user.getRNG()) ? null : stack);
        }
        else if (user.capabilities.isCreativeMode || CrossbowHelper.findProjectile(user.inventory) >= 0)
        {
            user.setItemInUse(stack, getMaxItemUseDuration(stack));
        }
        return super.onItemRightClick(stack, world, user);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 65535;
    }

    public int pullTime(ItemStack stack) {
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
    public IIcon getIcon(ItemStack stack, int pass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (getLoad(stack) >= 0) return CrossbowHelper.getLoadIcon(stack);
        return iconCocked[MathHelper.clamp_int(
            (percentPulled(stack, player, usingItem == stack, useRemaining) + 9) / 20,
            0, 5)];
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);
        iconCocked = new IIcon[] {itemIcon,
            register.registerIcon(getIconString() + "_pull1"),
            register.registerIcon(getIconString() + "_pull2"),
            register.registerIcon(getIconString() + "_pull3"),
            register.registerIcon(getIconString() + "_pull4"),
            register.registerIcon(getIconString() + "_cocked")};
        if (iconArrow == null) {
            iconArrow = register.registerIcon(TakesAnIllage.MODID + ":crossbow_arrow");
            iconSpectralArrow = register.registerIcon(TakesAnIllage.MODID + ":crossbow_spectral_arrow");
            iconTippedArrow = register.registerIcon(TakesAnIllage.MODID + ":crossbow_tipped_arrow");
            iconRocket = register.registerIcon(TakesAnIllage.MODID + ":crossbow_fireworks");
            iconBlank = register.registerIcon(TakesAnIllage.MODID + ":blank");
        }
    }
}
