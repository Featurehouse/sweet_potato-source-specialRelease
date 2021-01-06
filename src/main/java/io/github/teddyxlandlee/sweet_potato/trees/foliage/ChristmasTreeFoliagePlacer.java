package io.github.teddyxlandlee.sweet_potato.trees.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.teddyxlandlee.sweet_potato.trees.TreeFeatures;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class ChristmasTreeFoliagePlacer extends FoliagePlacer {
    public static final Codec<ChristmasTreeFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> method_28846(instance).and(instance.group(Codec.INT.fieldOf("trunk_height").forGetter(placer -> placer.trunkHeight), Codec.INT.fieldOf("trunk_height_random").forGetter(placer -> placer.randomTrunkHeight))).apply(instance, ChristmasTreeFoliagePlacer::new));
    protected final int trunkHeight;
    protected final int randomTrunkHeight;

    public ChristmasTreeFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l);
        this.trunkHeight = m;
        this.randomTrunkHeight = n;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return TreeFeatures.FoliagePlacers.CHRISTMAS_TREE_LEAVES_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int offset, BlockBox box) {
        BlockPos pos = treeNode.getCenter();
        int radius2 = random.nextInt(2);
        int j = 1, k = 0;

        for (int y = offset; y >= -foliageHeight; --y) {
            this.generate(world, random, config, pos, radius2, leaves, y, false, box);
            if (radius2 > j) {
                radius2 = k;
                k = 1;
                j = Math.min(j + 1, radius + treeNode.getFoliageRadius());
            } else ++radius2;
        }
    }

    @Override
    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return Math.max(4, trunkHeight - this.trunkHeight);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int y, int dz, boolean giantTrunk) {
        return baseHeight == dz && y == dz && dz > 0;
    }
}
