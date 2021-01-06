package io.github.teddyxlandlee.sweet_potato.trees.blockstate;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

import java.util.Random;

public class ChristmasTreeLeavesBlockStateProvider extends BlockStateProvider {
    protected static final ChristmasTreeLeavesBlockStateProvider INSTANCE = new ChristmasTreeLeavesBlockStateProvider();
    protected static final Codec<ChristmasTreeLeavesBlockStateProvider> CODEC = Codec.unit(INSTANCE);

    @Override
    protected BlockStateProviderType<?> getType() {
        return BlockStates.Providers.CHRISTMAS_TREE_LEAVES_PROVIDER;
    }

    @Override
    public BlockState getBlockState(Random random, BlockPos pos) {
        return random.nextBoolean() ? BlockStates.COMMON_CHRISTMAS_TREE_LEAVES : BlockStates.GIFTED_CHRISTMAS_TREE_LEAVES;
    }
}
