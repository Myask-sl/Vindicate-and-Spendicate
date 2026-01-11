package invalid.myask.vindicateandspendicate.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

public class EntityAIFollowLeader extends EntityAIBase {
    EntityCreature thinker;
    float leashDistanceSquared, personalSpaceSquared;
    PathNavigate navigator;
    Entity owner;
    public EntityAIFollowLeader(EntityCreature thinker, float causeFollow, float stopFollow) {
        super();
        this.thinker = thinker;
        leashDistanceSquared = causeFollow * causeFollow;
        personalSpaceSquared = stopFollow * stopFollow;
    }

    @Override
    public boolean shouldExecute() {
        if (thinker instanceof IEntityOwnable follower)
            return (follower.getOwner() != null
                && thinker.getDistanceSqToEntity(follower.getOwner()) > leashDistanceSquared);
        return false;
    }

    @Override
    public boolean continueExecuting() {
        return !navigator.noPath() && thinker.getDistanceSqToEntity(owner) > personalSpaceSquared;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        navigator = thinker.getNavigator();
        owner = ((IEntityOwnable)thinker).getOwner();
    }

    @Override
    public void resetTask() {
        super.resetTask();
        navigator = null;
    }

    @Override
    public void updateTask() {
        super.updateTask();
        navigator.tryMoveToEntityLiving(owner, 1);
        //don't need to teleport to leader like follow owner task would
    }
}
