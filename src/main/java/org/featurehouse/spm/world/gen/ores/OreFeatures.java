package org.featurehouse.spm.world.gen.ores;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.featurehouse.annotation.Diff16and17;

import static org.featurehouse.spm.SPMFools.CURSEFORGE_ORE;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.*;

public class OreFeatures {
    /**
     * @see net.minecraft.world.gen.feature.ConfiguredFeatures#ORE_REDSTONE
     * @see org.featurehouse.spm.SPMFools#CURSEFORGE_ORE
     */
    @Diff16and17("code:deepslate")
    protected static ConfiguredFeature<?, ?> ORE_CURSEFORGE;

    private static ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<?, ?> feature) {
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN,
                id(id));
        ConfiguredFeature<?, ?> ret = configuredFeature(id, feature);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, key);
        return ret;
    }

    /** Force loading this java class */
    public static void load() {}

    static {
        ORE_CURSEFORGE = register("ore_curseforge", Feature.ORE
                .configure(new OreFeatureConfig(
                        OreFeatureConfig.Rules.BASE_STONE_OVERWORLD,
                        CURSEFORGE_ORE.getDefaultState(),
                        8
                )).rangeOf(16).spreadHorizontally().repeat(8));
    }
}
