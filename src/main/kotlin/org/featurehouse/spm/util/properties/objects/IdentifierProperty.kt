package org.featurehouse.spm.util.properties.objects

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.state.property.Property
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry
import java.util.*
import java.util.stream.Collectors

@Deprecated("bug", level = DeprecationLevel.WARNING)
open class IdentifierProperty private constructor(name: String, private val col: MutableCollection<Identifier>) : Property<Identifier>(name, Identifier::class.java) {
    override fun getValues(): MutableCollection<Identifier> = col

    override fun name(value: Identifier?): String = value?.toString() ?: ""

    override fun parse(name: String?): Optional<Identifier> {
        val id = Identifier(name)
        var contains = false
        col.forEach {colId -> if (colId == id) contains = true }
        return if (name == null || !contains)
            Optional.empty()
        else Optional.of(id)
    }

    @Deprecated("bug", level = DeprecationLevel.ERROR)
    companion object {
        fun ofBlocks(name: String, tag: Tag<Block>) = ofRegistry(name, tag, Registry.BLOCK)
        fun ofItems (name: String, tag: Tag<Item> ) = ofRegistry(name, tag, Registry.ITEM)

        private fun <T> ofRegistry(name: String, tag: Tag<T>, registry: DefaultedRegistry<T>)
             = IdentifierProperty(name, tag.values().stream().map(registry::getId).collect(Collectors.toSet()))
    }
}