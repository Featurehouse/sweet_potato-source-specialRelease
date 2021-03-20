package org.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.world.BlockView;
import org.featurehouse.spm.blocks.entities.DeepDarkFantasyBlockEntity;

public class DeepDarkFantasyBlock extends AbstractBlockWithEntity {
    public DeepDarkFantasyBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(GrinderBlock.GRINDING, false));
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof DeepDarkFantasyBlockEntity;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new DeepDarkFantasyBlockEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GrinderBlock.GRINDING);
    }
}
