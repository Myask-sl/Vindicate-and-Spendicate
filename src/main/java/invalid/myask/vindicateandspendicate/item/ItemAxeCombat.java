package invalid.myask.vindicateandspendicate.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemAxeCombat extends ItemAxe implements VindicItem {
    public ItemAxeCombat(ToolMaterial mat) {
        super(mat);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
        stack.damageItem(1, p_77644_3_);
        return true;
    }
}
