package com.core.kubejselectrodynamics.plugin.recipe.schema;

import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidItemToFluid {

    public static class FluidItemToFluidRecipeJS extends ElectrodynamicsRecipeJS { }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidItemToFluidRecipeJS.class,
            FluidItemToFluidRecipeJS::new,
            Keys.FLUID_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_1,
            // Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
