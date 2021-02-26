package io.featurehouse.spm;

import io.featurehouse.spm.items.SteveSpawnEggItem;
import io.featurehouse.spm.items.interactions.ItemInteractions;
import io.featurehouse.spm.util.properties.objects.ItemSettings;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;

import static io.featurehouse.spm.util.registries.FoolsRegistryHelper.*;

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
