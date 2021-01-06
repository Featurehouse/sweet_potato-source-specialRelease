package io.github.teddyxlandlee.sweet_potato.advancements;

import com.google.gson.JsonObject;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.items.ChristmasSockItem;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

@Deprecated
public class ObtainBundledSockCriterion extends AbstractCriterion<ObtainBundledSockCriterion.Conditions> {
    private static final Identifier ID = new Identifier(MODID, "obtain_bundled_sock");

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, EntityPredicate.Extended playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new Conditions(playerPredicate);
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player, ItemStack stack) {
        this.test(player, conditions -> conditions.matches(stack));
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(EntityPredicate.Extended player) {
            super(ID, player);
        }

        public static Conditions create() {
            return new Conditions(EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ItemStack stack) {
            return stack.getItem() == SPMMain.PEEL && ChristmasSockItem.bundled(stack);
        }
    }
}
