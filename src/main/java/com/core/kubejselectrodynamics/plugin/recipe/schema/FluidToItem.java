package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.fluid.ElectroFluidStackJS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.InputReplacement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidToItem {

    public static class FluidToItemRecipeJS extends RecipeJS {
        @Override
        public InputFluid readInputFluid(Object from) {
            return ElectroFluidStackJS.of(from);
        }

        @Override
        public JsonElement writeInputFluid(InputFluid value) {
            if (value instanceof ElectroFluidStackJS stack) {
                return stack.toJson();
            } else if (value instanceof FluidStackJS stack) {
                return stack.toJson();
            } else {
                throw new IllegalArgumentException("Unexpected fluid object!");
            }
        }
    }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidToItemRecipeJS.class,
            FluidToItemRecipeJS::new,
            Keys.ITEM_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS
    );
}
