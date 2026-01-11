package invalid.myask.vindicateandspendicate.entity.illager;

import invalid.myask.vindicateandspendicate.Config;
import invalid.myask.vindicateandspendicate.VindicateItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityVindicator extends EntityPillager {
    public EntityVindicator(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(12);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(Config.vindicator_base_damage);
    }

    @Override
    protected void addRandomArmor() {
        super.addRandomArmor();
        setCurrentItemOrArmor(0, new ItemStack(Items.iron_axe));
    }

    @Override
    public boolean isSprinting() {
        return true;
    }
}
