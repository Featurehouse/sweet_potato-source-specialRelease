package org.featurehouse.spm.item

import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtDouble
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import org.featurehouse.spm.SPMFools
import org.featurehouse.spm.util.properties.objects.ItemSettings

object HorseBucketItem : Item(ItemSettings.MISC_ONE) {
    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        if (context?.world?.isClient != false) return ActionResult.SUCCESS

        if (context.world.random.nextDouble() < 0.10) {
            val player: PlayerEntity = context.player ?: return ActionResult.CONSUME
            context.world.playSound(null, player.x, player.y, player.z, SPMFools.BUCKET_HUNGRY, SoundCategory.AMBIENT,
            1.0F, 1.0F)
            player.getStackInHand(context.hand)?.decrement(1)
            return ActionResult.CONSUME
        }

        val blockPos:  BlockPos = context.blockPos
        val blockPos2: BlockPos = blockPos.offset(context.side, 1)
        val compoundTag = context.stack.orCreateTag
        if (compoundTag.contains("EntityTag", NbtType.COMPOUND)) {
            val entityTag = compoundTag.getCompound("EntityTag")
            val id = Identifier(entityTag.getString("id"))
            val listTag = NbtList()
            listTag.add(NbtDouble.of(blockPos2.x + 0.5))
            listTag.add(NbtDouble.of(blockPos2.y.toDouble() ))
            listTag.add(NbtDouble.of(blockPos2.z + 0.5))
            entityTag.put("Pos", listTag)
            val entityType: EntityType<*> = Registry.ENTITY_TYPE[id]
            entityType.spawnFromItemStack(context.world as ServerWorld, context.stack, context.player, blockPos,
                    SpawnReason.SPAWN_EGG, true, blockPos==blockPos2 && context.side === Direction.UP
            ) ?: return ActionResult.PASS

        }
        return ActionResult.CONSUME
    }
}