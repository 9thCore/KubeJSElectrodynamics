package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import com.core.kubejselectrodynamics.util.ItemUtils;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidItemToItem {
    public static class FluidItemToItemRecipeJS extends ElectrodynamicsRecipeJS {
        @Override
        public InputFluid readInputFluid(Object from) {
            return ElectroFluidWrapper.of(from);
        }

        @Override
        public JsonElement writeInputItem(InputItem item) {
            return ItemUtils.writeInputItem(item);
        }
    }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidItemToItemRecipeJS.class,
            FluidItemToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
