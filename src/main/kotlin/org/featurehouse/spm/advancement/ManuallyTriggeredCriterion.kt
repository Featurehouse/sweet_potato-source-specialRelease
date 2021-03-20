package org.featurehouse.spm.advancement

import com.google.gson.JsonObject
import net.minecraft.advancement.criterion.AbstractCriterion
import net.minecraft.advancement.criterion.AbstractCriterionConditions
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

class ManuallyTriggeredCriterion(private val id: Identifier) : AbstractCriterion<AbstractCriterionConditions>() {
    override fun getId(): Identifier = id
    override fun conditionsFromJson(obj: JsonObject?, playerPredicate: EntityPredicate.Extended, predicateDeserializer: AdvancementEntityPredicateDeserializer?): AbstractCriterionConditions
        = object : AbstractCriterionConditions(id, playerPredicate) {}

    fun trigger(serverPlayerEntity: ServerPlayerEntity) = this.test(serverPlayerEntity) { true }
}