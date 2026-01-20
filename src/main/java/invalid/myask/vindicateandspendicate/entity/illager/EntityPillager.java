package invalid.myask.vindicateandspendicate.entity.illager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.VindicateItems;
import invalid.myask.vindicateandspendicate.api.CrossbowHelper;
import invalid.myask.vindicateandspendicate.api.ITemPreferrer;
import invalid.myask.vindicateandspendicate.compat.EtFuturumWrappium;
import invalid.myask.vindicateandspendicate.entity.ai.EntityAIFollowLeader;
import invalid.myask.vindicateandspendicate.entity.ai.EntityAIReloadCrossbow;
import invalid.myask.vindicateandspendicate.entity.ai.EntityAITargetLeadersTarget;
import invalid.myask.vindicateandspendicate.item.ItemXBow;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityPillager extends EntityMob implements IEntityOwnable, IRangedAttackMob, Illager, ITemPreferrer {

    private final EntityAIBreakDoor DOOR_ATTACK;
    private final EntityAIAttackOnCollide GETIN_ATTACK ;
    private final EntityAIArrowAttack ARROW_ATTACK;
    protected String ownerUUID;
    protected boolean breaksDoors, crossbowWielding = true, amCaptain = false, onRaid = false;
    protected int loadProgress;

    public EntityPillager(World world) {
        this(world, false);
    }

    public EntityPillager(World world, boolean captaincy) {
        this(world, captaincy, false);
    }

    public EntityPillager(World world, boolean captaincy, boolean raiding) {
        super(world);
        amCaptain = captaincy;
        onRaid = raiding;
        // tasks.addTask(-1, new EntityAIJumpForJoy(this)); //won raid
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIReloadCrossbow(this, new ItemStack(Items.arrow)));
        ARROW_ATTACK = new EntityAIArrowAttack(this, 1.0D, 60, 15.0F);
        GETIN_ATTACK = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
        DOOR_ATTACK = new EntityAIBreakDoor(this);
        tasks.addTask(4, new EntityAIFollowLeader(this, 10, 5));
        tasks.addTask(5, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(2, new EntityAITargetLeadersTarget(this, false, false));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, true));
        targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        if (world != null && !world.isRemote)
            setCombatStrategy();
        loadProgress = -1;
    }

    private void setCombatStrategy() {
        tasks.removeTask(ARROW_ATTACK);
        tasks.removeTask(GETIN_ATTACK);
        tasks.removeTask(DOOR_ATTACK);
        ItemStack stack = getHeldItem();
        if (stack != null && stack.getItem() instanceof ItemXBow) {
            tasks.addTask(2, ARROW_ATTACK);
            crossbowWielding = true;
        } else {
            tasks.addTask(2, GETIN_ATTACK);
            crossbowWielding = false;
        }
        if (stack != null && breaksDoors && stack.getItem() instanceof ItemAxe)
            tasks.addTask(3, DOOR_ATTACK);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, "");
    }

    @Override
    public String func_152113_b() { //GetOwner
        return dataWatcher.getWatchableObjectString(17);
    }

    @Override
    public EntityLivingBase getOwner() {
        try {
            UUID uuid = UUID.fromString(func_152113_b());
            return uuid == null ? null : this.worldObj.func_152378_a(uuid);
        }
        catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase pincushion, float num) {
        CrossbowHelper.launchProjectile(getHeldItem(), worldObj, this, pincushion); //applies enchants and everything
        if (getHeldItem().attemptDamageItem(1, rand))
            setCurrentItemOrArmor(0, null);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }


    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData entityLivingData) {
        //TODO
        addRandomArmor();
        enchantEquipment();
        setCombatStrategy();
        setCanPickUpLoot(rand.nextFloat() < 0.5F * worldObj.func_147462_b(posX, posY, posZ));
        return super.onSpawnWithEgg(entityLivingData);
    }

    @Override
    protected void addRandomArmor() {
        //super.addRandomArmor(); //don't, they don't wear random armor
        setRandomWeapon();
        //if (amCaptain) { setCurrentItemOrArmor(4, OMINOUS_BANNER.copy()); setEquipmentDropChance(4, 1F); }
        //setEquipmentDropChance(0, .085F); //normal chance.
    }

    protected void setRandomWeapon() {
        setCurrentItemOrArmor(0, new ItemStack(VindicateItems.XBOW));
    }

    public boolean showArms() {
        return getAttackTarget() != null || (getHeldItem() != null && getHeldItem().getItem() instanceof ItemXBow);
    }

    public void setLoadProgress(int i) {
        loadProgress = i;
    }
    public int getLoading() {
        return loadProgress;
    }

    @Override
    public int preferNewItem(int slot, Item oldItem, Item newItem) { //TODO: tags
        if (slot == 0) {
            if (oldItem instanceof ItemXBow) {
                return (newItem instanceof ItemXBow) ? 0 : -1;
            } else {
                return (newItem instanceof ItemXBow) || (newItem instanceof ItemAxe) ? 1 : 0;
            }
        } else if (slot == 4) {
            return EtFuturumWrappium.instance.isEFRBanner(oldItem) ? -1 :
                EtFuturumWrappium.instance.isEFRBanner(newItem) ? 1 : 0;
        }
        return 0;
    }

    @Override
    public int preferNewTag(int slot, ItemStack oldStack, ItemStack newStack) { //Remember, we've already established they're the same item.
        if (slot == 4 && EtFuturumWrappium.instance.isEFRBanner(newStack))
            return EtFuturumWrappium.instance.isEFROminousBanner(oldStack) ? -1 :
                EtFuturumWrappium.instance.isEFROminousBanner(newStack) ? 1 : 0;
        return ITemPreferrer.super.preferNewTag(slot, oldStack, newStack); //TODO: prefer enchanted to named
    }

    public void setCurrentItemOrArmor(int slotIn, ItemStack itemStackIn)
    {
        super.setCurrentItemOrArmor(slotIn, itemStackIn);

        if (!this.worldObj.isRemote && slotIn == 0)
            setCombatStrategy();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        if (ownerUUID != null) tagCompound.setString("LeaderUUID", ownerUUID);
        if (breaksDoors) tagCompound.setBoolean("breaksDoors", true);
        if (amCaptain) tagCompound.setBoolean("Captain", true);
        if (onRaid) tagCompound.setBoolean("onRaid", true);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);
        ownerUUID = tagCompound.getString("LeaderUUID");
        breaksDoors = tagCompound.getBoolean("breaksDoors");
        amCaptain = tagCompound.getBoolean("Captain");
        onRaid = tagCompound.getBoolean("onRaid");

        setCombatStrategy();
    }

    @Override
    protected Item getDropItem() {
        return crossbowWielding ? Items.arrow : Items.emerald;
    }

    @Override
    protected void dropEquipment(boolean playerKilled, int fortuneRanks) {
        super.dropEquipment(playerKilled, fortuneRanks);
        if (amCaptain && Config.omens_in_bottles) entityDropItem(new ItemStack(VindicateItems.OMEN_BOTTLE), 0);
    }

    @Override
    public void onDeath(DamageSource src) {
        super.onDeath(src);

        if (amCaptain && !Config.omens_in_bottles && src instanceof EntityDamageSource attackSrc
            && attackSrc.getEntity() instanceof EntityPlayer) {
            //TODO: inflict Ill Omen with popup
        }
    }

    public boolean isOnRaid() {
        return onRaid;
    }

    //TODO several more functions
    //sounds
}
