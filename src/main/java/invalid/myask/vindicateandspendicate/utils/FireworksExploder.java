package invalid.myask.vindicateandspendicate.utils;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.entities.ProjectileFireworkRocket;

public class FireworksExploder {
    public static void explodeForDamageMaybe(Entity firework, ItemStack rocket) {
        if (!firework.worldObj.isRemote && rocket != null && rocket.getTagCompound() != null && rocket.getTagCompound().hasKey("Fireworks")) {
            NBTTagCompound fireworksnbt = rocket.getTagCompound().getCompoundTag("Fireworks");
            if (fireworksnbt != null && fireworksnbt.hasKey("Explosions")) {
                int explosions = fireworksnbt.getTagList("Explosions", 10).tagCount(),
                    damageMax = 5 + 2 * explosions;
                List<Entity> hits = firework.worldObj.getEntitiesWithinAABBExcludingEntity(firework, AxisAlignedBB.getBoundingBox(
                    firework.posX - 5, firework.posY  -5, firework.posZ - 5, firework.posX + 5, firework.posY + 5, firework.posZ + 5));
                for (Entity e : hits) {
                    if (e instanceof EntityLivingBase elb) {
                        double d = Math.max (firework.getDistanceToEntity(elb), 1);
                        MovingObjectPosition movingObjectPosition = firework.worldObj.rayTraceBlocks(
                            VectorHelper.entityPosAsVec3(firework), VectorHelper.entityPosAsVec3(elb),
                            true); // liquids protect too
                        if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) continue;
                        if (firework instanceof ProjectileFireworkRocket pfr)
                            elb.attackEntityFrom(
                                new EntityDamageSourceIndirect("shotfireworks",
                                    firework, pfr.getShooter()), (float) (damageMax / d));
                        else
                            elb.attackEntityFrom(
                                new EntityDamageSource("fireworks",
                                    firework), (float) (damageMax / d));
                    }
                }
            }
        }
    }
}
