package org.featurehouse.spm.world.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.featurehouse.spm.structures.GlintPigHellHelperKt;
import org.featurehouse.spm.world.levelmeta.SPMLevelPropertiesHelper;

public class WorldEvents {
    private static final Logger LOGGER = LogManager.getLogger("SPM World Event Loader");

    public static void init() {
        ServerWorldEvents.LOAD.register((minecraftServer, serverWorld) -> {
            if (serverWorld.getRegistryKey() == World.OVERWORLD &&
                    !SPMLevelPropertiesHelper.hasInitializedGlintPig(minecraftServer)) {
                if (GlintPigHellHelperKt.fillPigHell(serverWorld)) {
                    SPMLevelPropertiesHelper.setInitializedGlintPig(minecraftServer);
                } else {
                    LOGGER.error("Cannot load structure spmfools21:pig_hell",
                            new NullPointerException("Cannot find structure file spmfools21:pig_hell : null"));
                }
            }
        });
    }
}
