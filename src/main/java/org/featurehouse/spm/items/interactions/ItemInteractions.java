package org.featurehouse.spm.items.interactions;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
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
            }
            return ActionResult.PASS;
        });
        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (world.isClient) return ActionResult.PASS;
            if (playerEntity.getStackInHand(hand).getItem() instanceof SteveSpawnEggItem
                && entity instanceof MobEntity) {
                entity.damage(SteveSpawnDamageSource.of(), Float.MAX_VALUE);
                return SteveSpawnEggItem.onUse(world, entity.getPos());
            }
            return ActionResult.PASS;
        });
        AttackBlockCallback.EVENT.register((playerEntity, world, hand, blockPos, direction) -> {
            if (world.isClient) return ActionResult.PASS;
            ItemStack stack = playerEntity.getStackInHand(hand);
            ServerWorld serverWorld = (ServerWorld) world;
            if (stack.getItem() instanceof MicrohammerItem) {
                BlockState state = world.getBlockState(blockPos);
                if (state.isIn(SPMFools.MICROHAMMER_BREAKABLE)) {
                    stack.damage(1, playerEntity, e -> {});
                    return world.setBlockState(blockPos, SPMFools.CRACKED_ROCK.get(state.getBlock()))
                            ? ActionResult.CONSUME : ActionResult.PASS;
                } else if (state.getBlock() instanceof CrackedRockBlock) {
                    stack.damage(1, playerEntity, e -> {});
                    if (world.random.nextFloat() < 0.3F) {
                        world.removeBlock(blockPos, false);
                        LootContext.Builder builder = new LootContext.Builder(serverWorld)
                                .random(world.getRandom())
                                .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
                                .parameter(LootContextParameters.TOOL, stack)
                                .optionalParameter(LootContextParameters.THIS_ENTITY, playerEntity);
                        LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, world.getBlockState(blockPos))
                                .build(LootContextTypes.BLOCK);
                        LootTable lootTable = serverWorld.getServer().getLootManager().getTable(LootTables.CRACKED_ROCK);
                        lootTable.generateLoot(lootContext, itemStack -> {
                            Block.dropStack(world, blockPos, itemStack);
                            state.onStacksDropped(serverWorld, blockPos, itemStack);
                        });
                    }
                }
            } return ActionResult.PASS;
        });
        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (world.isClient) return ActionResult.PASS;
            if (entity instanceof HorseBaseEntity && playerEntity.isSneaking()) {
                ItemStack originBucket = playerEntity.getStackInHand(hand);
                if (originBucket.getItem() == Items.BUCKET) {
                    entity.removeAllPassengers();
                    NbtCompound tag = new NbtCompound();
                    entity.saveSelfNbt(tag);
                    tag.remove("Pos");
                    tag.remove("UUID");
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    if (!playerEntity.getAbilities().creativeMode)
                        originBucket.decrement(1);
                    ItemStack horseBucket = new ItemStack(SPMFools.HORSE_BUCKET);
                    horseBucket.getOrCreateTag().put("EntityTag", tag);
                    if (!playerEntity.getInventory().insertStack(horseBucket))
                        playerEntity.dropItem(horseBucket, true);
                    return ActionResult.CONSUME;
                }
            } return ActionResult.PASS;
        });
    }
}
