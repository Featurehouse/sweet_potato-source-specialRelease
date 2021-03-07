package org.featurehouse.spm.mixin.global;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends TameableShoulderEntity {
    @Unique protected boolean spmfools21_isPigeon;

    @Deprecated
    private ParrotEntityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "readCustomDataFromTag")
    private void onReadNbt(CompoundTag root, CallbackInfo ci) {
        CompoundTag compoundTag = root.getCompound("spmfools21:info");
        spmfools21_isPigeon = compoundTag.getBoolean("IsPigeon");   // default false
    }

    @Inject(at = @At("RETURN"), method = "writeCustomDataToTag")
    private void onWriteNbt(CompoundTag root, CallbackInfo ci) {
        CompoundTag compoundTag = new CompoundTag();
        root.putBoolean("IsPigeon", spmfools21_isPigeon);
        root.put("spmfools21:info", compoundTag);
    }

    @Deprecated //@Accessor("TAMING_INGREDIENTS")
    static Set<Item> getTamingIngredients() {
        throw new AssertionError("Mixin");
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
