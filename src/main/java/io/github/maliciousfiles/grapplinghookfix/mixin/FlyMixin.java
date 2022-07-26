package io.github.maliciousfiles.grapplinghookfix.mixin;

import com.yyon.grapplinghook.client.ClientControllerManager;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@OnlyIn(Dist.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class FlyMixin {

    @Redirect(method="livingTick", at=@At(value="INVOKE", target="Lnet/minecraft/client/entity/player/ClientPlayerEntity;isSwimming()Z", ordinal=2))
    private boolean getLivingTick(ClientPlayerEntity instance) {
        if (ClientControllerManager.instance.wearingDoubleJumpEnchant(instance)) return true;

        return instance.isSwimming();
    }
}
