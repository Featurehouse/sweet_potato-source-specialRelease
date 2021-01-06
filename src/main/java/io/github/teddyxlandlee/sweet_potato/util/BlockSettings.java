package io.github.teddyxlandlee.sweet_potato.util;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public final class BlockSettings {
    public static final FabricBlockSettings GRASS_LIKE;
    public static final FabricBlockSettings GRASS;

    public static final Material GIFTED_XMAS_LEAVES;

    private BlockSettings() {}

    @Deprecated
    public static FabricBlockSettings create(AbstractBlock.Settings settings) {
        return (FabricBlockSettings)settings;
    }

    static {
        GRASS_LIKE = FabricBlockSettings.of(SPMMain.MATERIAL_PLANT) // Wanted: move MATERIAL_PLANT to Util
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.CROP);
        GRASS = FabricBlockSettings.of(SPMMain.MATERIAL_PLANT)
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS);
        GIFTED_XMAS_LEAVES = new FabricMaterialBuilder(MapColor.FOLIAGE)
                .burnable()
                .lightPassesThrough()
                //.pistonBehavior(PistonBehavior.IGNORE)
                .build();
    }
}