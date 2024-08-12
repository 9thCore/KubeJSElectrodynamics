package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import com.core.kubejselectrodynamics.util.ItemUtils;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidItemToFluid {

    public static class FluidItemToFluidRecipeJS extends ElectrodynamicsRecipeJS {
        @Override
        public OutputFluid readOutputFluid(Object from) {
            return ElectroFluidWrapper.of(from);
        }
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
            FluidItemToFluidRecipeJS.class,
            FluidItemToFluidRecipeJS::new,
            Keys.FLUID_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
