package io.github.teddyxlandlee.sweet_potato.trees.blockstate;

import com.mojang.serialization.Codec;
import io.github.teddyxlandlee.annotation.CopiedFrom;
import io.github.teddyxlandlee.annotation.Unused_InsteadOf;
import io.github.teddyxlandlee.sweet_potato.SPMXmas;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.BlockStateProviderType;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;

@CopiedFrom(ConfiguredFeatures.States.class)
public final class BlockStates {
    public static final BlockState SPRUCE_LOG;
    @Unused_InsteadOf
    public static final BlockState SPRUCE_LEAVES;

    public static final BlockState COMMON_CHRISTMAS_TREE_LEAVES;
    public static final BlockState GIFTED_CHRISTMAS_TREE_LEAVES;

    static {
        SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
        SPRUCE_LEAVES = Blocks.SPRUCE_LEAVES.getDefaultState();
        COMMON_CHRISTMAS_TREE_LEAVES = SPMXmas.CHRISTMAS_TREE_LEAVES_COMMON.getDefaultState();
        GIFTED_CHRISTMAS_TREE_LEAVES = SPMXmas.CHRISTMAS_TREE_LEAVES_GIFTED.getDefaultState();
    }

    public static class Providers {
        protected static <P extends BlockStateProvider> BlockStateProviderType<P> register(Identifier id, Codec<P> codec) {
            return Registry.register(Registry.BLOCK_STATE_PROVIDER_TYPE, id, new BlockStateProviderType<>(codec));
        }

        public static final BlockStateProviderType<ChristmasTreeLeavesBlockStateProvider> CHRISTMAS_TREE_LEAVES_PROVIDER;

        static {
            CHRISTMAS_TREE_LEAVES_PROVIDER = register(new Identifier(MODID, "christmas_tree_leaves_provider"), ChristmasTreeLeavesBlockStateProvider.CODEC);
        }
    }
}
