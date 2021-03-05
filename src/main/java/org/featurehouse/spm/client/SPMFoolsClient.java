package org.featurehouse.spm.client;

import org.featurehouse.spm.SPMFools;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

@Environment(EnvType.CLIENT)
public class SPMFoolsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ?
                  0x009999 /*Blue*/ : 0x865334 /*Brown*/ , SPMFools.STEVE_SPAWN_EGG);
    }
}
