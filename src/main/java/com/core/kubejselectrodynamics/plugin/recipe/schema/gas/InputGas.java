package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import dev.latvian.mods.kubejs.recipe.InputReplacement;
import dev.latvian.mods.kubejs.recipe.OutputReplacement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;

public interface InputGas extends GasLike, InputReplacement {
    @Override
    default Object replaceInput(RecipeJS recipe, ReplacementMatch match, InputReplacement original) {
        if (original instanceof GasLike o) {
            return kjs$copy(o.kjs$getAmount());
        }

        return kjs$copy(kjs$getAmount());
    }
}
