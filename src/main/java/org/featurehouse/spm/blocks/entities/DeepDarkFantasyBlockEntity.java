package org.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import org.featurehouse.spm.SPMFools;
import org.jetbrains.annotations.Nullable;

/**
 * Still in dev
 */
public class DeepDarkFantasyBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, SidedInventory {

    public DeepDarkFantasyBlockEntity() {
        super(SPMFools.DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE, 3);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.spmfools21.deep_dark_fantasy");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public void tick() {

    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }
}
