package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.fluid.ElectroFluidStackJS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidItemToFluid {

    public static class FluidItemToFluidRecipeJS extends ElectrodynamicsRecipeJS {
        @Override
        public OutputFluid readOutputFluid(Object from) {
            return ElectroFluidStackJS.of(from);
        }
        @Override
        public JsonElement writeOutputFluid(OutputFluid value) {
            if (value instanceof ElectroFluidStackJS stack) {
                return stack.toJson();
            } else if (value instanceof FluidStackJS stack) {
                return stack.toJson();
            } else {
                throw new IllegalArgumentException("Unexpected fluid object!");
            }
        }
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
        @Override
        public JsonElement writeInputItem(InputItem item) {
            JsonObject json = item.ingredient.toJson().getAsJsonObject();
            json.addProperty("count", item.count);
            return json;
        }
    }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidItemToFluidRecipeJS.class,
            FluidItemToFluidRecipeJS::new,
            Keys.FLUID_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS
    );
}
