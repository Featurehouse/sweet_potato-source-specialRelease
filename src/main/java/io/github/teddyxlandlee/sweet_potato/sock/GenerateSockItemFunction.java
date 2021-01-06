package io.github.teddyxlandlee.sweet_potato.sock;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import io.github.teddyxlandlee.sweet_potato.SPMXmas;
import io.github.teddyxlandlee.sweet_potato.items.ChristmasSockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;

import java.util.List;

public class GenerateSockItemFunction extends ConditionalLootFunction {
    protected GenerateSockItemFunction(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        Item item = stack.getItem();
        if (!(item instanceof ChristmasSockItem))
            throw new UnsupportedOperationException("Not christmas sock but generate sock item?!");
        LootTable sockInvLootTable = SockInventoryLootTables.fromId(context.getWorld(), SockInventoryLootTables.SOCK_INVENTORY);
        List<ItemStack> itemStacks = sockInvLootTable.generateLoot(context);
        return ((ChristmasSockItem) item).fromExisted(stack, itemStacks);
    }

    @Override
    public LootFunctionType getType() {
        return SPMXmas.GENERATE_SOCK_ITEM_FUNC;
    }

    public static Builder<?> builder() {
        return builder(GenerateSockItemFunction::new);
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<GenerateSockItemFunction> {
        @Override
        public GenerateSockItemFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return new GenerateSockItemFunction(conditions);
        }
    }
}
