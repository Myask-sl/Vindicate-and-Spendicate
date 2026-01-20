package invalid.myask.vindicateandspendicate.entity.ai;

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;
import invalid.myask.vindicateandspendicate.item.ItemXBow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

public class EntityAIReloadCrossbow extends EntityAIBase {
    EntityLiving I;
    long endTime, pullTime;
    ItemStack munition;
    boolean pillage = true;
    public EntityAIReloadCrossbow(EntityLiving entityPillager, ItemStack ammo) {
        super();
        I = entityPillager;
        if (I instanceof EntityPillager) pillage = true;
        this.setMutexBits(2); //reload on the run, but can't attack while doing so
        munition = ammo;
        pullTime = 15;
    }

    @Override
    public boolean shouldExecute() {
        return CrossbowHelper.isUnloaded(I.getHeldItem());
    }

    @Override
    @SuppressWarnings("null")
    public void startExecuting() {
        ItemStack held = I.getHeldItem();
        pullTime = ((ItemXBow)held.getItem()).pullTime(held);
        endTime = I.worldObj.getTotalWorldTime() + pullTime;
        if (pillage)
            ((EntityPillager)I).setLoadProgress(0);
        //TODO playloadSound(held);
    }

    //    public boolean continueExecuting() {} //falls through to shouldExecute, so is fine
    @Override
    public void updateTask() {
        long now = I.worldObj.getTotalWorldTime();
        if (now > endTime) { //note that continueExecuting guarantees equipped Xbow
            ItemStack held = I.getHeldItem();
            ((ItemXBow) held.getItem()).loadWith(held, munition.copy());
            //TODO: playLoadClick(held)
            if (pillage)
                ((EntityPillager)I).setLoadProgress(0);
        } else if (pullTime != 0 && pillage)
            ((EntityPillager)I).setLoadProgress((int) (100 * (endTime - now) / pullTime));
    }
}
