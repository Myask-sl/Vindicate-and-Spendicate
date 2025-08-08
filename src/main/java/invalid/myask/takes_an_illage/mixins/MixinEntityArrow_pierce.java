package invalid.myask.takes_an_illage.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.api.IPierceArrow;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow_pierce extends Entity implements IPierceArrow {

    @Unique
    public int takes_an_illage$initialPierces = 0;

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

    @Inject(method = "onUpdate",
    at = @At(value = "FIELD", target = "Lnet/minecraft/entity/projectile/EntityArrow;ticksInAir:I", opcode = Opcodes.PUTFIELD, ordinal = 2, shift = At.Shift.AFTER))
    private void dontBounce(CallbackInfo ci, @Local MovingObjectPosition movingObjectPosition) {
        CrossbowHelper.undoBounce(this, movingObjectPosition);

    }

    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void writeMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        if (takes_an_illage$piercesRemaining > 0) tagCompound.setInteger("pierces", takesAnIllage$getPierces());
        if (takes_an_illage$initialPierces > 0) tagCompound.setInteger("init_pierces", takes_an_illage$initialPierces);
    }

    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void readMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        takesAnIllage$setPierces(tagCompound.getInteger("pierces"));
        takes_an_illage$initialPierces = tagCompound.getInteger("init_pierces");
    }

    @Override
    public void takesAnIllage$setPierces(int number) {
        takes_an_illage$piercesRemaining = Math.max(number, 0);
        takes_an_illage$initialPierces = takes_an_illage$piercesRemaining;
    }

    @Override
    public int takesAnIllage$getPierces() {
        return takes_an_illage$piercesRemaining;
    }

    public int takes_an_illage$getInitialPierces() {
        return takes_an_illage$initialPierces;
    }
}
