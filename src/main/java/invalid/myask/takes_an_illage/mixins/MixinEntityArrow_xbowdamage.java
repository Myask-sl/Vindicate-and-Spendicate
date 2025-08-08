package invalid.myask.takes_an_illage.mixins;

import invalid.myask.takes_an_illage.api.IXbowArrow;
import net.minecraft.entity.projectile.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow_xbowdamage implements IXbowArrow {
    @Shadow
    public abstract double getDamage();

    @ModifyArg(method = "onUpdate",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z"))
    private float xbowDamage(float original) {
        return takesAnIllage$isFixedDamage() ? (float) getDamage() : original;
    }

    @Unique
    protected boolean takesAnIllage$isFixedDamage = false;

    @Override
    public void takesAnIllage$setFixedDamage(boolean in) {
        takesAnIllage$isFixedDamage = in;
    }

    @Override
    public boolean takesAnIllage$isFixedDamage() {
        return takesAnIllage$isFixedDamage;
    }
}
