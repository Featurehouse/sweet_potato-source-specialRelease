package org.featurehouse.spm.blocks

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.BlockView
import net.minecraft.world.World
import org.featurehouse.spm.SPMFools

/**
 * @see net.minecraft.block.SlimeBlock
 */
class ElevatorBlock(settings: Settings) : Block(settings) {
    override fun onLandedUpon(world: World?, pos: BlockPos?, entity: Entity, distance: Float) {
        entity.handleFallDamage(distance, 0.0F)
    }

    override fun onEntityLand(world: BlockView, entity: Entity) {
        //TODO calculate velocity
        super.onEntityLand(world, entity)
        if (entity is LivingEntity) bounce(entity)
    }

    override fun onSteppedOn(world: World, pos: BlockPos, entity: Entity) {
        //TODO calculate velocity
        super.onSteppedOn(world, pos, entity)
        if (entity is LivingEntity) bounce(entity)
    }

    private fun bounce(entity: LivingEntity) {
        val vec3d: Vec3d = entity.velocity
        entity.setVelocity(vec3d.x, 12.95, vec3d.z)
        entity.addStatusEffect(StatusEffectInstance(SPMFools.BUGJUMP, 100))
    }
}