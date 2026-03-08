package invalid.myask.vindicateandspendicate.entity.ai;

import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.api.IWeaponReloader;
import invalid.myask.vindicateandspendicate.item.ItemXBow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;

public class EntityAIReloadCrossbow extends EntityAIBase {
    EntityLiving I;
    long endTime, pullTime;
    ItemStack munition;
    boolean loader = true;
    public EntityAIReloadCrossbow(EntityLiving entityPillager, ItemStack ammo) {
        super();
        I = entityPillager;
        if (I instanceof IWeaponReloader) loader = true;
        this.setMutexBits(2); //reload on the run, but can't attack while doing so
        munition = ammo;
        pullTime = Config.crossbow_base_charge_ticks;
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
        if (loader)
            ((IWeaponReloader)I).setLoadProgress(0);
        I.playSound("illager.xbow.charge", 1, .9F + .2F * I.getRNG().nextFloat());
    }

    //    public boolean continueExecuting() {} //falls through to shouldExecute, so is fine
    @Override
    public void updateTask() {
        long now = I.worldObj.getTotalWorldTime();
        if (now > endTime) { //note that continueExecuting guarantees equipped Xbow
            ItemStack held = I.getHeldItem();
            ((ItemXBow) held.getItem()).loadWith(held, munition.copy());
            I.playSound("illager.xbow.charge.done", 1, .9F + .2F * I.getRNG().nextFloat());
            if (loader)
                ((IWeaponReloader)I).setLoadProgress(0);
        } else if (pullTime != 0 && loader)
            ((IWeaponReloader)I).setLoadProgress((int) (100 * (endTime - now) / pullTime));
    }
}
