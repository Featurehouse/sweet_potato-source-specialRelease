package org.featurehouse.spm.util

import net.minecraft.entity.Entity
import net.minecraft.entity.damage.DamageSource

fun Entity.handleFallDamage(a: Float, b: Float) = this.handleFallDamage(a, b, DamageSource.FALL)