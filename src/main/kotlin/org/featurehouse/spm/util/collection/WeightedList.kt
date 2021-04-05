package org.featurehouse.spm.util.collection

import com.google.common.collect.Lists
import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.Dynamic
import com.mojang.serialization.DynamicOps
import java.util.*
import java.util.stream.Stream
import kotlin.math.pow

/**
 * Copied from net.minecraft.util.collection.WeightedList from 1.16.5.
 * @see net.minecraft.class_6032
 */
open class WeightedList<U> private constructor(entries: List<Entry<U>>) {
    public constructor() : this(ArrayList())

    protected val entries: MutableList<Entry<U>>
    private val random: Random = Random()

    init {
        this.entries = Lists.newArrayList(entries)
    }

    companion object {
        fun <U> createCodec(codec: Codec<U>) : Codec<WeightedList<U>>
            = Entry.createCodec(codec).listOf().xmap({entries -> WeightedList(entries)}) { it.entries }
    }

    fun add(item: U, weight: Int) : WeightedList<U> {
        this.entries.add(Entry(item, weight))
        return this
    }

    fun shuffle() = shuffle(this.random)

    fun shuffle(random: Random) : WeightedList<U> {
        this.entries.forEach {
            it.setShuffledOrder(random.nextFloat())
        }
        this.entries.sortWith(Comparator.comparingDouble {
            it.getShuffledOrder()
        })
        return this
    }

    fun isEmpty() = entries.isEmpty()
    fun stream(): Stream<U> = entries.stream().map { it.element }

    fun pickRandom(random: Random) : U
        = this.shuffle(random).stream().findFirst().orElseThrow {RuntimeException()}

    override fun toString()
        = "WeightedList[" + this.entries + "]"

    open class Entry<T>(private val item: T, private val weight: Int) {
        private var shuffledOrder: Double = 0.0

        fun getShuffledOrder() = shuffledOrder
        fun setShuffledOrder(random: Float) {
            this.shuffledOrder = -random.toDouble().pow((1.0F / weight.toFloat()).toDouble())
        }

        val element get() = item

        override fun toString()
            = "" + this.weight + ":" + this.item

        companion object {
          fun <E> createCodec(codec: Codec<E>) = object: Codec<Entry<E>> {
              override fun <T : Any?> decode(ops: DynamicOps<T>, input: T): DataResult<Pair<Entry<E>, T>> {
                  val dynamic = Dynamic(ops, input)
                  return dynamic["data"].flatMap(codec::parse).map {
                      Entry(it, dynamic.get("weight").asInt(1))
                  }.map { Pair.of(it, ops.empty()) }
              }

              override fun <T : Any?> encode(input: Entry<E>, ops: DynamicOps<T>, prefix: T): DataResult<T>
                = ops.mapBuilder()
                      .add("weight", ops.createInt(input.weight))
                      .add("data", codec.encodeStart(ops, input.item))
                      .build(prefix)
          }
        }
    }
}