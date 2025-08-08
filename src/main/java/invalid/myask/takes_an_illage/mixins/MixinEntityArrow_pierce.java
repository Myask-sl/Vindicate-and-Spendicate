package invalid.myask.takes_an_illage.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow_pierce extends Entity {

    public MixinEntityArrow_pierce(World worldIn) {
        super(worldIn);
    }

    @Unique
    public int takes_an_illage$piercesRemaining = 0;

    @WrapWithCondition(
        method = "Lnet/minecraft/entity/projectile/EntityArrow;onUpdate()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityArrow;setDead()V", ordinal = 1))
    private boolean pierce(EntityArrow instance) {
        if (takes_an_illage$piercesRemaining > 0) {
            takes_an_illage$piercesRemaining--;
            return false;
        }
        return true;
    }

    @WrapWithCondition(
        method = "Lnet/minecraft/entity/projectile/EntityArrow;onUpdate()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setArrowCountInEntity(I)V"))
    private boolean dontstick(EntityLivingBase instance, int p_85034_1_) {
        return takes_an_illage$piercesRemaining <= 0;
    }

    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void writeMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        if (takes_an_illage$piercesRemaining > 0) tagCompound.setInteger("pierces", takes_an_illage$piercesRemaining);
    }

    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void readMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        takes_an_illage$piercesRemaining = tagCompound.getInteger("pierces");
    }
}
