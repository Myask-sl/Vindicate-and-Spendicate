package invalid.myask.vindicateandspendicate.entities;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.utils.FireworksExploder;

public class ProjectileFireworkRocket extends EntityFireworkRocket implements IEntityAdditionalSpawnData {

    EntityLivingBase shooter = null;
    UUID shooterUUID = null;

    @SuppressWarnings("unused")
    public ProjectileFireworkRocket(World w) {// this ctor is used by reflection in vanilla
        super(w);
    }

    public ProjectileFireworkRocket(World w, double x, double y, double z, ItemStack stackin) {
        super(w, x, y, z, stackin);
    }

    public ProjectileFireworkRocket(World world, EntityLivingBase user, ItemStack ammo) {
        super(world, user.posX, user.posY + user.getEyeHeight(), user.posZ, ammo);
        this.shooter = user;
        this.shooterUUID = user.getPersistentID();
        Vec3 lookvec = user.getLookVec().normalize();
        CrossbowHelper.setEntityVTimes(this, lookvec, Config.rocket_init_v_magnitude);
    }

    public ProjectileFireworkRocket(World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        super(world, user.posX, user.posY + user.getEyeHeight(), user.posZ, ammo);
        this.shooter = user;
        this.shooterUUID = user.getPersistentID();
        Vec3 aimvec = Vec3.createVectorHelper(target.posX - user.posX + target.motionX * world.difficultySetting.ordinal(),
            target.posY - user.posY + target.motionY * world.difficultySetting.ordinal(),
            target.posZ - user.posZ + target.motionZ * world.difficultySetting.ordinal());
        aimvec = aimvec.normalize();
        CrossbowHelper.setEntityV(this, aimvec);
    }

    @Override
    public void onUpdate() {
        this.motionY -= 0.04;
        this.motionX /= 1.15;
        this.motionZ /= 1.15; //I don't need to mixin for this!
        super.onUpdate();
        if (Config.fireworks_impact_fuse) {
            List<Entity> hits = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1,1,1));

            Entity finalHit = null;
            for (Entity e : hits) {
                if (e instanceof EntityLivingBase) {
                    if (e.getUniqueID().equals(shooterUUID)) continue;
                    if (e.boundingBox.intersectsWith(this.boundingBox)) {
                        finalHit = e;
                        break;
                    }
                }
            }
            if (finalHit != null) {
                FireworksExploder.explodeForDamageMaybe(this, this.dataWatcher.getWatchableObjectItemStack(8));
                this.worldObj.setEntityState(this, (byte)17);
                this.setDead();
            }
        }
    }

    public EntityLivingBase getShooter() {
        return shooter;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeLong(shooterUUID.getLeastSignificantBits());
        buffer.writeLong(shooterUUID.getMostSignificantBits());
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        long low, high;
        low = additionalData.readLong();
        high = additionalData.readLong();
        shooterUUID = new UUID(high, low);
        for (Entity e : this.worldObj.getLoadedEntityList()) {
            if (e instanceof EntityLivingBase elb && elb.getPersistentID().equals(shooterUUID)) shooter = elb;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompund) {
        super.writeEntityToNBT(tagCompund);
        tagCompund.setLong("shooterUUIDMSB", shooterUUID.getMostSignificantBits());
        tagCompund.setLong("shooterUUIDLSB", shooterUUID.getLeastSignificantBits());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {
        super.readEntityFromNBT(tagCompund);
        if (tagCompund.hasKey("shooterUUIDMSB")) {
            long high = tagCompund.getLong("shooterUUIDMSB"),
                low = tagCompund.getLong("shooterUUIDLSB");
            shooterUUID = new UUID(high, low);
        }
    }
}
