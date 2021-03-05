package org.featurehouse.spm;

import org.featurehouse.spm.items.SteveSpawnEggItem;
import org.featurehouse.spm.items.interactions.ItemInteractions;
import org.featurehouse.spm.util.properties.objects.ItemSettings;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;

import static org.featurehouse.spm.util.registries.FoolsRegistryHelper.*;

public class SPMFools implements ModInitializer {
    public static final String FOOLS_ID = "spmfools21";

    public static final Item STEVE_SPAWN_EGG;

    @Override
    public void onInitialize() {
        ItemInteractions.init();
    }

    static {
        STEVE_SPAWN_EGG = item("steve_spawn_egg", new SteveSpawnEggItem(ItemSettings.UNC_MISC));
    }
}
