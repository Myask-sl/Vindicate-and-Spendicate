package invalid.myask.takes_an_illage.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import ganymedes01.etfuturum.entities.EntityTippedArrow;
import invalid.myask.takes_an_illage.Config;
import invalid.myask.takes_an_illage.compat.BackhandWrapper;
import invalid.myask.takes_an_illage.entities.ProjectileFireworkRocket;
import invalid.myask.takes_an_illage.items.ItemXBow;

public class CrossbowHelper {

    protected static Set<Item> crossbowLoads = new HashSet<>();
    protected static Map<Item, IIcon> crossbowLoadsIcons = new HashMap<>();

    public static void registerLoad(Item item, @Nullable IIcon loadedIcon) {
        crossbowLoads.add(item);
        if (loadedIcon != null) crossbowLoadsIcons.put(item, loadedIcon);
    }

    public static void deregisterLoad(Item item) {
        crossbowLoads.remove(item);
        crossbowLoadsIcons.remove(item);
    }

    public static IIcon getLoadIcon(ItemStack stack) {
        IIcon result = null;
        if (stack != null) {
            NBTBase nbt = stack.getTagCompound();
            if (nbt != null) {
                nbt = ((NBTTagCompound) nbt).getTagList("charged_projectile", 10);
                if (nbt != null) {
                    ItemStack loaded = ItemStack.loadItemStackFromNBT(((NBTTagList) nbt).getCompoundTagAt(0));
                    if (loaded != null) result = crossbowLoadsIcons.get(loaded.getItem());
                }
            }
        }
        return result == null ? ItemXBow.iconBlank : result;
    }

