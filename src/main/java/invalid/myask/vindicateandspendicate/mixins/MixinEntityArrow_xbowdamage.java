package invalid.myask.vindicateandspendicate.mixins;

import invalid.myask.vindicateandspendicate.api.IXbowArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow_xbowdamage implements IXbowArrow {

    @Unique
    protected boolean vindicateAndSpendicate$isFixedDamage = false;

    @Shadow
    public abstract double getDamage();

    @ModifyArg(method = "onUpdate",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    private float xbowDamage(float original) {
        return vindicateAndSpendicate$isFixedDamage() ? (float) getDamage() : original;
    }

    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void writeMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        if (vindicateAndSpendicate$isFixedDamage()) tagCompound.setBoolean("fixed_damage", true);
    }

    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void readMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        vindicateAndSpendicate$setFixedDamage(tagCompound.getBoolean("pierces"));
    }

    @Override
    public void vindicateAndSpendicate$setFixedDamage(boolean in) {
        vindicateAndSpendicate$isFixedDamage = in;
    }

    @Override
    public boolean vindicateAndSpendicate$isFixedDamage() {
        return vindicateAndSpendicate$isFixedDamage;
    }
}
