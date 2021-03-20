package org.featurehouse.spm.structures

import net.minecraft.server.world.ServerWorld
import net.minecraft.structure.Structure
import net.minecraft.structure.StructurePlacementData
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.featurehouse.spm.SPMFools

val GLINT_PIG_HELL_ID = Identifier(SPMFools.FOOLS_ID, "pig_hell")

/** start point of the structure. */
private val START_POINT = BlockPos(-5, 12, -4)

fun fillPigHell(serverWorld: ServerWorld) : Boolean {
    val structure: Structure = serverWorld.structureManager.getStructure(GLINT_PIG_HELL_ID) ?: return false
    val structurePlacementData = StructurePlacementData()
    structure.place(serverWorld, START_POINT, structurePlacementData, serverWorld.random)
    return true
}