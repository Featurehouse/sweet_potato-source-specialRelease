package io.github.teddyxlandlee.sweet_potato.util;

import com.mojang.serialization.Codec;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public final class RegistryUtil {
    private RegistryUtil() {}

    public static SoundEvent sound(Identifier id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static LootFunctionType lootFunctionType(Identifier id, JsonSerializer<? extends LootFunction> jsonSerializer) {
        return Registry.register(Registry.LOOT_FUNCTION_TYPE, id, new LootFunctionType(jsonSerializer));
    }

    public static <P extends FoliagePlacer> FoliagePlacerType<?> foliagePlacerType(Identifier id, Codec<P> codec) {
        return Registry.register(Registry.FOLIAGE_PLACER_TYPE, id, new FoliagePlacerType<>(codec));
    }
}
