package invalid.myask.takes_an_illage.compat;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.entities.EntityTippedArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EtFuturumWrappium {

    public static int getFuturumLoadium(Item loadItem) {

        if (loadItem == ModItems.TIPPED_ARROW.get()) return 2;
        // if (loadItem == ModItems.SPECTRAL_ARROW.get()) return 1; //uh...not there yet?
        return -1;
    }

    public static Entity fletchTippedArrow(World world, EntityLivingBase user, ItemStack ammo) {
        EntityTippedArrow shot = new EntityTippedArrow(world, user, 2);
        shot.setArrow(ammo);
        return shot;
    }

    public static Entity fletchTippedArrow(World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        EntityTippedArrow shot = new EntityTippedArrow(world, user, target, 1.6F,
            (float) (14 - world.difficultySetting.getDifficultyId() * 4));
        shot.setArrow(ammo);
        return shot;
    }

    public static Entity fletchSpectralArrow(World world, EntityLivingBase user, ItemStack ammo) {
        EntityArrow shot = new EntityArrow(world, user, 2); //TODO: when spectral arrows exist...replace
        //shot.setArrow(ammo);
        return shot;
    }

    public static Entity fletchSpectralArrow(World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        EntityArrow shot = new EntityArrow(world, user, target, 1.6F, //TODO: when spectral arrows exist...replace
            (float) (14 - world.difficultySetting.getDifficultyId() * 4));
        //shot.setArrow(ammo);
        return shot;
    }
}
