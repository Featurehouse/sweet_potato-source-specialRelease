package org.featurehouse.spm.mixin.global;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.featurehouse.spm.util.Pigeon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends TameableShoulderEntity implements Pigeon {
    @Unique protected boolean spmfools21_isPigeon;

    @Override
    public boolean spmfools21_isPigeon() {
        return spmfools21_isPigeon;
    }

    @Override
    public void spmfools21_setPigeon(boolean pigeon) {
        spmfools21_isPigeon = pigeon;
    }

    @Deprecated
    private ParrotEntityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "readCustomDataFromNbt")
    private void onReadNbt(NbtCompound root, CallbackInfo ci) {
        NbtCompound compoundTag = root.getCompound("spmfools21:info");
        spmfools21_isPigeon = compoundTag.getBoolean("IsPigeon");   // default false
    }

    @Inject(at = @At("RETURN"), method = "writeCustomDataToNbt")
    private void onWriteNbt(NbtCompound root, CallbackInfo ci) {
        NbtCompound compoundTag = new NbtCompound();
        root.putBoolean("IsPigeon", spmfools21_isPigeon);
        root.put("spmfools21:info", compoundTag);
    }

    @Inject(at = @At("RETURN"), method = "damage")
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && this.spmfools21_isPigeon && !this.world.isClient) {
            ((ServerWorld) world).getServer().getPlayerManager().sendToAround(null, this.getX(), this.getY(), this.getZ(),
                    16.0F, world.getRegistryKey(), new GameMessageS2CPacket(
                            new TranslatableText("death.spmfools21.parrot.pigeon", this.getPos().toString()), MessageType.SYSTEM, Util.NIL_UUID));
        }
    }
}
