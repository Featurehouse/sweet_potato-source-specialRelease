package org.featurehouse.spm.structures

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.world.HeightLimitView
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.ChunkRandom
import net.minecraft.world.gen.chunk.ChunkGenerator

fun startPos(chunkPos: ChunkPos, chunkGenerator: ChunkGenerator, heightLimitView: HeightLimitView, chunkRandom: ChunkRandom) : BlockPos {
    val x = chunkPos.startX + chunkRandom.nextInt(16)
    val z = chunkPos.startZ + chunkRandom.nextInt(16)
    val y = chunkGenerator.getHeightOnGround(x, z, Heightmap.Type.WORLD_SURFACE_WG, heightLimitView)
    return BlockPos(x, y, z)
}