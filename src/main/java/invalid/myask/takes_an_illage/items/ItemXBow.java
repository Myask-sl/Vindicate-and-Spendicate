package invalid.myask.takes_an_illage.items;

import cpw.mods.fml.common.Loader;
import ganymedes01.etfuturum.ModItems;
import invalid.myask.takes_an_illage.Config;
import invalid.myask.takes_an_illage.TakesAnIllage;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemXBow extends Item {
    protected IIcon iconCocked, iconArrow, iconSpectralArrow, iconTippedArrow, iconRocket, iconBlank;

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
        return pass == 1 ? switch (getLoad(stack)) {
            case 1 -> iconSpectralArrow;
            case 2 -> iconTippedArrow;
            case 3 -> iconRocket;
            case 0 -> iconArrow;
            /*case -1,*/ default -> iconBlank;
        } : percentPulled(stack, player, useRemaining) >= 100 ? iconCocked : itemIcon;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World w, EntityPlayer player, int useRemaining) {
        super.onPlayerStoppedUsing(stack, w, player, useRemaining);
    }

    private int percentPulled(ItemStack stack, EntityPlayer player, int useRemaining) {
        int maxUse = getMaxItemUseDuration(stack);
        return 100 * (int) (getLoad(stack) >= 0 ? 1 : (maxUse - useRemaining) / ((float) maxUse));
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return Config.crossbow_base_charge_ticks - EnchantmentHelper.getEnchantmentLevel(Config.enchid_quickcharge, stack) * Config.ticks_per_quickcharge;
    }

    protected int getLoad(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            ItemStack load = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("charged_projectile"));
            if (load != null) {
                Item loadItem = load.getItem();
                if (loadItem == Items.arrow) return 0;
                if (loadItem == Items.fireworks) return 3;
                if (loadItem != null && Loader.isModLoaded("etfuturum")) {
                    if (loadItem == ModItems.TIPPED_ARROW.get()) return 2;
                    //if (loadItem == ModItems.SPECTRAL_ARROW.get()) return 1; //uh...not there yet?
                }
            }
        }
        return -1;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(TakesAnIllage.MODID + ":xbow");
        iconCocked = register.registerIcon(TakesAnIllage.MODID + ":xbow_cocked");
        iconArrow = register.registerIcon(TakesAnIllage.MODID + ":xbow_arrow");
        iconSpectralArrow = register.registerIcon(TakesAnIllage.MODID + ":xbow_arrow_spectral");
        iconRocket = register.registerIcon(TakesAnIllage.MODID + ":xbow_rocket");
        iconBlank = getIcon(new ItemStack(Blocks.air), 0);
    }
}
