package org.featurehouse.spm.mixin.global;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.featurehouse.spm.SPMFools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public class MixinOverworldOres {
    /**
     * Copied from
     * @see DefaultBiomeFeatures#addDefaultOres(Biome)
     */
    @Inject(at = @At("RETURN"), method = "addDefaultOres(Lnet/minecraft/world/biome/Biome;)V")
    private static void addOverworldOresInSPM(Biome biome, CallbackInfo ci) {
        biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                Feature.ORE.configure(new OreFeatureConfig(
                        OreFeatureConfig.Target.NATURAL_STONE, SPMFools.CURSEFORGE_ORE.getDefaultState(), 8
                )).createDecoratedFeature(Decorator.COUNT_RANGE.configure(new RangeDecoratorConfig(8, 0, 0, 16))));
    }
}
