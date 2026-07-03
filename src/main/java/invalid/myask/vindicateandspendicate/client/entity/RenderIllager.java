package invalid.myask.vindicateandspendicate.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import invalid.myask.vindicateandspendicate.VindicateAndSpendicate;
import invalid.myask.vindicateandspendicate.entity.illager.EntityPillager;
import invalid.myask.vindicateandspendicate.entity.illager.EntityVindicator;

@SideOnly(Side.CLIENT)
public class RenderIllager extends RenderLiving {
    public static final RenderIllager instance = new RenderIllager (new ModelIllager(), 0.5F);
    public static final ResourceLocation PILLAGER_TEX = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/illager/pillager.png");
    public static final ResourceLocation VINDICATOR_TEX = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/illager/vindicator.png");
    public static final ResourceLocation TUX_TEX = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/illager/vindicator_tux.png");

    public static final ResourceLocation DEFAULT_TEX = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/wanderer/trillager.png");
    public static final ResourceLocation TRILLAGER_TEX = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/wanderer/trillager2.png");
    public static final ResourceLocation TRADER_TEX = new ResourceLocation(VindicateAndSpendicate.MODID, "textures/entity/wanderer/wandering_trader.png");

    public RenderIllager(ModelIllager modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity e) {
        if (e instanceof EntityPillager pillager) {
            if (e instanceof EntityVindicator vind) {
                if (vind.isWearingTux()) return TUX_TEX;
                else return VINDICATOR_TEX;
            } else return PILLAGER_TEX;
        } // else if (e instanceof EntityWanderingTrader)
        return DEFAULT_TEX;
    }
}
