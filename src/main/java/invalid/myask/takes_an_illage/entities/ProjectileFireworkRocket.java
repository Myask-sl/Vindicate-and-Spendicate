package invalid.myask.takes_an_illage.entities;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ProjectileFireworkRocket extends EntityFireworkRocket {
    public ProjectileFireworkRocket(World w) {//this ctor is used by reflection in vanilla
        super(w);
    }

    public ProjectileFireworkRocket(World w, double x, double y, double z, ItemStack stackin) {
        super(w, x, y, z, stackin);
    }
}
