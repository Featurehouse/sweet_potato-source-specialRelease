package org.featurehouse.spm.world.levelmeta;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SaveProperties;
import org.jetbrains.annotations.NotNull;

public class SPMLevelPropertiesHelper {
    public static final int DATA_VERSION = 16;  // alpha.5pi.

    public static int getLevelSPMDataVersion(MinecraftServer server) {
        return getLevelSPMDataVersion(server.getSaveProperties());
    }

    public static int getLevelSPMDataVersion(SaveProperties properties) {
        return getLevelSPMDataVersion((SPMLevelProperties) properties);
    }

    /* Zero: wasn't loaded */
    public static int getLevelSPMDataVersion(SPMLevelProperties spmLevelProperties) {
        CompoundTag tag = spmLevelProperties.sweetPotato_getSPMMetaRaw();
        return tag.getInt("DataVersion");
    }

    public static void setLevelSPMDataVersion(SPMLevelProperties spmLevelProperties, int dataVersion) {
        spmLevelProperties.sweetPotato_getSPMMetaRaw().putInt("DataVersion", dataVersion);
    }

    public static void setCurrentSPMDataVersion(SPMLevelProperties spmLevelProperties) {
        setLevelSPMDataVersion(spmLevelProperties, DATA_VERSION);
    }

    @NotNull /* Empty Compound Tag: not initialized. */
    public static CompoundTag getFoolsData(MinecraftServer server) {
        CompoundTag tag = ((SPMLevelProperties) server.getSaveProperties()).sweetPotato_getSPMMetaRaw();
        return tag.getCompound("spmfools21");
    }

    /* False: Not Initialized */
    public static boolean hasInitializedGlintPig(MinecraftServer server) {
        CompoundTag tag = getFoolsData(server);
        return tag.getBoolean("HasInitializedGlintPig");
    }

    public static void setInitializedGlintPig(MinecraftServer server) {
        CompoundTag tag = getFoolsData(server);
        tag.putBoolean("HasInitializedGlintPig", true);
    }
}
