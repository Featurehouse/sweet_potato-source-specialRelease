package org.featurehouse.spm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.featurehouse.annotation.Diff16and17;
import org.featurehouse.spm.blocks.CrackedRockBlock;
import org.featurehouse.spm.blocks.DeepDarkFantasyBlock;
import org.featurehouse.spm.blocks.MicrostoneBlock;
import org.featurehouse.spm.blocks.entities.DeepDarkFantasyBlockEntity;
import org.featurehouse.spm.item.HorseBucketItem;
import org.featurehouse.spm.items.MicrohammerItem;
import org.featurehouse.spm.items.SteveSpawnEggItem;
import org.featurehouse.spm.items.interactions.ItemInteractions;
import org.featurehouse.spm.util.properties.objects.BlockSettings;
import org.featurehouse.spm.util.properties.objects.ItemSettings;
import org.featurehouse.spm.util.properties.objects.Materials;

import static org.featurehouse.spm.util.properties.objects.BlockSettings.functionalMinable;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.*;

public class SPMFools implements ModInitializer {
    public static final String FOOLS_ID = "spmfools21";

    public static final Block CURSEFORGE_BLOCK;
    public static final Block CURSEFORGE_ORE;
    public static final CrackedRockBlock CRACKED_ROCK;
    public static final Block MICROSTONE;
    public static final Block DEEP_DARK_FANTASY;

    public static final Item STEVE_SPAWN_EGG;
    public static final Item CURSEFORGE_INGOT;
    public static final Item CURSEFORGE_BLOCK_ITEM;
    public static final Item CURSEFORGE_ORE_ITEM;
    public static final Item HORSE_BUCKET;
    public static final Item MICROHAMMER;
    public static final Item MICROSTONE_ITEM;
    public static final Item DEEP_DARK_FANTASY_ITEM;

    public static final Item FABRIC;

    public static final BlockEntityType<DeepDarkFantasyBlockEntity> DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE;

    public static final SoundEvent SERVER_CRASHING;
    public static final SoundEvent BUCKET_HUNGRY;

    @Diff16and17({"data-pack-tags", "resource-pack-block-states", "resource-pack-textures"})
    public static final Tag<Block> MICROHAMMER_BREAKABLE = blockTag("microhammer_breakable");

    @Diff16and17
    public static final ConfiguredFeature<?, ?> CURSEFORGE_FEATURE;

    @Override
    public void onInitialize() {
        ItemInteractions.init();

        RegistryKey<ConfiguredFeature<?, ?>> curseforgeFeature = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier(FOOLS_ID, "org_curseforge"));
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, curseforgeFeature);
    }

    static {
        CURSEFORGE_BLOCK = defaultBlock("curseforge_block", FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK));
        CURSEFORGE_ORE = defaultBlock("curseforge_ore", FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE));
        CRACKED_ROCK = (CrackedRockBlock) block("cracked_rock", new CrackedRockBlock(FabricBlockSettings.copyOf(Blocks.STONE)));
        MICROSTONE = block("microstone", new MicrostoneBlock(BlockSettings.MICROSTONE));
        DEEP_DARK_FANTASY = block("deep_dark_fantasy", new DeepDarkFantasyBlock(functionalMinable(Materials.MATERIAL_STONE, 3.5F, 6.0F, 0)));

        STEVE_SPAWN_EGG = item("steve_spawn_egg", new SteveSpawnEggItem(ItemSettings.UNC_MISC));
        CURSEFORGE_INGOT = defaultItem("curseforge_ingot", ItemSettings.MISC);
        CURSEFORGE_BLOCK_ITEM = blockItem("curseforge_block", CURSEFORGE_BLOCK, ItemSettings.BUILDING);
        CURSEFORGE_ORE_ITEM = blockItem("curseforge_ore", CURSEFORGE_ORE, ItemSettings.BUILDING);
        HORSE_BUCKET = item("horse_bucket", HorseBucketItem.INSTANCE);
        MICROHAMMER = item("microhammer", new MicrohammerItem(ItemSettings.TOOL_ONE));
        MICROSTONE_ITEM = blockItem("microstone", MICROSTONE, ItemSettings.MISC);
        DEEP_DARK_FANTASY_ITEM = blockItem("deep_dark_fantasy", DEEP_DARK_FANTASY, ItemSettings.DECORATIONS);
        FABRIC = defaultItem("fabric", ItemSettings.EASTER_EGG);

        DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE = blockEntity("deep_dark_fantasy", DeepDarkFantasyBlockEntity::new, DEEP_DARK_FANTASY);

        SERVER_CRASHING = sound("background.tip.server_crashing");
        BUCKET_HUNGRY = sound("item.bucket.hungry");

        CURSEFORGE_FEATURE = configuredFeature("ore_curseforge", Feature.ORE.configure(
                new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, CURSEFORGE_ORE.getDefaultState(), 8))
                .rangeOf(16)).spreadHorizontally().repeat(8);
    }
}
