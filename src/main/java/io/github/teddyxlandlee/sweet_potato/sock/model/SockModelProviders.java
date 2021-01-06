package io.github.teddyxlandlee.sweet_potato.sock.model;

import com.google.common.collect.Maps;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.items.ChristmasSockItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

@Environment(EnvType.CLIENT)
public final class SockModelProviders {
    private SockModelProviders() {}

    public static void register(Item item, Identifier id, ModelPredicateProvider provider) {
        ModelPredicateProviderRegistry.ITEM_SPECIFIC.computeIfAbsent(item, item1 -> Maps.newHashMap()).put(id, provider);
    }
}
