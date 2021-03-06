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

open class IdentifierProperty private constructor(name: String, private val col: MutableCollection<Identifier>) : Property<Identifier>(name, Identifier::class.java) {
    override fun getValues(): MutableCollection<Identifier> = col

    override fun name(value: Identifier?): String = value?.toString() ?: ""

    override fun parse(name: String?): Optional<Identifier> =
        if (name==null) Optional.empty() else Optional.of(Identifier(name))

    companion object {
        fun ofBlocks(name: String, tag: Tag<Block>) = ofRegistry(name, tag, Registry.BLOCK)
        fun ofItems (name: String, tag: Tag<Item> ) = ofRegistry(name, tag, Registry.ITEM)

        fun <T> ofRegistry(name: String, tag: Tag<T>, registry: DefaultedRegistry<T>)
             = IdentifierProperty(name, tag.values().stream().map(registry::getId).collect(Collectors.toSet()))
    }
}