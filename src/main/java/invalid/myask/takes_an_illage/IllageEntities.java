package invalid.myask.takes_an_illage;

import cpw.mods.fml.common.registry.EntityRegistry;
import invalid.myask.takes_an_illage.entities.ProjectileFireworkRocket;

public class IllageEntities {
    public static void register() {
        EntityRegistry.registerModEntity(ProjectileFireworkRocket.class, "weaponizedFireworksRocket",
            0, TakesAnIllage.instance, 250, 5, true);

    }
}
