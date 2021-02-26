package io.featurehouse.spm.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class SteveSpawnDamageSource extends DamageSource {
    protected SteveSpawnDamageSource() {
        super("spmfools21.steve_spawn");
    }

    public static SteveSpawnDamageSource of() {
        return new SteveSpawnDamageSource();
    }
}
