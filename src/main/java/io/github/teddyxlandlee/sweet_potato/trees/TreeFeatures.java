package io.github.teddyxlandlee.sweet_potato.trees;

import io.github.teddyxlandlee.annotation.CopiedFrom;
import io.github.teddyxlandlee.sweet_potato.trees.blockstate.BlockStates;
import io.github.teddyxlandlee.sweet_potato.trees.blockstate.ChristmasTreeLeavesBlockStateProvider;
import io.github.teddyxlandlee.sweet_potato.trees.foliage.ChristmasTreeFoliagePlacer;
import io.github.teddyxlandlee.sweet_potato.util.RegistryUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.UniformIntDistribution;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

public final class TreeFeatures {
    public static final ConfiguredFeature<TreeFeatureConfig, ?> CHRISTMAS_TREE = register(new Identifier(MODID, "christmas_tree"), Feature.TREE.configure(
        new TreeFeatureConfig.Builder(
                new SimpleBlockStateProvider(BlockStates.SPRUCE_LOG),
                new ChristmasTreeLeavesBlockStateProvider(),
                new ChristmasTreeFoliagePlacer(
                        UniformIntDistribution.of(2, 1),
                        UniformIntDistribution.of(0, 2),
                        UniformIntDistribution.of(1, 1)
                ),
                new StraightTrunkPlacer(5, 2, 1),
                new TwoLayersFeatureSize(2, 0, 2)
        ).ignoreVines().build()
    ));

    @CopiedFrom(ConfiguredFeatures.class)
    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(Identifier id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    public static final class FoliagePlacers {
        private FoliagePlacers() {}

        public static final FoliagePlacerType<?> CHRISTMAS_TREE_LEAVES_FOLIAGE_PLACER;

        static {
            CHRISTMAS_TREE_LEAVES_FOLIAGE_PLACER = RegistryUtil.foliagePlacerType(new Identifier(MODID, "christmas_tree_foliage_placer"), ChristmasTreeFoliagePlacer.CODEC);
        }
    }
}
