package org.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.DefaultExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.featurehouse.spm.SPMFools;
import org.featurehouse.spm.blocks.GrinderBlock;
import org.featurehouse.spm.entity.damage.GrinderExplodeDamageSource;
import org.featurehouse.spm.screen.DeepDarkFantasyScreenHandler;
import org.featurehouse.spm.util.properties.fantasy.IntDeepDarkFantasyProperties;
import org.featurehouse.spm.util.properties.state.BooleanStateManager;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;
import java.util.function.DoubleSupplier;

/**
 * Still in dev
 */
public class DeepDarkFantasyBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, SidedInventory {
    public final IntDeepDarkFantasyProperties properties;
    private final BooleanStateManager stateManager;

    private int ingredientData;
    private short sublimateTime;
    private static final int[] TOP_SLOTS = new int[]{0},
                               BOTTOM_SLOTS = new int[]{2},
                               SIDE_SLOTS = new int[]{1};
    private static final ItemStack COMPARABLE_OUTPUT = new ItemStack(SPMFools.STONE_NUGGET);
    private static final short MAX_SUBLIMATE_TIME = 200;
    private static final int SUBLIMATE_INGREDIENT_DATA = 64;
    private byte absorbCooldown = 0;

    public DeepDarkFantasyBlockEntity() {
        super(SPMFools.DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE, 3);
        properties = new IntDeepDarkFantasyProperties() {
            @Override
            public int getIngredientData() {
                return ingredientData;
            }

            @Override
            public short getSublimateTime() {
                return sublimateTime;
            }

            @Override
            public void setIngredientData(int value) {
                ingredientData = value;
            }

            @Override
            public void setSublimateTime(short value) {
                sublimateTime = value;
            }
        };
        ingredientData = 0;
        sublimateTime = -1;

        this.stateManager = new BooleanStateManager(GrinderBlock.GRINDING) {
            public boolean shouldChange(boolean newOne) {
                assert DeepDarkFantasyBlockEntity.this.world != null;
                return DeepDarkFantasyBlockEntity.this.world.getBlockState(pos).get(property) != newOne;
            }

            @Override
            public void run() {
                assert DeepDarkFantasyBlockEntity.this.world != null;
                boolean b;
                if (this.shouldChange(b = DeepDarkFantasyBlockEntity.this.isSublimating()))
                    DeepDarkFantasyBlockEntity.this.world.setBlockState(
                            DeepDarkFantasyBlockEntity.this.pos,
                            DeepDarkFantasyBlockEntity.this.world.getBlockState(
                                    DeepDarkFantasyBlockEntity.this.pos
                            ).with(property, b)
                    );
            }
        };
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.spmfools21.deep_dark_fantasy");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new DeepDarkFantasyScreenHandler(syncId, playerInventory, this, this.properties);
    }

