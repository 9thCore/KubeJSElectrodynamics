package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public class FluidItemToGas {

    public static class FluidItemToGasRecipeJS extends ElectrodynamicsRecipeJS {
        @Override
        public InputFluid readInputFluid(Object from) {
            return ElectroFluidWrapper.of(from);
        }
    }

    public static RecipeSchema SCHEMA = new RecipeSchema(
            FluidItemToGasRecipeJS.class,
            FluidItemToGasRecipeJS::new,
            Keys.GAS_OUTPUT,
            Keys.FLUID_INPUTS_EXACT_1,
            Keys.ITEM_INPUTS_EXACT_1,
            Keys.USAGE_PER_TICK,
            Keys.TICKS,
            Keys.EXPERIENCE
    );
}
