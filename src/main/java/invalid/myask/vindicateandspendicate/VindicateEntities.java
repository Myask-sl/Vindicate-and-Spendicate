package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.common.registry.EntityRegistry;
import invalid.myask.vindicateandspendicate.entities.ProjectileFireworkRocket;

public class VindicateEntities {
    public static void register() {
        EntityRegistry.registerModEntity(ProjectileFireworkRocket.class, "weaponizedFireworksRocket",
            0, VindicateAndSpendicate.instance, 250, 5, true);

    }
}
