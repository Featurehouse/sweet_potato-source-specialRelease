package org.featurehouse.spm.util.tick

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

@FunctionalInterface
interface ITickable {
    fun tick(world: World, pos: BlockPos, state: BlockState)
}