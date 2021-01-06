package io.github.teddyxlandlee.sweet_potato.items;

import io.github.teddyxlandlee.sweet_potato.advancements.SPMCriteria;
import io.github.teddyxlandlee.sweet_potato.util.inventory.InventoryNbtHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Stream;

public class ChristmasSockItem extends Item {
    public ChristmasSockItem(Settings settings) {
        super(settings);
    }

    public ItemStack fromExisted(ItemStack existed, List<ItemStack> itemStacks) {
        CompoundTag tag = existed.getOrCreateTag();
        InventoryNbtHelper.list2tag(tag, itemStacks, false);
        return existed;
    }

    public ItemStack create(Inventory inventory) {
        return create(inventory, 1);
    }

    public ItemStack create(DefaultedList<ItemStack> itemStacks, int count) {
        ItemStack socks = new ItemStack(this, count);
        CompoundTag tag = new CompoundTag();
        Inventories.toTag(tag, itemStacks, false);
        socks.setTag(tag);
        return socks;
    }

    public ItemStack create(Inventory inventory, int count) {
        ItemStack socks = new ItemStack(this, count);
        CompoundTag tag = new CompoundTag();
        InventoryNbtHelper.inv2tag(tag, inventory, false);
        socks.setTag(tag);
        return socks;
    }

    protected boolean notEmpty(ItemStack itemStack) {
        return itemStack.getOrCreateTag().contains("Items", 9 /*ListTag*/);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        CompoundTag base = stack.getOrCreateTag();
        if (!base.contains("Items", 9))
            return TypedActionResult.fail(stack);
        if (!world.isClient) {
            DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
            Inventories.fromTag(base, inventory);

            for (ItemStack stack1 : inventory) {
                user.dropStack(stack1);
            }
            stack.removeSubTag("Items");

            SPMCriteria.UNWRAP_GIFT.trigger((ServerPlayerEntity) user);
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return notEmpty(stack) ? "item.sweet_potato.bundled_sock" : super.getTranslationKey(stack);
    }

    @Deprecated
    private static Stream<ItemStack> asStream(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag == null) {
            return Stream.empty();
        } else {
            ListTag listTag = compoundTag.getList("Items", 10);
            Stream<?> stream = listTag.stream();
            return stream.map(CompoundTag.class::cast).map(ItemStack::fromTag);
        }
    }

    public static boolean bundled(ItemStack sock) {
        Item item = sock.getItem();
        if (item instanceof ChristmasSockItem) {
            return ((ChristmasSockItem) item).notEmpty(sock);
        } return false;
    }
}
