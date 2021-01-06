package io.github.teddyxlandlee.sweet_potato.tooltips.data;

import io.github.teddyxlandlee.mcbridge.client.tooltip.data.TooltipData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

@Environment(EnvType.CLIENT)
@Deprecated
public class BundledSockTooltipData implements TooltipData {
    private final DefaultedList<ItemStack> inventory;

    public BundledSockTooltipData(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }
}
