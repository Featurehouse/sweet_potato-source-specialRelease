package io.github.teddyxlandlee.sweet_potato.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ManuallyTriggeredCriterion extends AbstractCriterion<ManuallyTriggeredCriterion.Conditions> {
    private final Identifier id;

    protected ManuallyTriggeredCriterion(Identifier id) {
        this.id = id;
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(id, playerPredicate);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity) {
        this.test(serverPlayerEntity, Conditions::matches);
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(Identifier id, EntityPredicate.Extended playerPredicate) {
            super(id, playerPredicate);
        }

        public boolean matches() {
            return true;
        }
    }
}
