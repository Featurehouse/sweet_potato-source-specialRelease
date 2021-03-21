package org.featurehouse.spm.util.properties.fantasy

data class NullDeepDarkFantasyProperties(private var ingredientData: Int = Int.MIN_VALUE + 1, private var sublimateTime: Short = (Short.MIN_VALUE + 1).toShort()) : IntDeepDarkFantasyProperties {
    override fun getIngredientData() = ingredientData
    override fun setIngredientData(ingredientData: Int) { this.ingredientData = ingredientData }
    override fun getSublimateTime() = sublimateTime
    override fun setSublimateTime(sublimateTime: Short) { this.sublimateTime = sublimateTime }
}