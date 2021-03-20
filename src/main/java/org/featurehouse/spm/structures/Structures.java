package org.featurehouse.spm.structures;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.id;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.structurePiece;

public class Structures {
    protected static final StructurePieceType ELEVATOR_ALTAR_PIECE = structurePiece("elevator_altar", ElevatorAltarPiece::new);
    private static final StructureFeature<DefaultFeatureConfig> ELEVATOR_ALTAR_STRUCTURE = new ElevatorAltarFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> ELEVATOR_ALTAR_CONFIGURED = ELEVATOR_ALTAR_STRUCTURE.configure(DefaultFeatureConfig.DEFAULT);

    public static void init() {
        FabricStructureBuilder.create(id("elevator_altar"), ELEVATOR_ALTAR_STRUCTURE)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(32, 8, ELEVATOR_ALTAR_STRUCTURE.hashCode())
                .adjustsSurface()
                .register();
        RegistryKey<ConfiguredStructureFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_STRUCTURE_FEATURE_WORLDGEN, id("elevator_altar"));
        BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, key.getValue(), ELEVATOR_ALTAR_CONFIGURED);

        BiomeModifications.addStructure(BiomeSelectors.foundInOverworld().or(ctx -> ctx.getBiomeKey().getValue().getNamespace().equals("shurlin")), key);
    }
}
