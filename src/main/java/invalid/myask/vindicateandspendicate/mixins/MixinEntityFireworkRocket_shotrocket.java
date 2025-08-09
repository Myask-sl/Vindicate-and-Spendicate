package invalid.myask.vindicateandspendicate.mixins;

import invalid.myask.vindicateandspendicate.api.IBlowUp;
import invalid.myask.vindicateandspendicate.utils.FireworksExploder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFireworkRocket.class)
public abstract class MixinEntityFireworkRocket_shotrocket extends Entity implements IBlowUp {
    public MixinEntityFireworkRocket_shotrocket(World worldIn) {
        super(worldIn);
    }

    @Inject(method = "onUpdate",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setEntityState(Lnet/minecraft/entity/Entity;B)V"))
    private void explosionsHurt (CallbackInfo cbi) {
        FireworksExploder.explodeForDamageMaybe(this, this.dataWatcher.getWatchableObjectItemStack(8));
    }
}
