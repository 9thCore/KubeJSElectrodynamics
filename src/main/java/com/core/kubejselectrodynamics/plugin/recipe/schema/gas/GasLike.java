package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;

@RemapPrefixForJS("kjs$")
public interface GasLike extends ReplacementMatch {
    double kjs$getAmount();

    default boolean kjs$isEmpty() {
        return kjs$getAmount() <= 0L;
    }

    default GasLike kjs$copy(double amount) {
        return this;
    }

    default boolean matches(GasLike other) {
        return equals(other);
    }
}
