package org.featurehouse.spm.util.properties.fantasy;

import net.minecraft.screen.PropertyDelegate;

public interface IntDeepDarkFantasyProperties extends PropertyDelegate {
    int getIngredientData();
    short getSublimateTime();

    void setIngredientData(int ingredientData);
    void setSublimateTime(short sublimateTime);

    @Override
    default int get(int index) {
        switch (index) {
            case 0: return getIngredientData();
            case 1: return getSublimateTime();
            default:return Integer.MIN_VALUE;
        }
    }

    @Override
    default void set(int index, int value) {
        switch (index) {
            case 0:
                setIngredientData(value);
                break;
            case 1:
                setSublimateTime((short) value);
        }
    }

    @Override
    default int size() { return 2; }
}
