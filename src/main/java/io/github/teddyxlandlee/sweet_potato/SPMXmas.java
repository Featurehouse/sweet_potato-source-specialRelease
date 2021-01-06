package io.github.teddyxlandlee.sweet_potato;

import io.github.teddyxlandlee.sweet_potato.advancements.SPMCriteria;
import io.github.teddyxlandlee.sweet_potato.sock.GenerateSockItemFunction;
import io.github.teddyxlandlee.sweet_potato.trees.ChristmasTreeGen;
import io.github.teddyxlandlee.sweet_potato.util.BlockSettings;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.teddyxlandlee.sweet_potato.SPMMain.MODID;
import static io.github.teddyxlandlee.sweet_potato.util.RegistryUtil.lootFunctionType;
import static io.github.teddyxlandlee.sweet_potato.util.RegistryUtil.sound;

public class SPMXmas implements ModInitializer {
    public static final Block CHRISTMAS_TREE_SAPLING;
    public static final Block POTTED_CHRISTMAS_TREE_SAPLING;
    public static final Block CHRISTMAS_TREE_LEAVES_COMMON;
    public static final Block CHRISTMAS_TREE_LEAVES_GIFTED;

    public static final Item TREATING_BOWL;

    public static final Item CHRISTMAS_TREE_LEAVES_ITEM_COMMON;
    public static final Item CHRISTMAS_TREE_LEAVES_ITEM_GIFTED;
    public static final Item CHRISTMAS_TREE_SAPLING_ITEM;

    public static final SoundEvent CHRISTMATATO_YES;

    public static final LootFunctionType GENERATE_SOCK_ITEM_FUNC;

    @Override
    public void onInitialize() {
        System.out.println("Merry Christmas!");

        SPMCriteria.register(SPMCriteria.CHRISTMATATO_SUCCESS);
        SPMCriteria.register(SPMCriteria.UNWRAP_GIFT);
    }

    static {
        CHRISTMAS_TREE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
                MODID, "christmas_tree_sapling"
        ), new SaplingBlock(new ChristmasTreeGen(), BlockSettings.GRASS_LIKE));
        POTTED_CHRISTMAS_TREE_SAPLING = Registry.register(Registry.BLOCK, new Identifier(
                MODID, "potted_christmas_tree_sapling"
        ), new FlowerPotBlock(CHRISTMAS_TREE_SAPLING, AbstractBlock.Settings.of(Material.SUPPORTED)));
        CHRISTMAS_TREE_LEAVES_COMMON = Registry.register(Registry.BLOCK, new Identifier(
                MODID, "common_christmas_tree_leaves"
        ), Util.createGiftedXmasLeavesBlock());
        CHRISTMAS_TREE_LEAVES_GIFTED = Registry.register(Registry.BLOCK, new Identifier(
                MODID, "gifted_christmas_tree_leaves"
        ), Util.createGiftedXmasLeavesBlock());

        TREATING_BOWL = Registry.register(Registry.ITEM, new Identifier(
                MODID, "treating_bowl"
        ), new Item(new Item.Settings()
                .maxCount(64)
                .group(ItemGroup.MISC)
        ));

        CHRISTMAS_TREE_LEAVES_ITEM_COMMON = Registry.register(Registry.ITEM, new Identifier(
                MODID, "common_christmas_tree_leaves"
        ), new BlockItem(CHRISTMAS_TREE_LEAVES_COMMON, new Item.Settings()
                .maxCount(64)
                .group(ItemGroup.DECORATIONS)
        ));
        CHRISTMAS_TREE_LEAVES_ITEM_GIFTED = Registry.register(Registry.ITEM, new Identifier(
                MODID, "gifted_christmas_tree_leaves"
        ), new BlockItem(CHRISTMAS_TREE_LEAVES_GIFTED, new Item.Settings()
                .maxCount(64)
                //.group(ItemGroup.DECORATIONS)
        ));
        CHRISTMAS_TREE_SAPLING_ITEM = Registry.register(Registry.ITEM, new Identifier(
                MODID, "christmas_tree_sapling"
        ), new BlockItem(CHRISTMAS_TREE_SAPLING, new Item.Settings()
                .maxCount(64)
                .group(ItemGroup.DECORATIONS)
        ));

        CHRISTMATATO_YES = sound(new Identifier(MODID, "christmas.christmatato.yes"));

        GENERATE_SOCK_ITEM_FUNC = lootFunctionType(new Identifier(MODID, "generate_sock_item"), new GenerateSockItemFunction.Serializer());
    }
}
