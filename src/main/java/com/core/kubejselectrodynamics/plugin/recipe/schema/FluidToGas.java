package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.fluid.TagFluidStackJS;
import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidToGas {

    public static class FluidToGasRecipeJS extends ElectrodynamicsRecipeJS {
        @Override
        public InputFluid readInputFluid(Object from) {
            return ElectroFluidWrapper.of(from);
        }
    }

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
