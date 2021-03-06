package org.featurehouse.spm.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.featurehouse.spm.blocks.entities.DeepDarkFantasyBlockEntity;

public class DeepDarkFantasyBlock extends AbstractBlockWithEntity {
    public DeepDarkFantasyBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean blockEntityPredicate(BlockEntity blockEntity) {
        return blockEntity instanceof DeepDarkFantasyBlockEntity;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new DeepDarkFantasyBlockEntity();
    }
}
