package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import dev.latvian.mods.kubejs.fluid.FluidLike;
import dev.latvian.mods.kubejs.recipe.OutputReplacement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;

public interface OutputGas extends GasLike, OutputReplacement {
    @Override
    default Object replaceOutput(RecipeJS recipe, ReplacementMatch match, OutputReplacement original) {
        if (original instanceof GasLike o) {
            return kjs$copy(o.kjs$getAmount());
        }

        return kjs$copy(kjs$getAmount());
    }
}
