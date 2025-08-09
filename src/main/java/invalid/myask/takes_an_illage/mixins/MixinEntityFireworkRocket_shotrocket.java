package invalid.myask.takes_an_illage.mixins;

import invalid.myask.takes_an_illage.api.CrossbowHelper;
import invalid.myask.takes_an_illage.api.IBlowUp;
import invalid.myask.takes_an_illage.entities.ProjectileFireworkRocket;
import invalid.myask.takes_an_illage.utils.FireworksExploder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

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
