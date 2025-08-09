package invalid.myask.takes_an_illage.utils;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;

import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.entities.ProjectileFireworkRocket;

public class FireworksExploder {
    public static void explodeForDamageMaybe(Entity firework, ItemStack rocket) {
        if (!firework.worldObj.isRemote && rocket != null && rocket.getTagCompound() != null && rocket.getTagCompound().hasKey("Fireworks")) {
            NBTTagCompound fireworksnbt = rocket.getTagCompound().getCompoundTag("Fireworks");
            if (fireworksnbt != null) {
                if (fireworksnbt.hasKey("Explosions")) {
                    int explosions = fireworksnbt.getTagList("Explosions", 10).tagCount(),
                        damageMax = 5 + 2 * explosions;
                    List<Entity> hits = firework.worldObj.getEntitiesWithinAABBExcludingEntity(firework, AxisAlignedBB.getBoundingBox(
                        firework.posX - 5, firework.posY  -5, firework.posZ - 5, firework.posX + 5, firework.posY + 5, firework.posZ + 5));
                    for (Entity e : hits) {
                        if (e instanceof EntityLivingBase elb) {
                            double d = firework.getDistanceToEntity(elb);
                            MovingObjectPosition movingObjectPosition = firework.worldObj.rayTraceBlocks(
                                CrossbowHelper.entityPosAsVec(firework), CrossbowHelper.entityPosAsVec(elb),
                                true); // liquids protect too
                            if (movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) continue;
                            if (((Entity) firework) instanceof ProjectileFireworkRocket pfr)
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
}
