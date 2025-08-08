package invalid.myask.takes_an_illage.mixins;

import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.api.IBlowUp;
import invalid.myask.takes_an_illage.entities.ProjectileFireworkRocket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityFireworkRocket.class)
public abstract class MixinEntityFireworkRocket_shotrocket extends Entity implements IBlowUp {
    public MixinEntityFireworkRocket_shotrocket(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "onUpdate",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityFireworkRocket;moveEntity(DDD)V"))
    private void changeRocketry (CallbackInfo cbi) {
        if (((Entity) this) instanceof ProjectileFireworkRocket)
            this.motionY = (this.motionY - 0.041) * 1.15;
    } //removes the "fly up" part and replaces with "fly in original direction" with very slight drop

    @Inject(method = "onUpdate",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setEntityState(Lnet/minecraft/entity/Entity;B)V"))
    private void explosionsHurt (CallbackInfo cbi) {
        takesAnIllage$explodeForDamageMaybe();
    }

    @Unique
    public void takesAnIllage$explodeForDamageMaybe() {
        ItemStack rocket = this.dataWatcher.getWatchableObjectItemStack(8);
        if (!worldObj.isRemote && rocket != null && rocket.getTagCompound() != null && rocket.getTagCompound().hasKey("Fireworks")) {
            NBTTagCompound fireworksnbt = rocket.getTagCompound().getCompoundTag("Fireworks");
            if (fireworksnbt != null) {
                if (fireworksnbt.hasKey("Explosions")) {
                    int explosions = fireworksnbt.getTagList("Explosions", 10).tagCount(),
                        damageMax = 5 + 2 * explosions;
                    List<Entity> hits = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(
                        posX - 5, posY  -5, posZ - 5, posX + 5, posY + 5, posZ + 5));
                    for (Entity e : hits) {
                        if (e instanceof EntityLivingBase elb) {
                            double d = this.getDistanceToEntity(elb);
                            MovingObjectPosition movingObjectPosition = worldObj.rayTraceBlocks(
                                CrossbowHelper.entityPosAsVec(this), CrossbowHelper.entityPosAsVec(elb),
                                true); // liquids protect too
                            if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) continue;
                            if (((Entity) this) instanceof ProjectileFireworkRocket pfr)
                                elb.attackEntityFrom(
                                    new EntityDamageSourceIndirect("shotfireworks",
                                        this, pfr.getShooter()), (float) (damageMax / d));
                            else
                                elb.attackEntityFrom(
                                    new EntityDamageSource("fireworks",
                                        this), (float) (damageMax / d));
                        }
                    }
                }
            }
        }
    }
}
