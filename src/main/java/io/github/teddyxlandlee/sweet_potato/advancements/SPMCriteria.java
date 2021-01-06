package io.github.teddyxlandlee.sweet_potato.advancements;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.util.Identifier;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;
import static net.minecraft.advancement.criterion.Criteria.VALUES;

public final class SPMCriteria {
    private SPMCriteria() {}

    public static <T extends Criterion<?>> T register(T object) {
        if (VALUES.containsKey(object.getId())) {
            throw new IllegalArgumentException("Duplicate criterion id " + object.getId());
        } else {
            VALUES.put(object.getId(), object);
            return object;
        }
    }

    public static final ManuallyTriggeredCriterion UNWRAP_GIFT = new ManuallyTriggeredCriterion(new Identifier(MODID, "unwrap_gift"));
    public static final ManuallyTriggeredCriterion CHRISTMATATO_SUCCESS = new ManuallyTriggeredCriterion(new Identifier(MODID, "christmatato_success"));
}
