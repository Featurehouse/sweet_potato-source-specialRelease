package org.featurehouse.spm.client

import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.featurehouse.spm.SPMFools
import org.featurehouse.spm.screen.DeepDarkFantasyScreenHandler

@Environment(EnvType.CLIENT)
class DeepDarkFantasyScreen(handler: DeepDarkFantasyScreenHandler, inventory: PlayerInventory, title: Text) : HandledScreen<DeepDarkFantasyScreenHandler>(handler, inventory, title) {
    override fun drawBackground(matrices: MatrixStack?, delta: Float, mouseX: Int, mouseY: Int) {
        this.client!!.textureManager.bindTexture(BACKGROUND_TEXTURE)
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight)
        if (this.handler.isWorking)
            this.drawTexture(matrices, this.x + 81, this.y + 31, 176, 17, 1, 18)
        val progress: Int = this.handler.sublimateProcess
        this.drawTexture(matrices, this.x + 74, this.y + 43, 176, 0, progress + 1, 16)
    }

    override fun drawForeground(matrices: MatrixStack?, mouseX: Int, mouseY: Int) {
        RenderSystem.disableBlend()
        super.drawForeground(matrices, mouseX, mouseY)
        val ingredientData = this.handler.ingredientData
        this.textRenderer.draw(matrices, ingredientData.toString(), 5.0f, 3.0f, 0)
    }

    @Environment(EnvType.CLIENT)
    companion object {
        private val BACKGROUND_TEXTURE = Identifier(SPMFools.FOOLS_ID, "textures/gui/container/deep_dark_fantasy.png")
    }

    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        RenderSystem.disableBlend()
        this.drawMouseoverTooltip(matrices, mouseX, mouseY)
    }
}