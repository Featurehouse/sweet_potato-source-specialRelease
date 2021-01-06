package io.github.teddyxlandlee.sweet_potato.items;

import io.github.teddyxlandlee.mcbridge.client.tooltip.data.TooltipData;
import net.minecraft.item.ItemStack;

@Deprecated
public interface Tooltipped {
    TooltipData getTooltipData(ItemStack stack);
}
