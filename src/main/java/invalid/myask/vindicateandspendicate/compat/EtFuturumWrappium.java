package invalid.myask.vindicateandspendicate.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EtFuturumWrappium {
    public static EtFuturumWrappium instance;
    static {
        if (Loader.isModLoaded("etfuturum"))
            instance = new EtFuturumLoaded();
        else instance = new EtFuturumWrappium();
    }

    public int getFuturumLoadium(Item loadItem) {
        return -1;
    }

    protected Entity fletchArrow(World world, EntityLivingBase user, ItemStack ammo) {
        return new EntityArrow(world, user, 2);
    }

    protected Entity fletchArrow(World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        return new EntityArrow(world, user, target, 1.6F,
            (float) (14 - world.difficultySetting.getDifficultyId() * 4));
    }

    public Entity fletchTippedArrow (World world, EntityLivingBase user, ItemStack ammo) {
        return fletchArrow(world, user, ammo);
    }

    public Entity fletchTippedArrow (World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        return fletchArrow(world, user,  target, ammo);
    }

    public Entity fletchSpectralArrow (World world, EntityLivingBase user, ItemStack ammo){
        return fletchArrow(world, user, ammo);
    }

    public Entity fletchSpectralArrow (World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        return fletchArrow(world, user, target, ammo);
    }

    public boolean isEFRBanner(Item item) {
        return false;
    }
    public boolean isEFRBanner(ItemStack stack) {
        return stack != null && isEFRBanner(stack.getItem());
    }
    public boolean isEFROminousBanner(ItemStack stack) {
        return false;
    }
}
