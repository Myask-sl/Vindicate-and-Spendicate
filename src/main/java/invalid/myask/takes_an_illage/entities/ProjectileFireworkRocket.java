package invalid.myask.takes_an_illage.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
        super(world, user.posX, user.posY, user.posZ, user.getHeldItem());
        this.shooter = user;
        // TODO
    }

    public ProjectileFireworkRocket(World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        this(world, user, ammo);
        // TODO
    }

    // TODO: collision fuse...mixin?
}
