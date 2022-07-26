package io.github.maliciousfiles.grapplinghookfix.mixin;

import com.yyon.grapplinghook.client.ClientControllerManager;
import io.github.maliciousfiles.grapplinghookfix.GrapplingHookFix;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(ClientControllerManager.class)
public class DoubleJumpMixin {

    @Inject(at=@At("HEAD"),method="wearingDoubleJumpEnchant", cancellable = true, remap = false)
    private void wearingDoubleJump(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.allowFlying && !GrapplingHookFix.doubleJumpActive) cir.setReturnValue(false);
    }
}
