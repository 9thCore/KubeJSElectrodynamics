package com.core.kubejselectrodynamics.plugin.recipe.schema;

import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidToItem {

    public static class FluidToItemRecipeJS extends ElectrodynamicsRecipeJS { }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidToItemRecipeJS.class,
            FluidToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            // Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
