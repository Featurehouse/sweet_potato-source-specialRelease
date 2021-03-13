package org.featurehouse.spm.util.properties.objects

import net.minecraft.state.property.Property
import java.util.*

//@Deprecated("Stopped.", level = DeprecationLevel.WARNING)
open class StringProperty(name: String, private val values: MutableCollection<String>) : Property<String>(name, String::class.java) {
    override fun getValues(): MutableCollection<String> = values

    override fun name(value: String?) : String = value?: ""

    override fun parse(name: String?): Optional<String> =
        Optional.ofNullable(name)

    constructor(name: String, vararg values0: String) : this(name, values0.toHashSet())
}