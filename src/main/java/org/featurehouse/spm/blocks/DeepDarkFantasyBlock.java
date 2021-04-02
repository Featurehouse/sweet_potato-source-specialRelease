package org.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import org.featurehouse.spm.SPMFools;
import org.featurehouse.spm.blocks.entities.DeepDarkFantasyBlockEntity;

public class DeepDarkFantasyBlock extends AbstractBlockWithEntity<DeepDarkFantasyBlockEntity> {
    public DeepDarkFantasyBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(GrinderBlock.GRINDING, false));
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof DeepDarkFantasyBlockEntity;
    }

    @Override
    public DeepDarkFantasyBlockEntity createBlockEntity(BlockPos a, BlockState b) {
        return new DeepDarkFantasyBlockEntity(a, b);
    }

    @Override
    protected BlockEntityType<DeepDarkFantasyBlockEntity> getBlockEntityType() {
        return SPMFools.DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GrinderBlock.GRINDING);
    }
}
