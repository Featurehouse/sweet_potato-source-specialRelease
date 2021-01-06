package io.github.teddyxlandlee.mcbridge.client.tooltip;

import io.github.teddyxlandlee.annotation.CopiedFrom1_17;
import io.github.teddyxlandlee.mcbridge.client.tooltip.data.TooltipData;
import io.github.teddyxlandlee.sweet_potato.tooltips.BundledSockTooltipComponent;
import io.github.teddyxlandlee.sweet_potato.tooltips.data.BundledSockTooltipData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.Matrix4f;

@CopiedFrom1_17("net/minecraft/client/gui/tooltip/TooltipComponent")
@Deprecated
@Environment(EnvType.CLIENT)
public interface TooltipComponent {
    static TooltipComponent of(OrderedText text) {
        return new OrderedTextTooltipComponent(text);
    }

    static TooltipComponent of(TooltipData data) {
        if (data instanceof BundledSockTooltipData) {
            return new BundledSockTooltipComponent((BundledSockTooltipData) data);
        } else
            throw new IllegalArgumentException("Unknown TooltipComponent");
    }

    int getHeight();

    int getWidth(TextRenderer textRenderer);

    default void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix4f, VertexConsumerProvider.Immediate immediate) {}

    default void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z, TextureManager textureManager) {}
}
