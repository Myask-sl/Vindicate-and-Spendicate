package invalid.myask.vindicateandspendicate.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.save.PeriodicSpawnTimesData;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import java.util.HashMap;
import java.util.Map;

public class Scheduillager {
    public static Scheduillager instance = new Scheduillager();

    private final PeriodicSpawnTimesData[] vanillaDimsData = new PeriodicSpawnTimesData[3];
    private final Map<Integer, PeriodicSpawnTimesData> dimSpawnData = new HashMap<>();

    @SubscribeEvent
    public void worldStart(WorldEvent.Load event) {
        World world = event.world;
        int dimension = world.provider.dimensionId;
        if (isRelevant(dimension)) {
            PeriodicSpawnTimesData data = (PeriodicSpawnTimesData) world.loadItemData(PeriodicSpawnTimesData.class, "PeriodicSpawns:" + dimension);
            if (data == null) data = new PeriodicSpawnTimesData("PeriodicSpawns:" + dimension, dimension);
            putData(dimension, data);
            //put in queue. init nextTime
        }
    }
    @SubscribeEvent
    public void tock (TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            int dimension = event.world.provider.dimensionId;
            if (isRelevant(dimension)) {
                long time = event.world.getTotalWorldTime();
                PeriodicSpawnTimesData data = getData(dimension);
                //todo: later, deal with more events possible.
                if (time >= data.next()) {
                    if (time >= data.nextTrader) trySpawnTrader(data, event.world);
                    if (time >= data.nextPatrol) trySpawnPatrol(data, event.world);
                }
            }
        }
    }

    private void trySpawnTrader(PeriodicSpawnTimesData data, World world) {
        if (world.rand.nextFloat() < getTraderSpawnChance(data)) {
            //if (spawnTradeDelegate(world)) { //spawn trader here
            //spawn trader-followers here
            data.failedTraders = 0;
        } else data.failedTraders = data.failedTraders >= Config.wandering_trader_bonus_multiplier_cap ?
            Config.wandering_trader_bonus_multiplier_cap : data.failedTraders + 1;
        data.nextTrader = world.getTotalWorldTime() + Config.wandering_trader_period
            + (world.rand.nextLong() % Config.wandering_trader_variance);
        data.markDirty();
    }

    private float getTraderSpawnChance(PeriodicSpawnTimesData data) {
        return Config.wandering_trader_chance_base + data.failedTraders * Config.wandering_trader_chance_bonus;
    }

    private void trySpawnPatrol(PeriodicSpawnTimesData data, World world) {
        if (world.rand.nextFloat() < getPatrolSpawnChance(data)) {
            //if (spawnPatrolCaptain(world) { //spawn patrol captain here
            //spawn patrol members here (localdifficulty: world.func_147473_B): Config.guaranteed_patrol_minimum
            data.failedPatrols = 0;
        } else data.failedPatrols = data.failedPatrols >= Config.patrol_bonus_multiplier_cap ?
            Config.patrol_bonus_multiplier_cap : data.failedPatrols + 1;
        data.nextPatrol = world.getTotalWorldTime() + Config.patrol_period
            + (world.rand.nextLong() % Config.patrol_variance);
        data.markDirty();
    }

    private float getPatrolSpawnChance(PeriodicSpawnTimesData data) {
        return Config.patrol_chance_base + data.failedPatrols * Config.patrol_chance_bonus;
    }

    private boolean isRelevant(int dimensionId) {
        return dimensionId == 0; //todo: expand later. No, can't get != null, it's prescriptive.
    }

    private PeriodicSpawnTimesData getData (int dimension) {
        if (dimension < 2 && dimension > -2) return vanillaDimsData[dimension+1];
        else return dimSpawnData.get(dimension);
    }
    private void putData (int dimension, PeriodicSpawnTimesData data) {
        if (dimension < 2 && dimension > -2) vanillaDimsData[dimension + 1] = data;
        else dimSpawnData.put(dimension, data);
    }
}
