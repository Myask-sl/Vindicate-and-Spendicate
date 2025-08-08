package invalid.myask.takes_an_illage.entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import invalid.myask.takes_an_illage.Config;
import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.api.IBlowUp;

public class ProjectileFireworkRocket extends EntityFireworkRocket {

    EntityLivingBase shooter = null;

    @SuppressWarnings("unused")
    public ProjectileFireworkRocket(World w) {// this ctor is used by reflection in vanilla
        super(w);
    }

    public ProjectileFireworkRocket(World w, double x, double y, double z, ItemStack stackin) {
        super(w, x, y, z, stackin);
    }

    public ProjectileFireworkRocket(World world, EntityLivingBase user, ItemStack ammo) {
        super(world, user.posX, user.posY, user.posZ, ammo);
        this.shooter = user;
        Vec3 lookvec = user.getLookVec().normalize();
        CrossbowHelper.setEntityV(this, lookvec);
    }

    public ProjectileFireworkRocket(World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        super(world, user.posX, user.posY, user.posZ, ammo);
        this.shooter = user;
        Vec3 aimvec = Vec3.createVectorHelper(target.posX - user.posX + target.motionX * world.difficultySetting.ordinal(),
            target.posY - user.posY + target.motionY * world.difficultySetting.ordinal(),
            target.posZ - user.posZ + target.motionZ * world.difficultySetting.ordinal());
        aimvec = aimvec.normalize();
        CrossbowHelper.setEntityV(this, aimvec);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (Config.fireworks_impact_fuse) {
            List<Entity> hits = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1,1,1));

            Entity finalHit = null;
            for (Entity e : hits) {
                if (e == shooter) continue;
                if (e.getBoundingBox().intersectsWith(this.boundingBox)) {
                    finalHit = e; break;
                }
            }
            if (finalHit != null) {
                ((IBlowUp)this).takesAnIllage$explodeForDamageMaybe();
                this.worldObj.setEntityState(this, (byte)17);
                this.setDead();
            }
        }
    }

    public EntityLivingBase getShooter() {
        return shooter;
    }
}
