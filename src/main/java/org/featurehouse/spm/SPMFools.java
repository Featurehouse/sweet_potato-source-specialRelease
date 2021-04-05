package org.featurehouse.spm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.featurehouse.annotation.Diff16and17;
import org.featurehouse.spm.advancement.BugjumpCriterion;
import org.featurehouse.spm.blocks.ElevatorBlock;
import org.featurehouse.spm.blocks.CrackedRockBlock;
import org.featurehouse.spm.blocks.DeepDarkFantasyBlock;
import org.featurehouse.spm.blocks.MicrostoneBlock;
import org.featurehouse.spm.blocks.entities.DeepDarkFantasyBlockEntity;
import org.featurehouse.spm.entity.passive.GlintPigEntity;
import org.featurehouse.spm.item.HorseBucketItem;
import org.featurehouse.spm.item.NullPointerExceptionItem;
import org.featurehouse.spm.items.MicrohammerItem;
import org.featurehouse.spm.items.SteveSpawnEggItem;
import org.featurehouse.spm.items.StoneNuggetItem;
import org.featurehouse.spm.items.interactions.ItemInteractions;
import org.featurehouse.spm.screen.DeepDarkFantasyScreenHandler;
import org.featurehouse.spm.structures.Structures;
import org.featurehouse.spm.util.properties.objects.BlockSettings;
import org.featurehouse.spm.util.properties.objects.ItemSettings;
import org.featurehouse.spm.util.properties.objects.Materials;
import org.featurehouse.spm.world.event.WorldEvents;
import org.featurehouse.spm.world.gen.ores.OreFeatures;

import static org.featurehouse.spm.util.properties.objects.BlockSettings.functionalMinable;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.*;

public class SPMFools implements ModInitializer {
    public static final String FOOLS_ID = "spmfools21";

    public static final Block CURSEFORGE_BLOCK;
    @Diff16and17("code:worldgen")
    public static final Block CURSEFORGE_ORE;
    @Diff16and17({"code:block-settings", "code:worldgen"})
    public static final Block DEEPSLATE_CURSEFORGE_ORE;
    public static final CrackedRockBlock CRACKED_ROCK;
    public static final Block MICROSTONE;
    public static final Block DEEP_DARK_FANTASY;
    public static final Block ELEVATOR_BLOCK;

    public static final Item STEVE_SPAWN_EGG;
    public static final Item CURSEFORGE_INGOT;
    public static final Item CURSEFORGE_BLOCK_ITEM;
    public static final Item CURSEFORGE_ORE_ITEM;
    public static final Item DEEPSLATE_CURSEFORGE_ORE_ITEM;
    public static final Item HORSE_BUCKET;
    public static final Item MICROHAMMER;
    public static final Item MICROSTONE_ITEM;
    public static final Item DEEP_DARK_FANTASY_ITEM;
    public static final Item STONE_NUGGET;
    public static final Item NULLPOINTEREXCEPTION;
    public static final Item KOTLIN;
    public static final Item ELEVATOR_BLOCK_ITEM;

    public static final Item FABRIC, DEEAM_ITEM, BUGJUMP_ITEM;

    public static final BlockEntityType<DeepDarkFantasyBlockEntity> DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE;

    /**
     * @see net.minecraft.entity.passive.PigEntity
     * @see EntityType#PIG
     */
    public static final EntityType<GlintPigEntity> GLINT_PIG;

    public static final ScreenHandlerType<DeepDarkFantasyScreenHandler> DEEP_DARK_FANTASY_SCREEN_HANDLER_TYPE;

    public static final SoundEvent SERVER_CRASHING;
    public static final SoundEvent BUCKET_HUNGRY;

    public static final StatusEffect DEEAM;
    public static final StatusEffect BUGJUMP;

    @Diff16and17({"data-pack-tags"})
    public static final Tag<Block> MICROHAMMER_BREAKABLE = blockTag("microhammer_breakable");

    public static final BlockPos GLINT_PIG_POS_CENTER = new BlockPos(0, 13, 1);

    public static final BugjumpCriterion BUGJUMP_CRITERION = CriterionRegistry.register(new BugjumpCriterion(id("bugjump")));

    @Override
    public void onInitialize() {
        ItemInteractions.init();
        OreFeatures.load();
        WorldEvents.init();
        Structures.init();

        CriterionRegistry.register(NullPointerExceptionItem.Companion.getCriterion());
    }

