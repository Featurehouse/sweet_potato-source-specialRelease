package org.featurehouse.spm.world.gen.ores;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.featurehouse.annotation.Diff16and17;

import static org.featurehouse.spm.SPMFools.CURSEFORGE_ORE;
import static org.featurehouse.spm.SPMFools.DEEPSLATE_CURSEFORGE_ORE;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.configuredFeature;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.id;

public class OreFeatures {
    private static final BlockState CURSEFORGE = CURSEFORGE_ORE.getDefaultState();
    private static final BlockState DEEPSLATE_CURSEFORGE = DEEPSLATE_CURSEFORGE_ORE.getDefaultState();
    /**
     * @see ConfiguredFeatures#ORE_REDSTONE
     * @see ConfiguredFeatures#ORE_REDSTONE_LOWER
     * @see org.featurehouse.spm.SPMFools#CURSEFORGE_ORE
     * @see ConfiguredFeatures#REDSTONE_CONFIG
     * @see ConfiguredFeatures#REDSTONE_ORE_TARGETS
     */
    @Diff16and17("code:deepslate")
    protected static ConfiguredFeature<?, ?> ORE_CURSEFORGE;
    protected static ConfiguredFeature<?, ?> ORE_CURSEFORGE_LOW;

    private static ConfiguredFeature<?, ?> register(String id, ConfiguredFeature<?, ?> feature) {
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                id(id));
        ConfiguredFeature<?, ?> ret = configuredFeature(id, feature);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, key);
        return ret;
    }

    private static final OreFeatureConfig CFG = new OreFeatureConfig(
            ImmutableList.of(
                    OreFeatureConfig.create(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, CURSEFORGE),
                    OreFeatureConfig.create(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES,DEEPSLATE_CURSEFORGE)
            ), 8
    );

    /** Force loading this java class */
    public static void load() {}

    static {
        //ORE_REDSTONE_LOWER = register("ore_redstone_lower", (ConfiguredFeature)((ConfiguredFeature)((ConfiguredFeature)
        // Feature.ORE.configure(REDSTONE_CONFIG).averageDepth(YOffset.getBottom(), 32)).spreadHorizontally()).repeat(8));
        ORE_CURSEFORGE = register("ore_curseforge", Feature.ORE.configure(CFG).rangeOf(YOffset.getBottom(), YOffset.fixed(15)).spreadHorizontally().repeat(4));
        ORE_CURSEFORGE_LOW = register("ore_curseforge_lower", Feature.ORE.configure(CFG).averageDepth(YOffset.getBottom(), 32)).spreadHorizontally().repeat(8);
    }
}
