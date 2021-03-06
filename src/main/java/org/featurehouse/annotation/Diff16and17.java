package org.featurehouse.annotation;

/**
 * Features should be different between 1.16.x and 1.17.x.
 */
public @interface Diff16and17 {
    /**
     * Type of the difference.<br />
     * Values: {@code code, data-pack-tag, resource-pack-models}, etc.
     */
    String[] value() default "code";
}
