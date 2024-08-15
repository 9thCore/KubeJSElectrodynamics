package com.core.kubejselectrodynamics.util;

import com.core.kubejselectrodynamics.util.mixinterfaces.ICountableIngredient;
import com.core.kubejselectrodynamics.util.mixinterfaces.IIngredient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

public class ItemUtils {
    public static JsonElement writeInputItem(InputItem item) {
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
                    ICountableIngredient countableInterface = (ICountableIngredient) countable;
                    Ingredient ingredient = countableInterface.kjsElectro$getIngredient();
                    Ingredient.Value[] values = ((IIngredient) ingredient).kjsElectro$getIngredientValues();
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

    public static JsonElement fluidContainerModel(JsonElement textures, String baseFluid, boolean luminosity, boolean tint) {
        JsonObject model = new JsonObject();
        model.addProperty("parent", "forge:item/default");
        model.addProperty("apply_fluid_luminosity", luminosity);
        model.addProperty("apply_tint", tint);
        model.addProperty("fluid", baseFluid);
        model.addProperty("loader", "forge:fluid_container");
        model.add("textures", textures);
        return model;
    }
}
