package com.core.kubejselectrodynamics.plugin.recipe.schema;

import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidToGas {

    public static class FluidToGasRecipeJS extends ElectrodynamicsRecipeJS { }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidToGasRecipeJS.class,
            FluidToGasRecipeJS::new,
            Keys.GAS_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE,
            Keys.GAS_BI_EXACT_1
    );
}
