package org.featurehouse.spm.client.render

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.entity.EntityRenderDispatcher
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer
import net.minecraft.client.render.entity.model.PigEntityModel
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.featurehouse.spm.SPMFools
import org.featurehouse.spm.entity.passive.GlintPigEntity

@Environment(EnvType.CLIENT)
class GlintPigEntityRenderer(entityRenderDispatcher: EntityRenderDispatcher)
    : MobEntityRenderer<GlintPigEntity, PigEntityModel<GlintPigEntity>>(entityRenderDispatcher, PigEntityModel(), .7F) {

    init {
        this.addFeature(SaddleFeatureRenderer(this, PigEntityModel(.5F), Identifier("textures/entity/pig/pig_saddle.png")))
    }

    override fun getTexture(entity: GlintPigEntity?)
        = Identifier(SPMFools.FOOLS_ID, "textures/entity/glint_pig.png")

    override fun getBlockLight(entity: GlintPigEntity?, blockPos: BlockPos?) = 15
}