    /**
     * Finds suitable crossbow projectile.
     *
     * @param inventory to search
     * @return slot index of projectile
     */
    public static int findProjectile(IInventory inventory) {
        ItemStack candidate = BackhandWrapper.getOffhand(inventory);
        if (candidate != null && crossbowLoads.contains(candidate.getItem()))
            return BackhandWrapper.getOffhandIndex(inventory);
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            candidate = inventory.getStackInSlot(i);
            if (candidate != null && crossbowLoads.contains(candidate.getItem())) return i;
        }
        return -1;
    }

    /**
     * Launch a projectile.
     *
     * @param launcher the crossbow
     * @param world    world
     * @param user     user
     * @param target   target (for mobs), leave null to use user-angle for projectile direction
     */
    public static void launchProjectile(ItemStack launcher, World world, EntityLivingBase user,
        EntityLivingBase target) {
        NBTTagCompound nbt = launcher.getTagCompound();
        String result = "arrow";
        Entity shot;
        ItemStack ammo = null;
        if (nbt != null && nbt.hasKey("charged_projectile")) {
            NBTTagList projlist = nbt.getTagList("charged_projectile", 10);
            if (projlist != null) {
                ammo = ItemStack.loadItemStackFromNBT((NBTTagCompound) projlist.removeTag(projlist.tagCount() - 1));
                if (ammo != null) {
                    Item ammoItem = ammo.getItem();
                    if (ammoItem != null) {
                        result = ammoItem.getUnlocalizedName().substring(5);
                    }
                }
            }
        }
        if (target == null) { // probably player
            switch (result) {
                case "tipped_arrow", "etfuturum.tipped_arrow" -> {
                    shot = new EntityTippedArrow(world, user, 2);
                    ((EntityTippedArrow) shot).setArrow(ammo);
                }
                case "spectral_arrow", "etfuturum.spectral_arrow" ->
                    shot = new EntityArrow(world, user, 2); //TODO: when spectral arrows exist...replace
                case "fireworks" -> shot = new ProjectileFireworkRocket(world, user, ammo);
                default -> shot = new EntityArrow(world, user, 2);
            }
        } else {
            switch (result) {
                case "tipped_arrow" -> {
                    shot = new EntityTippedArrow(world, user, target, 1.6F,
                        (float) (14 - world.difficultySetting.getDifficultyId() * 4));
                    ((EntityTippedArrow) shot).setArrow(ammo);
                }
                case "spectral_arrow" -> shot = new EntityArrow(world, user, target, 1.6F,
                    (float) (14 - world.difficultySetting.getDifficultyId() * 4));
                // TODO: when spectral arrows exist, replace
                case "fireworks" -> shot = new ProjectileFireworkRocket(world, user, target, ammo);
                default -> // "arrow".equals
                    shot = new EntityArrow(world, user, target, 1.6F,
                        (float) (14 - world.difficultySetting.getDifficultyId() * 4));
            }
        }
        if (shot instanceof EntityArrow shotArrow) {
            if (Config.random_crossbow_damage) shotArrow.setDamage(
                user.getRNG().nextInt(5) + 7);
            else shotArrow.setDamage(9);
            boolean pickup = true;
            if (!(user instanceof EntityPlayer)) {
                shotArrow.setDamage(((EntityArrow) shot).getDamage() * .46);
                if (shotArrow.getClass() == EntityArrow.class) pickup = false;
            }
            shotArrow.setDamage(EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, launcher) * 2
                + shotArrow.getDamage());
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, launcher) > 0)
                shotArrow.setFire(100);
            pickup = pickup && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, launcher) == 0;
            shotArrow.canBePickedUp = pickup ? 1 : 2;
            shotArrow.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, launcher));

            world.playSoundAtEntity(user, "random.bow", 1F, 0.9F + user.getRNG().nextFloat() * 0.25F);
        } //else if (shot instanceof EntityFireworkRocket) {
        //    world.playSoundAtEntity(); //Firework plays its own.
        if (!world.isRemote) world.spawnEntityInWorld(shot);
        applyCrossbowEnchantsToShot(shot, launcher, world, user);
    }


    public static void setEntityV(Entity newShot, Vec3 heading) {
        newShot.motionX = heading.xCoord;
        newShot.motionY = heading.yCoord;
        newShot.motionZ = heading.zCoord;
    }

    public static void applyCrossbowEnchantsToShot(Entity shot, ItemStack launcher, World world, EntityLivingBase user) {
        if (shot instanceof IPierceArrow modArrow) modArrow
            .takesAnIllage$setPierce(EnchantmentHelper.getEnchantmentLevel(Config.enchid_piercing, launcher));
        CrossbowHelper.multiShoot(shot, launcher, world, user);
    }

    public static void multiShoot(Entity originalShot, ItemStack launcher, World world, EntityLivingBase user) {
        int multishotX = EnchantmentHelper.getEnchantmentLevel(Config.enchid_multishot, launcher),
            multishotY = EnchantmentHelper.getEnchantmentLevel(Config.enchid_multishot_y, launcher);
        if (multishotX + multishotY == 0) return;
        NBTTagCompound nbt = new NBTTagCompound();
        originalShot.writeToNBTOptional(nbt);
        Entity newShot;
        Vec3 heading;
        // TODO: give cluster UUID so they can combine damage
        for (int yawShots = -multishotX; yawShots <= multishotX; yawShots++) {
            for (int pitchShots = -multishotY; pitchShots <= multishotY; pitchShots++) {
                if (yawShots == 0 && pitchShots == 0) continue; // don't dupe original
                newShot = EntityList.createEntityFromNBT(nbt, world);
                heading = Vec3.createVectorHelper(newShot.motionX, newShot.motionY, newShot.motionZ);
                heading.rotateAroundY((float) (Config.multishot_spread * yawShots));
                heading.rotateAroundX((float) (Config.multishot_spread * pitchShots));

                setEntityV(newShot, heading);
                if (newShot instanceof EntityArrow newArrow) newArrow.shootingEntity =
                    ((EntityArrow)originalShot).shootingEntity;
                // TODO: set cluster UUID so they can combine damage
                if (!world.isRemote) world.spawnEntityInWorld(newShot);
            }
        }
    }

    public static Vec3 entityPosAsVec(Entity e) {
        return Vec3.createVectorHelper(e.posX, e.posY, e.posZ);
    }
}
