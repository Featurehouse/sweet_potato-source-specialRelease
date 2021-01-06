package io.github.teddyxlandlee.sweet_potato.util.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.Collections;
import java.util.List;

public final class InventoryNbtHelper {
    public static CompoundTag inv2tag(CompoundTag tag, Inventory inventory, boolean setIfEmpty) {
        ListTag listTag = new ListTag();
        if (inventory == null)
            inventory = new SimpleInventory(0);

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                CompoundTag oneStackTag = new CompoundTag();
                oneStackTag.putByte("Slot", (byte) i);
                itemStack.toTag(oneStackTag);
                listTag.add(oneStackTag);
            }
        }

        if (!listTag.isEmpty() || setIfEmpty)
            tag.put("Items", listTag);

        return tag;
    }

    public static CompoundTag list2tag(CompoundTag tag, List<ItemStack> itemStacks, boolean setIfEmpty) {
        ListTag listTag = new ListTag();
        if (itemStacks == null)
            itemStacks = Collections.emptyList();

        for (int i = 0; i < itemStacks.size(); ++i) {
            ItemStack itemStack = itemStacks.get(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte) i);
                itemStack.toTag(compoundTag);
                listTag.add(compoundTag);
            }
        }

        if (!listTag.isEmpty() || setIfEmpty)
            tag.put("Items", listTag);

        return tag;
    }
}
