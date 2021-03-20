package org.featurehouse.spm.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import org.featurehouse.spm.SPMFools;
import org.featurehouse.spm.client.item.NullPointerExceptionModelPredicateProvider;
import org.featurehouse.spm.client.render.GlintPigEntityRenderer;
import org.featurehouse.spm.item.NullPointerExceptionItem;
import org.featurehouse.spm.mixin.ClientWorldAccessor;

@Environment(EnvType.CLIENT)
public class SPMFoolsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ?
                  0x009999 /*Blue*/ : 0x865334 /*Brown*/ , SPMFools.STEVE_SPAWN_EGG);
        ClientTickEvents.START_WORLD_TICK.register(clientWorld -> {
            if (clientWorld.getTime() % 8160 == 805) {
                ClientPlayerEntity player = ((ClientWorldAccessor) clientWorld).getClient().player;
                if (player == null) return;
                clientWorld.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SPMFools.SERVER_CRASHING, SoundCategory.MUSIC, 1.0F, 1.0F);
            }
        });

        FabricModelPredicateProviderRegistry.register(SPMFools.NULLPOINTEREXCEPTION,
                new Identifier(SPMFools.FOOLS_ID, "angle"), new NullPointerExceptionModelPredicateProvider());
        EntityRendererRegistry.INSTANCE.register(SPMFools.GLINT_PIG,
                (entityRenderDispatcher, context) -> new GlintPigEntityRenderer(entityRenderDispatcher));
        ClientPlayNetworking.registerGlobalReceiver(NullPointerExceptionItem.Companion.getClientCrashAccept(),
                (minecraftClient, clientPlayNetworkHandler, packetByteBuf, packetSender) -> minecraftClient.execute(() -> {
                    throw new CrashException(new CrashReport("I TOLD YOU NOT TO RIGHT CLICK!", new NullPointerException("I TOLD YOU NOT TO RIGHT CLICK!")));
                }));
    }
}
