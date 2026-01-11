package invalid.myask.vindicateandspendicate.entity.ai;

import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.item.ItemXBow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class EntityAIReloadCrossbow extends EntityAIBase {
    EntityLiving I;
    long endTime;
    ItemStack munition;
    public EntityAIReloadCrossbow(EntityLiving entityPillager, ItemStack ammo) {
        super();
        I = entityPillager;
        this.setMutexBits(2); //reload on the run, but can't attack while doing so
        munition = ammo;
    }

    @Override
    public boolean shouldExecute() {
        return CrossbowHelper.isUnloaded(I.getHeldItem());
    }

    @Override
    public void startExecuting() {
        ItemStack held = I.getHeldItem();
        endTime = I.worldObj.getTotalWorldTime() + ((ItemXBow)held.getItem()).pullTime(held);
        //TODO playloadSound(held);
    }

    //    public boolean continueExecuting() {} //falls through to shouldExecute, so is fine
    @Override
    public void updateTask() {
        if (I.worldObj.getTotalWorldTime() > endTime) { //note that continueExecuting guarantees equipped Xbow
            ItemStack held = I.getHeldItem();
            ((ItemXBow) held.getItem()).loadWith(held, munition.copy());
        }
    }
}
