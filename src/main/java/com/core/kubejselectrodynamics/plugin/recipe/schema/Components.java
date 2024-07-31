package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.rhino.NativeArray;
import it.unimi.dsi.fastutil.Arrays;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import java.sql.Array;

public class Components {
    public static final OrRecipeComponent<InputItem, TagKey<Item>> ITEM_OR_TAG = ItemComponents.INPUT.or(TagKeyComponent.ITEM);
    public static final OrRecipeComponent<Long, Either<InputItem, TagKey<Item>>> ARRAY_ITEM_ELEMENT_INPUT = NumberComponent.LONG.or(ITEM_OR_TAG);
    public static final OrRecipeComponent<InputFluid, TagKey<Fluid>> FLUID_OR_TAG = FluidComponents.INPUT.or(TagKeyComponent.FLUID);
    public static final OrRecipeComponent<Long, Either<InputFluid, TagKey<Fluid>>> ARRAY_FLUID_ELEMENT_INPUT = NumberComponent.LONG.or(FLUID_OR_TAG);
    public static final OrRecipeComponent<Long, OutputItem> ARRAY_ITEM_ELEMENT_OUTPUT = NumberComponent.LONG.or(ItemComponents.OUTPUT);
    public static final OrRecipeComponent<Long, OutputFluid> ARRAY_FLUID_ELEMENT_OUTPUT = NumberComponent.LONG.or(FluidComponents.OUTPUT);
    public static final StringComponent ARRAY_KEY = new StringComponent("must be \"count\" or a number in a string", s -> s.matches("^(count|[0-9]+)$"));

    private static final String COUNT = "count";
    public static final RecipeComponent<InputItem[]> ARRAY_ITEM_INPUT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return InputItem[].class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, InputItem[] value) {
            JsonObject json = new JsonObject();
            json.addProperty(COUNT, value.length);
            int i = 0;
            for (InputItem item : value) {
                json.add(String.valueOf(i), recipe.writeInputItem(item));
                i++;
            }
            return json;
        }

        @Override
        public InputItem[] read(RecipeJS recipe, Object from) {
            if (from instanceof InputItem[] item) {
                return item;
            } else if (from instanceof InputItem item) {
                return new InputItem[]{item};
            } else if (from instanceof String string) {
                return new InputItem[]{InputItem.of(string)};
            } else if (from instanceof NativeArray array) {
                long longCount = array.getLength();
                if (longCount > Integer.MAX_VALUE) {
                    throw new IllegalArgumentException("Array is too long! Max size: " + Integer.MAX_VALUE);
                }
                int count = (int)longCount;
                InputItem[] result = new InputItem[count];
                for (int i = 0; i < count; i++) {
                    result[i] = InputItem.of(array.get(i));
                }
                return result;
            } else if (from instanceof JsonObject object) {
                if (!object.has(COUNT)) {
                    throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                }
                int count = object.get(COUNT).getAsInt();
                InputItem[] result = new InputItem[count];
                for (int i = 0; i < count; i++) {
                    result[i] = InputItem.of(object.get(String.valueOf(i)));
                }
                return result;
            } else {
                throw new IllegalArgumentException("Expected JSON object!");
            }
        }
    };
    public static final RecipeComponent<OutputItem[]> ARRAY_ITEM_OUTPUT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return OutputItem[].class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, OutputItem[] value) {
            JsonObject json = new JsonObject();
            json.addProperty(COUNT, value.length);
            int i = 0;
            for (OutputItem item : value) {
                json.add(String.valueOf(i), recipe.writeOutputItem(item));
                i++;
            }
            return json;
        }

        @Override
        public OutputItem[] read(RecipeJS recipe, Object from) {
            if (from instanceof OutputItem[] items) {
                return items;
            } else if (from instanceof OutputItem item) {
                return new OutputItem[]{item};
            } else if (from instanceof String string) {
                return new OutputItem[]{OutputItem.of(string)};
            } else if (from instanceof NativeArray array) {
                long longCount = array.getLength();
                if (longCount > Integer.MAX_VALUE) {
                    throw new IllegalArgumentException("Array is too long! Max size: " + Integer.MAX_VALUE);
                }
                int count = (int)longCount;
                OutputItem[] result = new OutputItem[count];
                for (int i = 0; i < count; i++) {
                    result[i] = OutputItem.of(array.get(i));
                }
                return result;
            } else if (from instanceof JsonObject object) {
                if (!object.has(COUNT)) {
                    throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                }
                int count = object.get(COUNT).getAsInt();
                OutputItem[] result = new OutputItem[count];
                for (int i = 0; i < count; i++) {
                    result[i] = OutputItem.of(object.get(String.valueOf(i)));
                }
                return result;
            } else {
                throw new IllegalArgumentException("Expected JSON object!");
            }
        }
    };
}
