package invalid.myask.vindicateandspendicate;

import cpw.mods.fml.common.registry.EntityRegistry;
import invalid.myask.vindicateandspendicate.entity.ProjectileFireworkRocket;
import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;
import invalid.myask.vindicateandspendicate.entity.illager.EntityVindicator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class VindicateEntities {
    public static void register() {
        registerAnEntity(ProjectileFireworkRocket.class, "weaponizedFireworksRocket",
            250, 1, true);
        registerAnEntityWithEgg(EntityPillager.class, "Pillager",
            128, 5, false, 0x50_30_38, 0x90_98_98);
        registerAnEntityWithEgg(EntityVindicator.class, "Vindicator",
            128, 5, false, 0x90_98_98, 0x28_60_60);
    }

    //============================
    //TODO: replace with Stockyard

    static int nextModEntityDex = 0;
    private static void registerAnEntity(Class<? extends Entity> clazz, String entityName, int trackingRange,
                                         int updateFrequency, boolean sendVelocityUpdates) {
        EntityRegistry.registerModEntity(clazz, entityName, nextModEntityDex++, VindicateAndSpendicate.instance, trackingRange, updateFrequency, sendVelocityUpdates);
    }

    private static void registerAnEntityWithEgg(Class<? extends Entity> clazz, String entityName, int trackingRange,
                                                int updateFrequency, boolean sendVelocityUpdates,
                                                int bgColor, int spotColor) {
        registerAnEntity(clazz, entityName, trackingRange, updateFrequency, sendVelocityUpdates);
        addEggMapping(clazz, EntityList.classToStringMapping.getOrDefault(clazz, VindicateAndSpendicate.MODID + "." + entityName), getNextFreeEntityID(), bgColor, spotColor);
    }

    @SuppressWarnings("unchecked")
    private static void addEggMapping(Class<? extends Entity> clazz, String entityName, int verifiedFreeID, int bgColor, int spotColor) {
        EntityList.entityEggs.put(verifiedFreeID, new EntityList.EntityEggInfo(verifiedFreeID, bgColor, spotColor));

        EntityList.IDtoClassMapping.put(verifiedFreeID, clazz);
        EntityList.stringToIDMapping.put(entityName, verifiedFreeID);
        EntityList.classToIDMapping.put(clazz, verifiedFreeID);
    }

    static int nextFreeGlobalEntityID = 1; // 0 is sentinel I guess?
    private static int getNextFreeEntityID() {
        while (EntityList.IDtoClassMapping.containsKey(nextFreeGlobalEntityID)) nextFreeGlobalEntityID++;
        return nextFreeGlobalEntityID;
    }
}
