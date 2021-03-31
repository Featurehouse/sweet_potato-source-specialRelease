package org.featurehouse.spm.structures;

import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.featurehouse.spm.mixin.global.VanillaLayeredBiomeSourceAccessor;

import java.util.List;

import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.id;
import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.structurePiece;

public class Structures {
    protected static final StructurePieceType ELEVATOR_ALTAR_PIECE = structurePiece("elevator_altar", ElevatorAltarPiece::new);
    private static final StructureFeature<DefaultFeatureConfig> ELEVATOR_ALTAR_STRUCTURE = new ElevatorAltarFeature(DefaultFeatureConfig.CODEC);
    private static final ConfiguredStructureFeature<?, ?> ELEVATOR_ALTAR_CONFIGURED = ELEVATOR_ALTAR_STRUCTURE.configure(DefaultFeatureConfig.DEFAULT);

    public static void init() {
        List<Biome> biomeList = VanillaLayeredBiomeSourceAccessor.getBiomes();

        Registry.register(Registry.STRUCTURE_FEATURE, id("elevator_altar"), ELEVATOR_ALTAR_STRUCTURE);
        StructureFeature.STRUCTURES.put("ELEVATOR ALTAR", ELEVATOR_ALTAR_STRUCTURE);
        for (Biome biome: Registry.BIOME) {
            if (biome == Biomes.BAMBOO_JUNGLE_HILLS || biome == Biomes.BAMBOO_JUNGLE || biomeList.contains(biome)) {
                biome.addStructureFeature(ELEVATOR_ALTAR_CONFIGURED);
            }
        }
    }
}
