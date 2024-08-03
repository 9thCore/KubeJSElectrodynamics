package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.fluid.TagFluidStackJS;
import com.core.kubejselectrodynamics.util.CountableIngredientInterface;
import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import com.core.kubejselectrodynamics.util.IngredientInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

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
            if (item.ingredient instanceof CountableIngredient countable) {
                ArrayList<ItemStack> stacks = countable.fetchCountedStacks();
                if (stacks.size() == 1) {
                    Ingredient ing = Ingredient.of(stacks.get(0));
                    JsonObject json = ing.toJson().getAsJsonObject();
                    json.addProperty("count", stacks.get(0).getCount());
                    return json;
                } else {
                    // Try to find the tag
                    try {
                        CountableIngredientInterface countableInterface = (CountableIngredientInterface) countable;
                        Ingredient ingredient = countableInterface.kjsElectro$getIngredient();
                        Ingredient.Value[] values = ((IngredientInterface) ingredient).kjsElectro$getIngredientValues();
                        int i = 0;
                        for (Ingredient.Value value : values) {
                            if (value instanceof Ingredient.TagValue) {
                                JsonObject json = value.serialize();
                                json.addProperty("count", stacks.get(0).getCount());
                                if (i < values.length - 1) {
                                    ConsoleJS.SERVER.warn("Electrodynamics recipes only support one type of ingredient. Chosen first tag value at index " + i + ": " + json);
                                }
                                return json;
                            }
                            i++;
                        }

                        // Could not find tag value, use first value instead
                        JsonObject json = values[0].serialize();
                        json.addProperty("count", stacks.get(0).getCount());
                        return json;
                    } catch(Exception ex) {
                        ConsoleJS.SERVER.error("Fatal flaw!! Could not write input item of type CountableIngredient?? " + ex);
                    }
                }
            }
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
