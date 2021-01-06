package io.github.teddyxlandlee.sweet_potato.blocks;

import bilibili.ywsuoyi.block.AbstractBlockWithEntity;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrinderBlock extends AbstractBlockWithEntity {
    public static BooleanProperty GRINDING = BooleanProperty.of("grinding");

    public GrinderBlock(AbstractBlock.Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState().with(GRINDING, false));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> checkTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, SPMMain.GRINDER_BLOCK_ENTITY_TYPE, GrinderBlockEntity::tick);
    }


    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GrinderBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        //super.onUse(state, world, pos, player, hand, hitResult);  // super returns PASS.
        if (!world.isClient()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GrinderBlockEntity) {
                player.openHandledScreen((GrinderBlockEntity) blockEntity);
            }

            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity instanceof GrinderBlockEntity ? (GrinderBlockEntity) blockEntity : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GrinderBlockEntity) {
                ((GrinderBlockEntity) blockEntity).setCustomName(itemStack.getName());
            }
        }

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(GRINDING);
    }
}
