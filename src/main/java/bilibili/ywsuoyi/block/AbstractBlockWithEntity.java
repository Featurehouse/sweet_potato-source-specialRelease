package bilibili.ywsuoyi.block;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class AbstractBlockWithEntity extends BlockWithEntity {
    public AbstractBlockWithEntity(Settings settings) {
        super(settings);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public abstract <T extends BlockEntity> BlockEntityTicker<T> checkTicker(World world, BlockState state, BlockEntityType<T> type);

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkTicker(world, state, type);
    }
}
