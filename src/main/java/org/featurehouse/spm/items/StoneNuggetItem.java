package org.featurehouse.spm.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.featurehouse.spm.SPMFools;
import org.jetbrains.annotations.Nullable;

public class StoneNuggetItem extends Item {
    public StoneNuggetItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!(entity instanceof LivingEntity)) return;
        LivingEntity livingEntity = (LivingEntity) entity;

        @Nullable
        StatusEffectInstance statusEffectInstance = livingEntity.getActiveStatusEffects().get(SPMFools.DEEAM);
        if (statusEffectInstance != null && statusEffectInstance.getDuration() <= 1) {
            stack.decrement(1);
        }

        if (!world.isClient && world.getTime() % 10 == 0) {
            if (world.getRandom().nextDouble() < 0.25D) {
                if (!livingEntity.hasStatusEffect(SPMFools.DEEAM)) {
                    if (entity instanceof ServerPlayerEntity) {
                        ((ServerPlayerEntity) entity).networkHandler.sendPacket(new GameMessageS2CPacket(
                                new TranslatableText("message.spmfools21.stone_nugget.not_deeam"),
                                MessageType.SYSTEM, Util.NIL_UUID
                        ));
                    }
                    entity.kill();
                }
            } else {
                livingEntity.addStatusEffect(new StatusEffectInstance(SPMFools.DEEAM, 301));
            }
        }
    }
}
