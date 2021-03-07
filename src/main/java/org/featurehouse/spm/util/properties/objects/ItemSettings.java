package org.featurehouse.spm.util.properties.objects;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

public final class ItemSettings {
    public static final Settings GROUP_FOOD;
    public static final Settings ONE_FOOD; // unstackable food item
    public static final Settings DECORATIONS;
    public static final Settings UNCDEC;   // Uncommon Decorations
    public static final Settings MISC;
    public static final Settings UNC_MISC;
    public static final Settings BUILDING;
    public static final Settings MISC_ONE;
    public static final Settings TOOL_ONE;
    /** Does not appear in creative mode inventory, but still exists. */
    public static final Settings EASTER_EGG;

    private ItemSettings() {}

    static {
        GROUP_FOOD = new FabricItemSettings().group(ItemGroup.FOOD);
        ONE_FOOD = new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1);
        DECORATIONS = new FabricItemSettings().group(ItemGroup.DECORATIONS);
        UNCDEC = new FabricItemSettings().group(ItemGroup.DECORATIONS).rarity(Rarity.UNCOMMON);
        MISC = new FabricItemSettings().group(ItemGroup.MISC);
        UNC_MISC = new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.UNCOMMON);
        BUILDING = new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS);
        MISC_ONE = new FabricItemSettings().group(ItemGroup.MISC).maxCount(1);
        TOOL_ONE = new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1);
        EASTER_EGG = new FabricItemSettings();
    }
}