package com.core.kubejselectrodynamics.plugin.recipe.schema;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class ItemToItem {

    public static class ItemToItemRecipeJS extends RecipeJS {

    }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            ItemToItemRecipeJS.class,
            ItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.ITEM_INPUTS,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE,
            Keys.ITEM_BI
    );
}
