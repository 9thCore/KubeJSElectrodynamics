package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.OutputGas;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;

public class Keys {
    public static final RecipeKey<InputItem[]> ITEM_INPUTS_EXACT_1 = Components.ARRAY_ITEM_INPUT(1).key("iteminputs").preferred("itemInputs");
    public static final RecipeKey<InputItem[]> ITEM_INPUTS_EXACT_2 = Components.ARRAY_ITEM_INPUT(2).key("iteminputs").preferred("itemInputs");
    public static final RecipeKey<OutputItem[]> ITEM_BI_EXACT_1 = Components.ARRAY_ITEM_OUTPUT(1).key("itembi").preferred("itemByproducts").defaultOptional();
    public static final RecipeKey<InputFluid[]> FLUID_INPUTS_EXACT_1 = Components.ARRAY_FLUID_INPUT(1).key("fluidinputs").preferred("fluidInputs");
    public static final RecipeKey<OutputGas[]> GAS_BI_EXACT_1 = Components.ARRAY_GAS_OUTPUT(1).key("gasbi").preferred("gasByproducts");
    public static final RecipeKey<OutputItem> ITEM_OUTPUT = ItemComponents.OUTPUT.key("output");
    public static final RecipeKey<OutputFluid> FLUID_OUTPUT = FluidComponents.OUTPUT.key("output");
    public static final RecipeKey<OutputGas> GAS_OUTPUT = Components.GAS_OUTPUT.key("output");
    public static final RecipeKey<Double> EXPERIENCE = NumberComponent.ANY_DOUBLE.key("experience").preferred("xp").optional(0.0D);
    public static final RecipeKey<Long> TICKS = TimeComponent.TICKS.key("ticks");
    public static final RecipeKey<Double> USAGE_PER_TICK = NumberComponent.DOUBLE.key("usagepertick").preferred("usagePerTick");
}
