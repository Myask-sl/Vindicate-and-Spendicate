package invalid.myask.vindicateandspendicate.compat;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.entities.EntityTippedArrow;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EtFuturumLoaded extends EtFuturumWrappium {
    static final Item BANNER = ModBlocks.BANNER.getItem();
    private static final NBTTagList OMINOUS_PATTERNLIST = new NBTTagList();
    static {
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("lozenge", MapColor.cyanColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("stripe_bottom", MapColor.silverColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("stripe_center", MapColor.grayColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("border", MapColor.silverColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("stripe_middle", MapColor.blackColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("half_horizontal", MapColor.silverColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("circle", MapColor.silverColor.colorValue));
        OMINOUS_PATTERNLIST.appendTag(patternColorPair("border", MapColor.blackColor.colorValue));
    }

    private static NBTTagCompound patternColorPair (String pattern, int color) {
        NBTTagCompound result = new NBTTagCompound();
        result.setString("Pattern", pattern);
        result.setInteger("Color", color);
        return result;
    }

    @Override
    public int getFuturumLoadium(Item loadItem) {
        if (loadItem == ModItems.TIPPED_ARROW.get()) return 2;
        // if (loadItem == ModItems.SPECTRAL_ARROW.get()) return 1; //TODO: when spectral arrows exist
        return super.getFuturumLoadium(loadItem);
    }

    public Entity fletchTippedArrow (World world, EntityLivingBase user, ItemStack ammo) {
        EntityTippedArrow shot = new EntityTippedArrow(world, user, 2);
        shot.setArrow(ammo);
        return shot;
    }

    public Entity fletchTippedArrow (World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        EntityTippedArrow shot = new EntityTippedArrow(world, user, target, 1.6F,
            (float) (14 - world.difficultySetting.getDifficultyId() * 4));
        shot.setArrow(ammo);
        return shot;
    }

/* //TODO: when spectral arrows exist...don't fall through
    public Entity fletchSpectralArrow (World world, EntityLivingBase user, ItemStack ammo){
        EntityArrow shot = new EntitySpectralArrow(world, user, 2);
        //shot.setArrow(ammo);
        return shot;
    }

    public Entity fletchSpectralArrow (World world, EntityLivingBase user, EntityLivingBase target, ItemStack ammo) {
        EntityArrow shot = new EntitySpectralArrow(world, user, target, 1.6F,
            (float) (14 - world.difficultySetting.getDifficultyId() * 4));
        //shot.setArrow(ammo);
        return shot;
    }*/

    @Override
    public boolean isEFRBanner(Item item) {
        return item == BANNER;
    }

    @Override
    public boolean isEFROminousBanner(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound n = stack.getTagCompound();
            if (n.hasKey("BlockEntityTag")) {
                n = n.getCompoundTag("BlockEntityTag");
                if (n.hasKey("Base") && n.getInteger("Base") == 0) { //white
                    return OMINOUS_PATTERNLIST.equals(n.getTagList("Patterns", 10));
                }
            }
        }
        return false;
    }
}
