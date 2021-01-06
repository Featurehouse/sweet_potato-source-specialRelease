package io.github.teddyxlandlee.sweet_potato.util;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.teddyxlandlee.mcbridge.client.tooltip.TooltipComponent;
import io.github.teddyxlandlee.mcbridge.client.tooltip.data.TooltipData;
import io.github.teddyxlandlee.sweet_potato.items.Tooltipped;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Deprecated
@Environment(EnvType.CLIENT)
public final class RenderUtils {
    private RenderUtils() {}

    @Deprecated
    public static Optional<TooltipData> optionalTooltipData(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof Tooltipped)
            return Optional.of(((Tooltipped) item).getTooltipData(stack));
        return Optional.empty();
    }

    @Deprecated
    public static void renderTooltip(Screen instance, MatrixStack matrices, List<Text> lines, Optional<TooltipData> data, int x, int y) {
        List<TooltipComponent> list = lines.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Collectors.toList());
        data.ifPresent(tooltipData -> list.add(1, TooltipComponent.of(tooltipData)));

        renderTooltipFromComponents(instance, matrices, list, x, y);
    }

    @Deprecated
    private static void renderTooltipFromComponents(Screen instance, MatrixStack matrices, List<TooltipComponent> components, int x, int y) {
        if (!components.isEmpty()) {
            int i = 0;
            int j = components.size() == 1 ? -2 : 0;

            TooltipComponent tooltipComponent;
            for (Iterator<?> var7 = components.iterator(); var7.hasNext(); j += tooltipComponent.getHeight()) {
                tooltipComponent = (TooltipComponent) var7.next();
                int k = tooltipComponent.getWidth(instance.textRenderer);
                if (k > i) {
                    i = k;
                }
            }

            int l = x + 12;
            int m = y - 12;
            if (l + i > instance.width) {
                l -= 28 + i;
            }

            if (m + j + 6 > instance.height) {
                m = instance.height - j - 6;
            }

            matrices.push();

            float f = instance.itemRenderer.zOffset;
            instance.itemRenderer.zOffset = 400.0F;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(5, VertexFormats.POSITION_COLOR);
            Matrix4f matrix4f = matrices.peek().getModel();
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 4, l + i + 3, m - 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 3, m + j + 3, l + i + 3, m + j + 4, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 3, l + i + 3, m + j + 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 4, m - 3, l - 3, m + j + 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l + i + 3, m - 3, l + i + 4, m + j + 3, 400, -267386864, -267386864);
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 3 + 1, l - 3 + 1, m + j + 3 - 1, 400, 1347420415, 1344798847);
            fillGradient(matrix4f, bufferBuilder, l + i + 2, m - 3 + 1, l + i + 3, m + j + 3 - 1, 400, 1347420415, 1344798847);
            fillGradient(matrix4f, bufferBuilder, l - 3, m - 3, l + i + 3, m - 3 + 1, 400, 1347420415, 1347420415);
            fillGradient(matrix4f, bufferBuilder, l - 3, m + j + 2, l + i + 3, m + j + 3, 400, 1344798847, 1344798847);
            RenderSystem.enableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.shadeModel(7425);
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            RenderSystem.shadeModel(7424);
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            matrices.translate(0.0D, 0.0D, 400.0D);
            int t = m;

            int v;
            TooltipComponent tooltipComponent3;
            for (v = 0; v < components.size(); ++v) {
                tooltipComponent3 = components.get(v);
                tooltipComponent3.drawText(instance.textRenderer, l, t, matrix4f, immediate);
                t += tooltipComponent3.getHeight() + (v == 0 ? 2 : 0);
            }

            immediate.draw();
            matrices.pop();
            t = m;

            for (v = 0; v < components.size(); ++v) {
                tooltipComponent3 = components.get(v);
                assert instance.client != null;
                tooltipComponent3.drawItems(instance.textRenderer, l, t, matrices, instance.itemRenderer, 400, instance.client.getTextureManager());
                t += tooltipComponent3.getHeight() + (v == 0 ? 2 : 0);
            }

            instance.itemRenderer.zOffset = f;
        }
    }

    @Deprecated
    protected static void fillGradient(Matrix4f matrix, BufferBuilder bufferBuilder, int xStart, int yStart, int xEnd, int yEnd, int z, int colorStart, int colorEnd) {
        float f = (float) (colorStart >> 24 & 255) / 255.0F;
        float g = (float) (colorStart >> 16 & 255) / 255.0F;
        float h = (float) (colorStart >> 8 & 255) / 255.0F;
        float i = (float) (colorStart & 255) / 255.0F;
        float j = (float) (colorEnd >> 24 & 255) / 255.0F;
        float k = (float) (colorEnd >> 16 & 255) / 255.0F;
        float l = (float) (colorEnd >> 8 & 255) / 255.0F;
        float m = (float) (colorEnd & 255) / 255.0F;
        bufferBuilder.vertex(matrix, (float) xEnd, (float) yStart, (float) z).color(g, h, i, f).next();
        bufferBuilder.vertex(matrix, (float) xStart, (float) yStart, (float) z).color(g, h, i, f).next();
        bufferBuilder.vertex(matrix, (float) xStart, (float) yEnd, (float) z).color(k, l, m, j).next();
        bufferBuilder.vertex(matrix, (float) xEnd, (float) yEnd, (float) z).color(k, l, m, j).next();
    }
}

