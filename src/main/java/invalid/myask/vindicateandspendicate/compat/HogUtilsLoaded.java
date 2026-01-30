package invalid.myask.vindicateandspendicate.compat;

import net.minecraft.item.Item;
//import roadhog360.hogutils.api.hogtags.helpers.ItemTags; //TODO: dep, uncomment

public class HogUtilsLoaded extends HogTagWrap {
    @Override
    public int taggedPillagerWeaponPreference(Item item) {
        int result = 0;
//        if (ItemTags.hasTag(item, "pillager_disliked_weapons")) result = -100;
//        else if (ItemTags.hasTag(item, "pillager_preferred_weapons")) result = 100;
//      else if (ItemTags.hasTag(item, "pillager_accepted_weapons")) result = 50; //Axe, here intended second-best
        return result;
    }

    @Override
    public int taggedVindicatorWeaponPreference(Item item) {
        int result = 0;
//        if (ItemTags.hasTag(item, "vindicator_disliked_weapons")) result = -100;
//        else if (ItemTags.hasTag(item, "vindicator_preferred_weapons")) result = 100;
//        else if (ItemTags.hasTag(item, "vindicator_accepted_weapons")) result = 50; //XBow, here intended second-best
        return result;
    }
}
