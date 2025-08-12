package invalid.myask.vindicateandspendicate.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
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

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.api.IPierceArrow;

import java.util.UUID;

@Mixin(EntityArrow.class)
public abstract class MixinEntityArrow_pierce extends Entity implements IPierceArrow, IEntityAdditionalSpawnData {

    @Unique public int vindicateAndSpendicate$initialPierces = 0;
    @Unique public int vindicateAndSpendicate$piercesRemaining = 0;

    @Unique private UUID vindicateAndSpendicate$shotGroupUUID;

    public MixinEntityArrow_pierce(World worldIn) {
        super(worldIn);
    }

    @WrapWithCondition(
        method = "Lnet/minecraft/entity/projectile/EntityArrow;onUpdate()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/EntityArrow;setDead()V", ordinal = 1))
    private boolean pierce(EntityArrow instance) {
        if (vindicateAndSpendicate$piercesRemaining > 0) {
            vindicateAndSpendicate$piercesRemaining--;
            return false;
        }
        return true;
    }

    @WrapWithCondition(
        method = "Lnet/minecraft/entity/projectile/EntityArrow;onUpdate()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setArrowCountInEntity(I)V"))
    private boolean dontstick(EntityLivingBase instance, int p_85034_1_) {
        return vindicateAndSpendicate$piercesRemaining <= 0;
    }

    @Inject(method = "onUpdate",
    at = @At(value = "FIELD", target = "Lnet/minecraft/entity/projectile/EntityArrow;ticksInAir:I", opcode = Opcodes.PUTFIELD, ordinal = 2, shift = At.Shift.AFTER))
    private void dontBounce(CallbackInfo ci, @Local MovingObjectPosition movingObjectPosition) {
        CrossbowHelper.undoBounce(this, movingObjectPosition);

    }

    @Inject(method = "writeEntityToNBT", at = @At("TAIL"))
    private void writeMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        if (vindicateAndSpendicate$piercesRemaining > 0) tagCompound.setInteger("pierces", vindicateAndSpendicate$getPierces());
        if (vindicateAndSpendicate$initialPierces > 0) tagCompound.setInteger("init_pierces", vindicateAndSpendicate$initialPierces);
        if (vindicateAndSpendicate$shotGroupUUID != null) {
            tagCompound.setLong("shotGroupUUIDLow", vindicateAndSpendicate$shotGroupUUID.getLeastSignificantBits());
            tagCompound.setLong("shotGroupUUIDHigh", vindicateAndSpendicate$shotGroupUUID.getMostSignificantBits());
        }
    }

    @Inject(method = "readEntityFromNBT", at = @At("TAIL"))
    private void readMoreNBT(NBTTagCompound tagCompound, CallbackInfo ci) {
        vindicateAndSpendicate$setPierces(tagCompound.getInteger("pierces"));
        vindicateAndSpendicate$initialPierces = tagCompound.getInteger("init_pierces");
        if (tagCompound.hasKey("shotGroupUUIDLow") && tagCompound.hasKey("shotGroupUUIDHigh")) {
            long low = tagCompound.getLong("shotGroupUUIDLow"),
                high = tagCompound.getLong("shotGroupUUIDHigh");
            vindicateAndSpendicate$shotGroupUUID = new UUID (high, low);
        } else vindicateAndSpendicate$shotGroupUUID = new UUID (getUniqueID().getMostSignificantBits(),
                                                                getUniqueID().getLeastSignificantBits());
    }

    @Override
    public void vindicateAndSpendicate$setPierces(int number) {
        vindicateAndSpendicate$piercesRemaining = Math.max(number, 0);
        vindicateAndSpendicate$initialPierces = vindicateAndSpendicate$piercesRemaining;
    }

    @Override
    public int vindicateAndSpendicate$getPierces() {
        return vindicateAndSpendicate$piercesRemaining;
    }

    public int vindicateAndSpendicate$getInitialPierces() {
        return vindicateAndSpendicate$initialPierces;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(vindicateAndSpendicate$getInitialPierces());
        buffer.writeInt(vindicateAndSpendicate$getPierces());
        buffer.writeLong(vindicateAndSpendicate$shotGroupUUID.getLeastSignificantBits());
        buffer.writeLong(vindicateAndSpendicate$shotGroupUUID.getMostSignificantBits());
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        vindicateAndSpendicate$initialPierces = additionalData.readInt();
        vindicateAndSpendicate$piercesRemaining = additionalData.readInt(); //perhaps redundant but I'm annoyed
        long low = additionalData.readLong(),
            high = additionalData.readLong();
        vindicateAndSpendicate$shotGroupUUID = new UUID (high, low);
    }

    @Override
    public UUID vindicateAndSpendicate$getShotGroupUUID() {
        return vindicateAndSpendicate$shotGroupUUID;
    }
    @Override
    public void vindicateAndSpendicate$setShotGroupUUID(UUID uuid) {
        vindicateAndSpendicate$shotGroupUUID = uuid;
    }
}
