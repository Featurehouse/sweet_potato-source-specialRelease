package org.featurehouse.spm.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class MicrostoneBlock extends Block implements Waterloggable {
    public static final IntProperty COUNT = IntProperty.of("count", 1, 4);
    public static final BooleanProperty WATERLOGGED= Properties.WATERLOGGED;
    public static final VoxelShape[] SHAPES;

    public MicrostoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(COUNT, 1).with(WATERLOGGED, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this))
            return blockState.with(COUNT, Math.min(4, blockState.get(COUNT) + 1));
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean waterLogged = fluidState.getFluid() == Fluids.WATER;
        return getDefaultState().with(WATERLOGGED, waterLogged);
    }

    public static boolean isDry(BlockState state) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos pos1 = pos.down(); BlockState floor = world.getBlockState(pos1);
        return !floor.getCollisionShape(world, pos).getFace(Direction.UP).isEmpty() || floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (!state.canPlaceAt(world, pos))
            return Blocks.AIR.getDefaultState();
        if (state.get(WATERLOGGED))
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().getItem() == this.asItem() && state.get(COUNT) < 4 || super.canReplace(state, context);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(COUNT) - 1];
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COUNT, WATERLOGGED);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    static {
        SHAPES = new VoxelShape[]{
                Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D),
                Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D)
        };
    }
}
