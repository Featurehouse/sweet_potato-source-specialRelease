package org.featurehouse.spm.item

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.TranslatableText
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import org.featurehouse.spm.SPMFools
import org.featurehouse.spm.advancement.ManuallyTriggeredCriterion

class NullPointerExceptionItem(settings: Settings) : Item(settings) {
    companion object {
        private val CLIENT_CRASH_ACCEPT = Identifier(SPMFools.FOOLS_ID, "client_crash_accept")
        fun getClientCrashAccept() = CLIENT_CRASH_ACCEPT

        private val K_STACK
            get() = ItemStack(SPMFools.KOTLIN)
        val criterion = ManuallyTriggeredCriterion(Identifier(SPMFools.FOOLS_ID, "consume_kotlin"))
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack: ItemStack = user.getStackInHand(hand)
        return if (world.isClient) TypedActionResult.success(itemStack) else {
            if (!user.inventory.contains(K_STACK)) {
                itemStack.setCustomName(TranslatableText("item.spmfools21.nullpointerexception.told_you"))
                ServerPlayNetworking.send(user as ServerPlayerEntity, CLIENT_CRASH_ACCEPT, PacketByteBufs.empty())
            } else {
                for (stack in user.inventory.main + user.inventory.offHand + user.inventory.armor) {
                    if (stack.isItemEqualIgnoreDamage(K_STACK)) {
                        stack.decrement(1)
                        break
                    }
                }
                criterion.trigger(user as ServerPlayerEntity)
            }
            TypedActionResult.consume(itemStack)
        }
    }
}