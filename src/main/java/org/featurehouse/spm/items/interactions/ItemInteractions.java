package org.featurehouse.spm.items.interactions;

import org.featurehouse.spm.entity.damage.SteveSpawnDamageSource;
import org.featurehouse.spm.items.SteveSpawnEggItem;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.ActionResult;

public class ItemInteractions {
    public static void init() {
        UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult) -> {
            if (!world.isClient) {
                if (playerEntity.getStackInHand(hand).getItem() instanceof SteveSpawnEggItem)
                    return SteveSpawnEggItem.onUse(world, blockHitResult.getPos());
                return ActionResult.PASS;
            } else return ActionResult.SUCCESS;
        });
        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (world.isClient) return ActionResult.SUCCESS;
            if (playerEntity.getStackInHand(hand).getItem() instanceof SteveSpawnEggItem
                && entity instanceof MobEntity) {
                entity.damage(SteveSpawnDamageSource.of(), Float.MAX_VALUE);
                return SteveSpawnEggItem.onUse(world, entity.getPos());
            }
            return ActionResult.PASS;
        });
    }
}
