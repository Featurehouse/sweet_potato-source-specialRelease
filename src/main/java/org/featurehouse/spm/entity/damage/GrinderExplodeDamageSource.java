package org.featurehouse.spm.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class GrinderExplodeDamageSource extends DamageSource {
    protected GrinderExplodeDamageSource() {
        super("spmfools21.grinder_explode");
        this.setExplosive();
    }

    public static GrinderExplodeDamageSource of() {
        return new GrinderExplodeDamageSource();
    }
}
