package org.featurehouse.spm.mixin

import net.minecraft.client.MinecraftClient
import net.minecraft.client.world.ClientWorld
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(ClientWorld::class)
interface ClientWorldAccessor{
    @Accessor
    fun getClient() : MinecraftClient
}