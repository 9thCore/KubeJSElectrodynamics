package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.ElectroGasStackJS;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.GasLike;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.OutputGas;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidLike;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.rhino.NativeArray;
import dev.latvian.mods.rhino.NativeObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class Components {
    private static String ConstructExceptionMessage(int minItems, int maxItems) {
        if (minItems == maxItems) {
            return "Must have exactly " + minItems + " elements!";
        }
        return "Must have between " + minItems + " and " + maxItems + " elements!";
    }
    public static int CastToInt(NativeObject object, Object key) {
        return (int)((double)object.get(key));
    }
    private static final String COUNT = "count";
    private static final String COMPONENT_TYPE = "electrodynamics_array";
    public static RecipeComponent<InputItem[]> ARRAY_ITEM_INPUT(int minItems, int maxItems) {
        return new RecipeComponentWithParent<>() {
            @Override
            public RecipeComponent<InputItem[]> parentComponent() {
                return ItemComponents.INPUT_ARRAY;
            }

            @Override
            public String componentType() {
                return COMPONENT_TYPE;
            }

            @Override
            public JsonElement write(RecipeJS recipe, InputItem[] value) {
                JsonObject json = new JsonObject();
                json.addProperty(COUNT, value.length);
                long i = 0;
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
                } else if (from instanceof Ingredient ingredient) {
                    return new InputItem[]{InputItem.of(ingredient)};
                } else if (from instanceof String string) {
                    return new InputItem[]{InputItem.of(string)};
                } else if (from instanceof NativeArray array) {
                    long longCount = array.getLength();
                    if (longCount > Integer.MAX_VALUE) {
                        throw new IllegalArgumentException("Array is too long! Max size: " + Integer.MAX_VALUE);
                    }
                    int count = (int) longCount;
                    if (count < minItems || count > maxItems) {
                        throw new IllegalStateException(ConstructExceptionMessage(minItems, maxItems));
                    }
                    InputItem[] result = new InputItem[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = InputItem.of(array.get(i));
                    }
                    return result;
                } else if (from instanceof NativeObject nativeObject) {
                    if (!nativeObject.containsKey(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = CastToInt(nativeObject, COUNT);
                    if (count < minItems || count > maxItems) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minItems, maxItems));
                    }
                    InputItem[] result = new InputItem[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = InputItem.of(nativeObject.get(i));
                    }
                    return result;
                } else if (from instanceof JsonObject object) {
                    if (!object.has(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = object.get(COUNT).getAsInt();
                    if (count < minItems || count > maxItems) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minItems, maxItems));
                    }
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
    }
    public static RecipeComponent<InputItem[]> ARRAY_ITEM_INPUT(int exact) {
        return ARRAY_ITEM_INPUT(exact, exact);
    }
    public static RecipeComponent<OutputItem[]> ARRAY_ITEM_OUTPUT(int minItems, int maxItems) {
        return new RecipeComponentWithParent<>() {
            @Override
            public RecipeComponent<OutputItem[]> parentComponent() {
                return ItemComponents.OUTPUT_ARRAY;
            }

            @Override
            public String componentType() {
                return COMPONENT_TYPE;
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
                } else if (from instanceof ItemStack stack) {
                    return new OutputItem[]{OutputItem.of(stack).withChance(1.0D)};
                } else if (from instanceof String string) {
                    return new OutputItem[]{OutputItem.of(string)};
                } else if (from instanceof NativeArray array) {
                    long longCount = array.getLength();
                    if (longCount > Integer.MAX_VALUE) {
                        throw new IllegalArgumentException("Array is too long! Max size: " + Integer.MAX_VALUE);
                    }
                    int count = (int)longCount;
                    if (count < minItems || count > maxItems) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minItems, maxItems));
                    }
                    OutputItem[] result = new OutputItem[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = OutputItem.of(array.get(i));
                    }
                    return result;
                } else if (from instanceof NativeObject nativeObject) {
                    if (!nativeObject.containsKey(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = CastToInt(nativeObject, COUNT);
                    if (count < minItems || count > maxItems) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minItems, maxItems));
                    }
                    OutputItem[] result = new OutputItem[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = OutputItem.of(nativeObject.get(i));
                    }
                    return result;
                } else if (from instanceof JsonObject object) {
                    if (!object.has(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = object.get(COUNT).getAsInt();
                    if (count < minItems || count > maxItems) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minItems, maxItems));
                    }
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
    public static RecipeComponent<OutputItem[]> ARRAY_ITEM_OUTPUT(int exact) {
        return ARRAY_ITEM_OUTPUT(exact, exact);
    }
    public static RecipeComponent<InputFluid[]> ARRAY_FLUID_INPUT(int minFluids, int maxFluids) {
        return new RecipeComponent<>() {
            @Override
            public Class<?> componentClass() {
                return InputFluid[].class;
            }

            @Override
            public ComponentRole role() {
                return ComponentRole.INPUT;
            }

            @Override
            public String componentType() {
                return COMPONENT_TYPE;
            }

            @Override
            public JsonElement write(RecipeJS recipe, InputFluid[] value) {
                JsonObject json = new JsonObject();
                json.addProperty(COUNT, value.length);
                int i = 0;
                for (InputFluid fluid : value) {
                    json.add(String.valueOf(i), recipe.writeInputFluid(fluid));
                    i++;
                }
                return json;
            }

            @Override
            public InputFluid[] read(RecipeJS recipe, Object from) {
                if (from instanceof InputFluid[] fluids) {
                    return fluids;
                } else if (from instanceof InputFluid fluid) {
                    return new InputFluid[]{fluid};
                } else if (from instanceof String string) {
                     return new InputFluid[]{recipe.readInputFluid(string)};
                } else if (from instanceof NativeArray array) {
                    long longCount = array.getLength();
                    if (longCount > Integer.MAX_VALUE) {
                        throw new IllegalArgumentException("Array is too long! Max size: " + Integer.MAX_VALUE);
                    }
                    int count = (int) longCount;
                    if (count < minFluids || count > maxFluids) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minFluids, maxFluids));
                    }
                    InputFluid[] result = new InputFluid[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = recipe.readInputFluid(array.get(i));
                    }
                    return result;
                } else if (from instanceof NativeObject nativeObject) {
                    if (!nativeObject.containsKey(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = CastToInt(nativeObject, COUNT);
                    if (count < minFluids || count > maxFluids) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minFluids, maxFluids));
                    }
                    InputFluid[] result = new InputFluid[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = recipe.readInputFluid(nativeObject.get(i));
                    }
                    return result;
                } else if (from instanceof JsonObject object) {
                    if (!object.has(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = object.get(COUNT).getAsInt();
                    if (count < minFluids || count > maxFluids) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minFluids, maxFluids));
                    }
                    InputFluid[] result = new InputFluid[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = recipe.readInputFluid(object.get(String.valueOf(i)));
                    }
                    return result;
                } else {
                    throw new IllegalArgumentException("Expected JSON object!");
                }
            }

            @Override
            public boolean isInput(RecipeJS recipe, InputFluid[] value, ReplacementMatch match) {
                if (match instanceof FluidLike other) {
                    for (InputFluid v : value) {
                        if (v.matches(other)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            @Override
            public String checkEmpty(RecipeKey<InputFluid[]> key, InputFluid[] value) {
                int i = 0;
                for (InputFluid fluid : value) {
                    if (fluid.kjs$isEmpty()) {
                        return "Fluid " + i + " can't be empty!";
                    }
                    i++;
                }
                return "";
            }
        };
    }
    public static RecipeComponent<InputFluid[]> ARRAY_FLUID_INPUT(int exact) {
        return ARRAY_FLUID_INPUT(exact, exact);
    }
    public static RecipeComponent<OutputGas> GAS_OUTPUT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return OutputGas.class;
        }

        @Override
        public ComponentRole role() {
            return ComponentRole.OUTPUT;
        }

        @Override
        public String componentType() {
            return COMPONENT_TYPE;
        }

        @Override
        public JsonElement write(RecipeJS recipe, OutputGas value) {
            if (value instanceof ElectroGasStackJS stack) {
                return stack.toJson();
            } else {
                throw new IllegalArgumentException("Invalid gas object!");
            }
        }

        @Override
        public OutputGas read(RecipeJS recipe, Object from) {
            return ElectroGasStackJS.of(from);
        }

        @Override
        public boolean isOutput(RecipeJS recipe, OutputGas value, ReplacementMatch match) {
            if (match instanceof GasLike other) {
                return value.matches(other);
            }
            return false;
        }
    };
    public static RecipeComponent<OutputGas[]> ARRAY_GAS_OUTPUT(int minGasses, int maxGasses) {
        return new RecipeComponent<>() {
            @Override
            public Class<?> componentClass() {
                return OutputGas[].class;
            }

            @Override
            public ComponentRole role() {
                return ComponentRole.OUTPUT;
            }

            @Override
            public String componentType() {
                return COMPONENT_TYPE;
            }

            @Override
            public JsonElement write(RecipeJS recipe, OutputGas[] value) {
                JsonObject json = new JsonObject();
                json.addProperty(COUNT, value.length);
                int i = 0;
                for (OutputGas gas : value) {
                    if (gas instanceof ElectroGasStackJS stack) {
                        json.add(String.valueOf(i), stack.toJson());
                    }
                    i++;
                }
                return json;
            }

            @Override
            public OutputGas[] read(RecipeJS recipe, Object from) {
                if (from instanceof OutputGas[] fluids) {
                    return fluids;
                } else if (from instanceof OutputGas fluid) {
                    return new OutputGas[]{fluid};
                } else if (from instanceof String string) {
                    return new OutputGas[]{ElectroGasStackJS.of(string)};
                } else if (from instanceof NativeArray array) {
                    long longCount = array.getLength();
                    if (longCount > Integer.MAX_VALUE) {
                        throw new IllegalArgumentException("Array is too long! Max size: " + Integer.MAX_VALUE);
                    }
                    int count = (int) longCount;
                    if (count < minGasses || count > maxGasses) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minGasses, maxGasses));
                    }
                    OutputGas[] result = new OutputGas[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = ElectroGasStackJS.of(array.get(i));
                    }
                    return result;
                } else if (from instanceof NativeObject nativeObject) {
                    if (nativeObject.containsKey("gas")) {
                        return new OutputGas[]{GAS_OUTPUT.read(recipe, from)};
                    }
                    if (!nativeObject.containsKey(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = CastToInt(nativeObject, COUNT);
                    if (count < minGasses || count > maxGasses) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minGasses, maxGasses));
                    }
                    OutputGas[] result = new OutputGas[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = ElectroGasStackJS.of(nativeObject.get(i));
                    }
                    return result;
                } else if (from instanceof JsonObject object) {
                    if (object.has("gas")) {
                        return new OutputGas[]{GAS_OUTPUT.read(recipe, from)};
                    }
                    if (!object.has(COUNT)) {
                        throw new IllegalArgumentException("Object does not have \"" + COUNT + "\" property!");
                    }
                    int count = object.get(COUNT).getAsInt();
                    if (count < minGasses || count > maxGasses) {
                        throw new IllegalArgumentException(ConstructExceptionMessage(minGasses, maxGasses));
                    }
                    OutputGas[] result = new OutputGas[count];
                    for (int i = 0; i < count; i++) {
                        result[i] = ElectroGasStackJS.of(object.get(String.valueOf(i)));
                    }
                    return result;
                } else {
                    throw new IllegalArgumentException("Expected JSON object!");
                }
            }

            @Override
            public boolean isOutput(RecipeJS recipe, OutputGas[] value, ReplacementMatch match) {
                if (match instanceof GasLike other) {
                    for (OutputGas v : value) {
                        if (v.matches(other)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }
    public static RecipeComponent<OutputGas[]> ARRAY_GAS_OUTPUT(int exact) {
        return ARRAY_GAS_OUTPUT(exact, exact);
    }
}
