package org.featurehouse.spm.entity.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.World;
import org.featurehouse.spm.SPMFools;

public class GlintPigEntity extends PigEntity {
    public GlintPigEntity(World world) {
        this(SPMFools.GLINT_PIG, world);
    }

    public GlintPigEntity(EntityType<GlintPigEntity> entityType, World world) {
        super(entityType, world);
    }
}