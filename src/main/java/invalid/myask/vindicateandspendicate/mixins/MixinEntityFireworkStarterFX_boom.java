package invalid.myask.vindicateandspendicate.mixins;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFireworkStarterFX;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityFireworkStarterFX.class)
public abstract class MixinEntityFireworkStarterFX_boom extends EntityFX {
    protected MixinEntityFireworkStarterFX_boom(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
    }

    @WrapWithCondition(method = "onUpdate",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFireworkStarterFX;createBall(DI[I[IZZ)V", ordinal = 1))
    private boolean wheresTheEarthShatteringKaboom (EntityFireworkStarterFX instance, double d5, int d6, int[] d7, int[] l, boolean k, boolean j, @Local byte boomType) {
        return boomType == 0;
    }

    @WrapWithCondition(method = "onUpdate",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EffectRenderer;addEffect(Lnet/minecraft/client/particle/EntityFX;)V"))
    private boolean whereIsIt (EffectRenderer instance, EntityFX p_78873_1_, @Local byte boomType) {
        return boomType != -1;
    }
}
