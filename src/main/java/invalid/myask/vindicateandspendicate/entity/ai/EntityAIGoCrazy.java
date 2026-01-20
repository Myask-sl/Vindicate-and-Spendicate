package invalid.myask.vindicateandspendicate.entity.ai;

import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;
import invalid.myask.vindicateandspendicate.entity.illager.EntityVindicator;
import invalid.myask.vindicateandspendicate.entity.illager.Illager;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityGhast;

public class EntityAIGoCrazy extends EntityAINearestAttackableTarget {
    public EntityAIGoCrazy(EntityVindicator self) {
        super(self, EntityLivingBase.class, 90, true, false, ViableTargets.instance);
    }

    @Override
    public boolean shouldExecute() {
        return ((EntityVindicator)taskOwner).isJohnnyHere() && super.shouldExecute();
    }

    static class ViableTargets implements IEntitySelector {
        static ViableTargets instance = new ViableTargets();
        @Override
        public boolean isEntityApplicable(Entity entity) {
            return !(entity instanceof EntityPillager || entity instanceof EntityGhast); //TODO: incl. other illagers?
        }
    }
}
