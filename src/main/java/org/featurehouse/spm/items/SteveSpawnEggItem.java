package org.featurehouse.spm.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

/**
 * @see net.minecraft.item.SpawnEggItem
 */
public class SteveSpawnEggItem extends Item {
    public SteveSpawnEggItem(Settings settings) {
        super(settings);
    }

    public static ActionResult onUse(World world, Vec3d vec3d) {
        if (world.isClient) return ActionResult.SUCCESS;
        //this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 1.0F, Explosion.DestructionType.BREAK);
        world.createExplosion(null, vec3d.x, vec3d.y, vec3d.z, 2.0F, false, Explosion.DestructionType.DESTROY);
        if (world.getRandom().nextFloat() < 0.05F) {
            ZombieVillagerEntity zombieVillager = new ZombieVillagerEntity(EntityType.ZOMBIE_VILLAGER, world);
            zombieVillager.setPos(vec3d.x, vec3d.y, vec3d.z);
            zombieVillager.setBaby(true);
            zombieVillager.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200));
            world.spawnEntity(zombieVillager);
        }
        return ActionResult.CONSUME;
    }
}
