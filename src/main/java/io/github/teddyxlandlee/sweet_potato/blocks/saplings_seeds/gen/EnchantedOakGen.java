package io.github.teddyxlandlee.sweet_potato.blocks.saplings_seeds.gen;

import io.github.teddyxlandlee.annotation.Unused_InsteadOf;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

@Unused_InsteadOf
@Deprecated
public class EnchantedOakGen extends OakSaplingGenerator {
    @Nullable
    @Deprecated
    protected ConfiguredFeature<TreeFeatureConfig, ?> oldCreateTreeFeature(Random random, boolean bl) {
        if (random.nextInt(10) == 0) {
            return bl ? ConfiguredFeatures.FANCY_OAK_BEES_005 : ConfiguredFeatures.FANCY_OAK;
        } else {
            return bl ? ConfiguredFeatures.OAK_BEES_005 : ConfiguredFeatures.OAK;
        }
    }

}
