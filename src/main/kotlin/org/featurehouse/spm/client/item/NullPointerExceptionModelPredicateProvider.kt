package org.featurehouse.spm.client.item

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.item.ModelPredicateProvider
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.decoration.ItemFrameEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import org.featurehouse.spm.SPMFools
import kotlin.math.atan2

@Environment(EnvType.CLIENT)
class NullPointerExceptionModelPredicateProvider : ModelPredicateProvider {
    private val value = AngleInterpolator()
    private val speed = AngleInterpolator()

    override fun call(stack: ItemStack, world: ClientWorld?, livingEntity: LivingEntity?): Float {
        val entity: Entity? = livingEntity ?: stack.holder
        entity ?: return 0.0F
        var clientWorld: ClientWorld? = world
        if (clientWorld == null && entity.world is ClientWorld)
            clientWorld = entity.world as ClientWorld

        val blockPos: BlockPos? = if (clientWorld?.registryKey == World.OVERWORLD) SPMFools.GLINT_PIG_POS_CENTER else null
        val l: Long = clientWorld?.time ?: Long.MIN_VALUE
        if (blockPos != null && entity.pos.squaredDistanceTo(blockPos.x + .5, entity.pos.y, blockPos.z + .5) >= 9.999999747378752E-6) {
            val bl: Boolean = livingEntity is PlayerEntity && livingEntity.isMainPlayer
            var e = 0.0
            when {
                bl -> e = (livingEntity as LivingEntity).yaw.toDouble()
                entity is ItemFrameEntity -> e = this.getItemFrameAngleOffset(entity)
                entity is ItemEntity -> e = (180.0F - entity.method_27314(0.5F) / 6.2831855F * 360.0F).toDouble()
                livingEntity != null -> e = livingEntity.bodyYaw.toDouble()
            }

            e = MathHelper.floorMod(e / 360.0, 1.0)
            val f: Double = this.getAngleToPos(Vec3d.ofCenter(blockPos), entity) / 6.2831854820251465
            val h: Double = if (bl) {
                if (this.value.shouldUpdate(l))
                    this.value.update(l, 0.5 - (e - 0.25))
                f + this.value.value
            } else 0.5 - (e - 0.25 - f)

            return MathHelper.floorMod(h.toFloat(), 1.0F)
        } else {
            if (this.speed.shouldUpdate(l))
                this.speed.update(l, Math.random())
            val d: Double = this.speed.value + stack.hashCode() / (Int.MAX_VALUE.toFloat())
            return MathHelper.floorMod(d.toFloat(), 1.0F)
        }
    }

    private fun getItemFrameAngleOffset(itemFrame: ItemFrameEntity) : Double {
        val direction: Direction = itemFrame.horizontalFacing
        val i: Int = if (direction.axis.isVertical) 90 * direction.direction.offset() else 0
        return MathHelper.wrapDegrees(180 + direction.horizontal * 90 + itemFrame.rotation * 45 + i).toDouble()
    }

    private fun getAngleToPos(pos: Vec3d, entity: Entity) : Double =
            atan2(pos.z - entity.z, pos.x - entity.x)
}

@Environment(EnvType.CLIENT)
private class AngleInterpolator {
    var value: Double = 0.0
    var speed: Double = 0.0
    var lastUpdateTime: Long = 0L

    fun shouldUpdate(time: Long) = lastUpdateTime != time

    fun update(time: Long, d: Double) {
        lastUpdateTime = time
        var e: Double = d - this.value
        e = MathHelper.floorMod(e + 0.5, 1.0) - .5
        speed += e * .1
        speed *= .8
        value = MathHelper.floorMod(value + speed, 1.0)
    }
}