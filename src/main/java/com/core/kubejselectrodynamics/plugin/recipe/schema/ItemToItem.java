package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.util.ItemUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class ItemToItem {

    public static class ItemToItemRecipeJS extends ElectrodynamicsRecipeJS {
        @Override
        public JsonElement writeInputItem(InputItem item) {
            return ItemUtils.writeInputItem(item);
        }
    }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            ItemToItemRecipeJS.class,
            ItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.ITEM_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE,
            Keys.ITEM_BI_EXACT_1
    );

    public static RecipeSchema ALLOYER_SCHEMA = new RecipeSchema(
            ItemToItemRecipeJS.class,
            ItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.ITEM_INPUTS_EXACT_2,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE,
            Keys.ITEM_BI_EXACT_1
    );

    public static RecipeSchema OXIDIZER_FURNACE_SCHEMA = new RecipeSchema(
            ItemToItemRecipeJS.class,
            ItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.ITEM_INPUTS_EXACT_2,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE,
            Keys.ITEM_BI_EXACT_1
    );

    public static RecipeSchema FISSION_REACTOR_SCHEMA = new RecipeSchema(
            ItemToItemRecipeJS.class,
            ItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.ITEM_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
