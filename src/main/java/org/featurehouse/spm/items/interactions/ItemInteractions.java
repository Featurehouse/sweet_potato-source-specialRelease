package org.featurehouse.spm.items.interactions;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import org.featurehouse.spm.SPMFools;
import org.featurehouse.spm.blocks.CrackedRockBlock;
import org.featurehouse.spm.entity.damage.SteveSpawnDamageSource;
import org.featurehouse.spm.items.MicrohammerItem;
import org.featurehouse.spm.items.SteveSpawnEggItem;
import org.featurehouse.spm.loot.LootTables;

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
        AttackBlockCallback.EVENT.register((playerEntity, world, hand, blockPos, direction) -> {
            if (world.isClient) return ActionResult.PASS;
            if (playerEntity.getStackInHand(hand).getItem() instanceof MicrohammerItem) {
                BlockState state = world.getBlockState(blockPos);
                if (state.isIn(SPMFools.MICROHAMMER_BREAKABLE)) {
                    return world.setBlockState(blockPos, SPMFools.CRACKED_ROCK.get(state.getBlock()))
                            ? ActionResult.CONSUME : ActionResult.PASS;
                } else if (state.getBlock() instanceof CrackedRockBlock) {
                    if (world.random.nextFloat() < 0.3F) {
                        ServerWorld serverWorld = (ServerWorld) world;
                        world.breakBlock(blockPos, false, playerEntity);
                        LootTable lootTable = serverWorld.getServer().getLootManager().getTable(LootTables.CRACKED_ROCK);
                        LootContext context = new LootContext.Builder(serverWorld)
                                .random(world.random)
                                .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
                                .build(LootContextTypes.BLOCK);
                        lootTable.generateLoot(context, itemStack -> world.spawnEntity(new ItemEntity(world,
                                blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D,
                                itemStack)));
                    }
                }
            } return ActionResult.PASS;
        });
        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (world.isClient) return ActionResult.SUCCESS;
            if (entity instanceof HorseBaseEntity && playerEntity.isSneaking()) {
                ItemStack originBucket = playerEntity.getStackInHand(hand);
                if (originBucket.getItem() == Items.BUCKET) {
                    entity.removeAllPassengers();
                    CompoundTag tag = new CompoundTag();
                    entity.saveSelfToTag(tag);
                    entity.remove();
                    ItemStack horseBucket = new ItemStack(SPMFools.HORSE_BUCKET);
                    horseBucket.getOrCreateTag().put("EntityTag", tag);
                    if (!playerEntity.inventory.insertStack(horseBucket))
                        playerEntity.dropItem(horseBucket, true);
                    return ActionResult.CONSUME;
                }
            } return ActionResult.PASS;
        });
    }
}
