package invalid.myask.vindicateandspendicate;

import net.minecraft.world.GameRules;

public class VindicateRules {
    public static void registerRules(GameRules gameRules) {
        gameRules.addGameRule("disableRaids", "false");
        gameRules.addGameRule("doPatrolSpawning", "true");
        gameRules.addGameRule("doTraderSpawning", "true");
    }
}
