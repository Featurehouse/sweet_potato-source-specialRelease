package org.featurehouse.spm.mixin.global;

import net.minecraft.server.network.ServerPlayerEntity;
import org.featurehouse.spm.SPMFools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(
            at = @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target = "Lnet/minecraft/advancement/criterion/TickCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;)V"
            ), method = "tick()V"
    )
    private void onTickCriterion(CallbackInfo ci) {
        SPMFools.BUGJUMP_CRITERION.trigger((ServerPlayerEntity) (Object) this);
    }
}
