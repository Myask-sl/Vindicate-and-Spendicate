package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.common.registry.EntityRegistry;
import invalid.myask.vindicateandspendicate.entity.ProjectileFireworkRocket;
import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;
import invalid.myask.vindicateandspendicate.entity.illager.EntityVindicator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class VindicateEntities {
    public static void register() {
        EntityRegistry.registerModEntity(ProjectileFireworkRocket.class, "weaponizedFireworksRocket",
            0, VindicateAndSpendicate.instance, 250, 5, true);

    }
}
