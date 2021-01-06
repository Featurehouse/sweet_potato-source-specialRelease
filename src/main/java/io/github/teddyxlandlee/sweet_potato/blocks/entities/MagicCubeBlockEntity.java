package io.github.teddyxlandlee.sweet_potato.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.github.teddyxlandlee.annotation.OperationBeforeDeveloping;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.blocks.MagicCubeBlock;
import io.github.teddyxlandlee.sweet_potato.util.BooleanStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.block.Blocks.SOUL_FIRE;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements SidedInventory {

    protected BooleanStateManager stateManager;

    public MagicCubeBlockEntity(BlockPos pos, BlockState state) {
        super(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, pos, state, 5);
        this.stateManager = new StateManager(MagicCubeBlock.ACTIVATED, pos) {
            boolean shouldChange(boolean newOne) {
                assert MagicCubeBlockEntity.this.world != null;
                return MagicCubeBlockEntity.this.world.getBlockState(this.current).get(property) != newOne;
            }

            @Override
            public void run() {
                assert MagicCubeBlockEntity.this.world != null;
                boolean b;
                if (this.shouldChange(b = this.fireCount() > 0)) {
                    MagicCubeBlockEntity.this.world.setBlockState(
                            this.current,
                            MagicCubeBlockEntity.this.world.getBlockState(this.current)
                                .with(property, b)
                    );
                }
            }

            @FireBelow
            byte fireCount() {
                BlockPos[] blockPosList = calcPos(this.current);

                assert MagicCubeBlockEntity.this.world != null;
                byte b = 0;
                for (BlockPos eachPos: blockPosList) {
                    if (MagicCubeBlockEntity.this.world.getBlockState(eachPos).getBlock() == SOUL_FIRE)
                        ++b;
                }
                return b;
            }
        };
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        assert this.world != null;
        if (world.getTime() % 20L == 5L) {
            stateManager.run();
        }
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.magic_cube");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null; // INDEED TODO
    }


    @OperationBeforeDeveloping
    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @OperationBeforeDeveloping
    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @OperationBeforeDeveloping
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @interface FireBelow {
    }

    static abstract class StateManager extends BooleanStateManager {
        BlockPos current;
        BlockPos[] blockPosList;

        StateManager(Property<Boolean> property, BlockPos pos) {
            super(property);
            this.current = pos;
            BlockPos downPos = pos.down();
            blockPosList = new BlockPos[]{
                    downPos,
                    downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                    downPos.east().north(), downPos.east().south(),
                    downPos.west().north(), downPos.west().south()
            };
        }
    }

    /*
    static class StateHelper {
        private StateHelper() {}

        protected static boolean stateChanged(BlockState oldState, BlockState newState) {
            if (oldState.getBlock() != SPMMain.MAGIC_CUBE || newState.getBlock() != SPMMain.MAGIC_CUBE)
                throw new UnsupportedOperationException("Non-magic cube operates");
            return oldState.get(MagicCubeBlock.ACTIVATED) == newState.get(MagicCubeBlock.ACTIVATED);
        }

        protected static boolean stateChanged(World world, BlockPos pos, BlockState newState) {
            return stateChanged(world.getBlockState(pos), newState);
        }

        protected static void changeIfChange(World world, BlockPos pos) {
            BlockState newState = calcState(world, pos);
            if (stateChanged(world, pos, newState))
                world.setBlockState(pos, newState);
        }

        @NonMinecraftNorFabric
        protected static BlockState calcState(World world, BlockPos pos) {
            if (fireCount(world, pos) > 0) {
                return SPMMain.MAGIC_CUBE.getDefaultState().with(MagicCubeBlock.ACTIVATED, true);
            }
            return SPMMain.MAGIC_CUBE.getDefaultState().with(MagicCubeBlock.ACTIVATED, false);
        }

        @FireBelow
        @NonMinecraftNorFabric
        protected static byte fireCount(World world, BlockPos pos) {
            byte fire = 0;
            BlockPos downPos = pos.down();
            BlockPos[] posArray = new BlockPos[]{
                    downPos,
                    downPos.east(), downPos.south(), downPos.west(), downPos.north(),
                    downPos.east().north(), downPos.east().south(),
                    downPos.west().north(), downPos.west().south()
            };  // down the block 3*3 area
            for (BlockPos eachPos: posArray) {
                if (world.getBlockState(eachPos).getBlock() == Blocks.SOUL_FIRE) {
                    ++fire;
                }
            }
            return fire;
        }
    }*/
}