    @Override
    public void tick() {
        assert world != null;
        boolean shallMarkDirty = false;

        if (!world.isClient) {
            if (this.sublimateTime >= MAX_SUBLIMATE_TIME && this.canOutput()) {
                sublimateTime = -1;
                craft();
                shallMarkDirty = true;
            } else if (sublimateTime >= 0 && sublimateTime < MAX_SUBLIMATE_TIME && this.canOutput()) {
                ++this.sublimateTime;
                shallMarkDirty = true;
            } else if (!this.canOutput() && this.isSublimating()) {
                sublimateTime = (short) MathHelper.clamp(sublimateTime - 2, 0, MAX_SUBLIMATE_TIME);
                shallMarkDirty = true;
            }

            if (!shallCooldown()) {
                if (isValidIngredient(this.inventory.get(0))) {
                    this.ingredientData++;
                    this.inventory.get(0).decrement(1);
                    //this.inventory.get(2).decrement(1);
                    this.absorbCooldown = 5;
                    shallMarkDirty = true;
                }
            } else --this.absorbCooldown;

            ItemStack msSlot = this.inventory.get(2);
            if (this.ingredientData >= SUBLIMATE_INGREDIENT_DATA && !isSublimating() && msSlot.getItem() == SPMFools.MICROSTONE_ITEM) {
                this.ingredientData -= SUBLIMATE_INGREDIENT_DATA;
                this.sublimateTime = 0;
                msSlot.decrement(1);
                shallMarkDirty = true;
            }

            if (world.getTime() % 50L == 1L)
                stateManager.run();
        }

        if (shallMarkDirty) markDirty();

        if (this.ingredientData >= 128) {
            if (!world.isClient) {
                Vec3d vec3d = Vec3d.ofCenter(pos);
                world.createExplosion(null, GrinderExplodeDamageSource.of(), new ExplosionBehavior() {
                            @Override
                            public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
                                return DefaultExplosionBehavior.INSTANCE.getBlastResistance(explosion, world, pos, blockState, fluidState);
                            }

                            @Override
                            public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
                                return false;
                            }
                        },
                        vec3d.x, vec3d.y, vec3d.z,
                        2.0F, false, Explosion.DestructionType.NONE
                );
                ((ServerWorld) world).getServer().getPlayerManager().sendToAround(
                        null, vec3d.x, vec3d.y, vec3d.z, 256.0F, world.getRegistryKey(),
                        new GameMessageS2CPacket(new LiteralText("Exception in thread \"Server Thread\" java.lang.StackOverflowError: ")
                                .append(new TranslatableText(
                                        "message.spmfools21.java.lang.StackOverflowError", pos))
                                , MessageType.SYSTEM, net.minecraft.util.Util.NIL_UUID)
                );
                this.ingredientData = 0;
            } else {
                Random random = world.getRandom();
                DoubleSupplier sup = () -> random.nextDouble() * (random.nextBoolean() ? 1.5 : -1.5) + 0.5D;
                world.addParticle(ParticleTypes.SMOKE,
                        sup.getAsDouble() + pos.getX(),
                        sup.getAsDouble() + pos.getY(),
                        sup.getAsDouble() + pos.getZ(),
                        0.0D, 0.0D, 0.0D
                );
            }
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        switch (side) {
            case UP: return TOP_SLOTS;
            case DOWN: return BOTTOM_SLOTS;
            default: return SIDE_SLOTS;
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.sublimateTime = tag.getShort("SublimateTime");
        this.ingredientData = tag.getInt("IngredientData");
        this.absorbCooldown = tag.getByte("absorbCooldown");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putShort("SublimateTime", sublimateTime);
        tag.putInt("IngredientData", ingredientData);
        tag.putByte("absorbCooldown", absorbCooldown);

        return tag;
    }

    protected void craft() {
        if (!canOutput()) return;
        ItemStack stack = this.inventory.get(1);
        if (stack.isEmpty()) {
            this.inventory.set(1, COMPARABLE_OUTPUT.copy());
        } else stack.increment(1);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) return stack.getItem() == SPMFools.MICROSTONE_ITEM;
        else if (slot == 0) return isValidIngredient(stack);
        return false;   // slot == 1 || slot not in range
    }

    public static boolean isValidIngredient(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof BlockItem)) return false;
        return ((BlockItem) item).getBlock().getDefaultState()
                .getMaterial() == Material.STONE;
    }

    private boolean canOutput() {
        ItemStack stack = this.inventory.get(1);
        if (stack.isEmpty()) return true;
        if (!stack.isItemEqualIgnoreDamage(COMPARABLE_OUTPUT)) return false;
        if (stack.getCount() < Math.min(this.getMaxCountPerStack(), stack.getMaxCount())) return true;
        return stack.getCount() < SPMFools.STONE_NUGGET.getMaxCount();
    }

    private boolean shallCooldown() {
        return this.absorbCooldown > 0;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1 && dir == Direction.DOWN;
    }

    /**
     * 0..199 -> range
     * -1: not sublimating
     */
    public boolean isSublimating() {
        return sublimateTime >= 0;
    }
}
