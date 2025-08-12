package invalid.myask.vindicateandspendicate.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.api.IPierceArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase_multishotMultihit {
    @Unique
    long vindicateAndSpendicate$shotGroupUUIDMSB = 0;
    @Unique
    long vindicateAndSpendicate$shotGroupUUIDLSB = 0;
    @Unique
    boolean vindicateAndSpendicate$wasLastArrowed = false;

    @ModifyExpressionValue(method = "attackEntityFrom",
    at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/EntityLivingBase;lastDamage:F"))
    float multishotMultiHit (float oldDamage, DamageSource source) {
        if (Config.damage_per_multishot && vindicateAndSpendicate$wasLastArrowed
            && source instanceof EntityDamageSourceIndirect edsi && edsi.isProjectile()
            && edsi.getSourceOfDamage() instanceof EntityArrow) {
            if (edsi.getSourceOfDamage() instanceof IPierceArrow pierceArrow) {
                if (pierceArrow.vindicateAndSpendicate$getShotGroupUUID().getMostSignificantBits() == vindicateAndSpendicate$shotGroupUUIDMSB
                && pierceArrow.vindicateAndSpendicate$getShotGroupUUID().getLeastSignificantBits() == vindicateAndSpendicate$shotGroupUUIDLSB)
                    return 0;
            }
        }
        return oldDamage;
    }
    @Inject(method = "attackEntityFrom",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DamageSource;getEntity()Lnet/minecraft/entity/Entity;"))
    void noteShotGroup(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!Config.damage_per_multishot) return;
        if (source instanceof EntityDamageSourceIndirect edsi && edsi.isProjectile()
            && edsi.getSourceOfDamage() instanceof EntityArrow
            && edsi.getSourceOfDamage() instanceof IPierceArrow pierceArrow) {
            vindicateAndSpendicate$wasLastArrowed = true;
            vindicateAndSpendicate$shotGroupUUIDLSB = pierceArrow.vindicateAndSpendicate$getShotGroupUUID().getLeastSignificantBits();
            vindicateAndSpendicate$shotGroupUUIDMSB = pierceArrow.vindicateAndSpendicate$getShotGroupUUID().getMostSignificantBits();
        } else vindicateAndSpendicate$wasLastArrowed = false;
    }
}
