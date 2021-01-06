package io.github.teddyxlandlee.sweet_potato.trees;

import io.github.teddyxlandlee.sweet_potato.trees.blockstate.ChristmasTreeLeavesBlockStateProvider;
import io.github.teddyxlandlee.sweet_potato.trees.foliage.ChristmasTreeFoliagePlacer;
import io.github.teddyxlandlee.sweet_potato.util.RegistryUtil;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;
import static io.github.teddyxlandlee.sweet_potato.trees.blockstate.BlockStates.SPRUCE_LOG;

public final class TreeFeatures {

    public static final TreeFeatureConfig CHRISTMAS_TREE = new TreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(SPRUCE_LOG),
            new ChristmasTreeLeavesBlockStateProvider(),
            new ChristmasTreeFoliagePlacer(2, 1, 0, 2, 1, 1),
            new StraightTrunkPlacer(5, 2, 1),
            new TwoLayersFeatureSize(2, 0, 2)
    ).ignoreVines().build();

    public static final class FoliagePlacers {
        private FoliagePlacers() {}

        public static final FoliagePlacerType<?> CHRISTMAS_TREE_LEAVES_FOLIAGE_PLACER;

        static {
            CHRISTMAS_TREE_LEAVES_FOLIAGE_PLACER = RegistryUtil.foliagePlacerType(new Identifier(MODID, "christmas_tree_foliage_placer"), ChristmasTreeFoliagePlacer.CODEC);
        }
    }
}