    static {
        CURSEFORGE_BLOCK = defaultBlock("curseforge_block", FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).breakByTool(FabricToolTags.PICKAXES, 2));
        CURSEFORGE_ORE = defaultBlock("curseforge_ore", FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).breakByTool(FabricToolTags.PICKAXES, 2));
        // $DEEPSLATE_CURSEFORGE_ORE: deepslate settings copyOf in 1.17
        DEEPSLATE_CURSEFORGE_ORE = defaultBlock("deepslate_curseforge_ore", FabricBlockSettings.copyOf(Blocks.DEEPSLATE_DIAMOND_ORE).breakByTool(FabricToolTags.PICKAXES, 2));
        CRACKED_ROCK = (CrackedRockBlock) block("cracked_rock", new CrackedRockBlock(FabricBlockSettings.copyOf(Blocks.STONE)));
        MICROSTONE = block("microstone", new MicrostoneBlock(BlockSettings.MICROSTONE));
        DEEP_DARK_FANTASY = block("deep_dark_fantasy", new DeepDarkFantasyBlock(functionalMinable(Materials.MATERIAL_STONE, 3.5F, 6.0F, 0)));
        ELEVATOR_BLOCK = block("elevator_block", new ElevatorBlock(BlockSettings.functionalMinable(Materials.MATERIAL_STONE, 5.0F, 8.0F, 2)));

        STEVE_SPAWN_EGG = item("steve_spawn_egg", new SteveSpawnEggItem(ItemSettings.UNC_MISC));
        CURSEFORGE_INGOT = defaultItem("curseforge_ingot", ItemSettings.MISC);
        CURSEFORGE_BLOCK_ITEM = blockItem("curseforge_block", CURSEFORGE_BLOCK, ItemSettings.BUILDING);
        CURSEFORGE_ORE_ITEM = blockItem("curseforge_ore", CURSEFORGE_ORE, ItemSettings.BUILDING);
        DEEPSLATE_CURSEFORGE_ORE_ITEM = blockItem("deepslate_curseforge_ore", DEEPSLATE_CURSEFORGE_ORE, ItemSettings.BUILDING);
        HORSE_BUCKET = item("horse_bucket", HorseBucketItem.INSTANCE);
        MICROHAMMER = item("microhammer", new MicrohammerItem(ItemSettings.TOOL_ONE));
        MICROSTONE_ITEM = blockItem("microstone", MICROSTONE, ItemSettings.MISC);
        DEEP_DARK_FANTASY_ITEM = blockItem("deep_dark_fantasy", DEEP_DARK_FANTASY, ItemSettings.DECORATIONS);
        FABRIC = defaultItem("fabric", ItemSettings.EASTER_EGG);
        DEEAM_ITEM = defaultItem("deeam", ItemSettings.EASTER_EGG);
        BUGJUMP_ITEM = defaultItem("bugjump", ItemSettings.EASTER_EGG);
        STONE_NUGGET = item("stone_nugget", new StoneNuggetItem(ItemSettings.UNC_MISC));
        NULLPOINTEREXCEPTION = item("nullpointerexception", new NullPointerExceptionItem(ItemSettings.TOOL_ONE_UNC));
        KOTLIN = defaultItem("kotlin", ItemSettings.MISC);
        ELEVATOR_BLOCK_ITEM = blockItem("elevator_block", ELEVATOR_BLOCK, ItemSettings.DECORATIONS);

        DEEP_DARK_FANTASY_BLOCK_ENTITY_TYPE = blockEntity("deep_dark_fantasy", DeepDarkFantasyBlockEntity::new, DEEP_DARK_FANTASY);

        GLINT_PIG = entityType("glint_pig", FabricEntityTypeBuilder.<GlintPigEntity>createMob().entityFactory(GlintPigEntity::new).dimensions(EntityDimensions.changing(.9F, .9F)).trackRangeBlocks(10).spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                (type, serverWorldAccess, spawnReason, pos, random) -> SpawnReason.STRUCTURE == spawnReason).defaultAttributes(PigEntity::createPigAttributes).build());
        //GLINT_PIG = entityType("glint_pig", FabricEntityTypeBuilder.<GlintPigEntity>createLiving().entityFactory(GlintPigEntity::new).dimensions(EntityDimensions.changing(.9F, .9F)).trackRangeBlocks(10).build());

        DEEP_DARK_FANTASY_SCREEN_HANDLER_TYPE = simpleScreenHandler("deep_dark_fantasy", DeepDarkFantasyScreenHandler::new);

        SERVER_CRASHING = sound("background.tip.server_crashing");
        BUCKET_HUNGRY = sound("item.bucket.hungry");

        DEEAM = statusEffect("deeam", StatusEffectType.BENEFICIAL, 0xFF8800);
        BUGJUMP = statusEffect("bugjump", StatusEffectType.NEUTRAL, 0xC9193F);
    }
}
