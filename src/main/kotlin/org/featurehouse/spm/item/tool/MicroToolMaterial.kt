package org.featurehouse.spm.item.tool

import net.minecraft.item.ToolMaterial
import net.minecraft.recipe.Ingredient
import org.featurehouse.spm.SPMFools

object MicroToolMaterial : ToolMaterial {
    override fun getDurability() = 109
    override fun getMiningSpeedMultiplier() = 0.0F
    override fun getAttackDamage() = 5.0F
    override fun getMiningLevel() = 0       // fw
    override fun getEnchantability() = 0    // fw
    override fun getRepairIngredient(): Ingredient = Ingredient.ofItems(SPMFools.CURSEFORGE_INGOT)
}