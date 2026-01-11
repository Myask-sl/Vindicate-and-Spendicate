package invalid.myask.vindicateandspendicate.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAITargetLeadersTarget extends EntityAITarget {
    public EntityAITargetLeadersTarget(EntityCreature thinker, boolean checkSight, boolean nearbyOnly) {
        super(thinker, checkSight, nearbyOnly);
    }

    @Override
    public boolean shouldExecute() {
        return taskOwner.getAITarget() == null && ((IEntityOwnable)taskOwner).getOwner() != null &&
            ((EntityLiving) ((IEntityOwnable)taskOwner).getOwner()).getAttackTarget() != null;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        taskOwner.setAttackTarget(((EntityLiving) ((IEntityOwnable)taskOwner).getOwner()).getAttackTarget());
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase prospect, boolean ignoreInvinciblePlayers) {
        return prospect != ((IEntityOwnable)taskOwner).getOwner() &&
            super.isSuitableTarget(prospect, ignoreInvinciblePlayers);
    }
}
