package org.featurehouse.spm.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.featurehouse.spm.SPMFools;
import org.jetbrains.annotations.Nullable;

public class BugjumpCriterion extends AbstractCriterion<BugjumpCriterion.Conditions> {
    private final Identifier id;
    public BugjumpCriterion(Identifier id) {
        this.id = id;
    }

    @Override
    protected Conditions conditionsFromJson(@Nullable JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(id, playerPredicate);
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, conditions -> conditions.matches(player));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private static final LocationPredicate LOCATION;

        public Conditions(Identifier id, EntityPredicate.Extended playerPredicate) {
            super(id, playerPredicate);
        }

        public boolean matches(ServerPlayerEntity player) {
            boolean location = LOCATION.test(player.getServerWorld(), player.getX(), player.getY(), player.getZ());
            boolean status   = player.hasStatusEffect(SPMFools.BUGJUMP);
            return location && status;
        }

        static {
            LOCATION = LocationPredicate.Builder.create()
                    .method_35278(NumberRange.FloatRange.atLeast(340.0F))
                    .build();
        }
    }
}
