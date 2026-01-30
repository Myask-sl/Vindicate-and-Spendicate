package invalid.myask.vindicateandspendicate.entity.illager;

import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.compat.EtFuturumWrappium;
import invalid.myask.vindicateandspendicate.compat.HogTagWrap;
import invalid.myask.vindicateandspendicate.entity.ai.EntityAIGoCrazy;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityVindicator extends EntityPillager {
    protected boolean wearingTux, amJohnny;

    public EntityVindicator(World world) {
        super(world);
        wearingTux = (rand.nextFloat() < 0.05);
        amJohnny = false;
        tasks.addTask(1, new EntityAIGoCrazy(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(12);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(Config.vindicator_base_damage);
    }

    @Override
    protected void setRandomWeapon() {
        setCurrentItemOrArmor(0, new ItemStack(Items.iron_axe));
    }

    @Override
    public boolean isSprinting() {
        return !crossbowWielding;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        if (wearingTux) tagCompound.setBoolean("vindicatorTuxedo", true); //don't waste space on normal case
        tagCompound.setBoolean("Johnny", amJohnny); //save false to overwrite name!
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound); //note this may call setCustomNameTag, so has to be before Johnny tag read
        wearingTux = tagCompound.getBoolean("vindicatorTuxedo"); //not-present = false;
        amJohnny = tagCompound.getBoolean("Johnny");
    }

    public boolean isWearingTux () {
        return wearingTux;
    }

    public boolean isJohnnyHere () {
        return amJohnny;
    }

    @Override
    public void setCustomNameTag(String newName) {
        super.setCustomNameTag(newName);
        amJohnny = (amJohnny && Config.vindicator_johnny_persists)
            || "Johnny".equals(newName);
    }

    @Override
    public int preferItem(int slot, Item item) {
        int result = 0;
        if (slot == 0) {
            result = HogTagWrap.instance.taggedVindicatorWeaponPreference(item);
        } else if (slot == 4) {
            if (EtFuturumWrappium.instance.isEFRBanner(item)) result = 1;
        }
        return result;
    }
}
