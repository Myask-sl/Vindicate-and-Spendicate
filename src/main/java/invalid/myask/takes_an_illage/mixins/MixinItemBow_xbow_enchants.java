package invalid.myask.takes_an_illage.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import invalid.myask.takes_an_illage.api.CrossbowHelper;

@Mixin(ItemBow.class)
public class MixinItemBow_xbow_enchants {
    // TODO
    @Inject(method = "onPlayerStoppedUsing",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntityInWorld(Lnet/minecraft/entity/Entity;)Z"))
    private void alteredArrow(ItemStack launcher, World world, EntityPlayer user, int p_77615_4_, CallbackInfo ci, @Local EntityArrow shot) {
        CrossbowHelper.applyCrossbowEnchantsToShot(shot, launcher, world, user);
    }
}
