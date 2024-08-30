package com.core.kubejselectrodynamics.plugin.recipe.schema;

import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidItemToItem {
    public static class FluidItemToItemRecipeJS extends ElectrodynamicsRecipeJS { }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidItemToItemRecipeJS.class,
            FluidItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_1,
            // Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );

    public static RecipeSchema MSRFUEL_PREPROCESSOR_SCHEMA = new RecipeSchema(
            FluidItemToItemRecipeJS.class,
            FluidItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_3,
            // Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
