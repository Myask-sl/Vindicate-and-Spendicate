package invalid.myask.vindicateandspendicate.save;

import invalid.myask.vindicateandspendicate.Config;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

import static org.spongepowered.libraries.com.google.common.primitives.Longs.min;

public class PeriodicSpawnTimesData extends WorldSavedData implements Comparable<PeriodicSpawnTimesData> {
    public long nextTrader = Config.wandering_trader_init;
    public long nextPatrol = Config.patrol_init;
    public int failedTraders = 0;
    public int failedPatrols = 0;
    public final int dimension;

    public PeriodicSpawnTimesData(String name, int dimension) {
        super(name);
        this.dimension = dimension;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        nextTrader = nbt.hasKey("nextTrader") ? nbt.getLong("nextTrader") : nextTrader;
        nextPatrol = nbt.hasKey("nextPatrol") ? nbt.getLong("nextPatrol") : nextPatrol;
        failedTraders = nbt.getInteger("failedTraders"); //already defaults 0
        failedPatrols = nbt.getInteger("failedPatrols");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("nextTrader", nextTrader);
        nbt.setLong("nextPatrol", nextPatrol);
        nbt.setInteger("failedTraders", failedTraders);
        nbt.setInteger("failedPatrols", failedPatrols);
    }

    public long next() {
        return min(nextTrader, nextPatrol);
    }

    @Override
    public int compareTo(PeriodicSpawnTimesData o) {
        return Long.compare(next(), o.next());
    }
}
