package invalid.myask.vindicateandspendicate.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;

@Mixin(EntitySkeleton.class)
public class MixinEntitySkeleton_xbow_enchants {
    @Inject(method = "attackEntityWithRangedAttack",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntityInWorld(Lnet/minecraft/entity/Entity;)Z"))
    private void alteredArrow(EntityLivingBase user, float p_82196_2_, CallbackInfo ci, @Local EntityArrow shot) {
        ItemStack launcher = user.getHeldItem();
        CrossbowHelper.applyCrossbowEnchantsToShot(shot, launcher, user.worldObj, user);
    }
}
