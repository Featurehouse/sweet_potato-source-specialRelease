package io.github.teddyxlandlee.sweet_potato.tooltips;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.teddyxlandlee.mcbridge.client.tooltip.TooltipComponent;
import io.github.teddyxlandlee.sweet_potato.tooltips.data.BundledSockTooltipData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
@Deprecated
public class BundledSockTooltipComponent implements TooltipComponent {
    private final DefaultedList<ItemStack> inventory;

    public BundledSockTooltipComponent(BundledSockTooltipData bundledSockTooltipData) {
        this.inventory = bundledSockTooltipData.getInventory();
    }

    private int getStackCount() {
        return this.inventory.size();
    }

    private int getDisplayColumns() {
        return MathHelper.ceil(Math.sqrt(this.getStackCount()));
    }

    @Override
    public int getHeight() {
        int stackCount = this.getStackCount();


        return 18 * (1 + (stackCount - 1) / this.getDisplayColumns()) + 4;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return this.getDisplayColumns() * 10;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z, TextureManager textureManager) {
        int i = 0, j = 0;
        int k = this.getDisplayColumns();

        for (ItemStack itemStack : this.inventory) {
            this.drawSlot(matrices, i + x - 1, j + y - 1, z, textureManager);
            itemRenderer.innerRenderInGui(MinecraftClient.getInstance().player, itemStack, i, j);
            itemRenderer.renderGuiItemOverlay(textRenderer, itemStack, x + i, y + j);
            i += 18;
            if (i >= 18 * k) {
                i = 0;
                j += 18;
            }
        }
    }

    private void drawSlot(MatrixStack matrices, int x, int y, int z, TextureManager textureManager) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        textureManager.bindTexture(DrawableHelper.STATS_ICON_TEXTURE);
        DrawableHelper.drawTexture(matrices, x, y, z, 0.0F, 0.0F, 18, 18, 128, 128);
    }
}
