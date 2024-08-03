package com.core.kubejselectrodynamics.util;

import com.core.kubejselectrodynamics.plugin.recipe.schema.fluid.TagFluidStackJS;
import com.google.gson.JsonObject;
import dev.architectury.fluid.FluidStack;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.EmptyFluidStackJS;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.rhino.NativeObject;
import dev.latvian.mods.rhino.mod.util.NBTUtils;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class ElectroFluidWrapper {
    /**
     * Returns TagFluidStackJS if matching a tag, otherwise FluidStackJS
     * @see TagFluidStackJS
     * @see FluidStackJS
     *
     * @param from
     * @return JS Fluid Stack
     */
    public static FluidStackJS of(@Nullable Object from) {
        if (from == null) {
            return EmptyFluidStackJS.INSTANCE;
        } else if (from instanceof FluidStackJS stack) {
            return stack;
        } else if (from instanceof String string) {
            if (string.startsWith("#")) {
                return new TagFluidStackJS(new ResourceLocation(string.substring(1)));
            } else {
                return FluidStackJS.of(string);
            }
        } else if (from instanceof FluidStack stack) {
            return FluidStackJS.of(stack);
        } else if (from instanceof FluidIngredient ingredient) {
            return new TagFluidStackJS(Objects.requireNonNull(ingredient.tag).location());
        } else if (from instanceof NativeObject object) {
            FluidStackJS stack = null;
            if (object.containsKey("tag")) {
                stack = new TagFluidStackJS(new ResourceLocation((String)object.get("tag")));
            }
            if (object.containsKey("fluid")) {
                stack = FluidStackJS.of(object.get("fluid"));
            }
            if (stack == null) {
                throw new IllegalArgumentException("Invalid fluid object!");
            }
            if (object.containsKey("amount")) {
                stack.setAmount((long)(double)object.get("amount"));
            } else if (object.containsKey("count")) {
                stack.setAmount((long)(double)object.get("count"));
            }
            if (object.containsKey("nbt")) {
                if (object.get("nbt") instanceof NativeObject nbt) {
                    stack.setNbt((CompoundTag)NBTUtils.compoundTag(nbt));
                }
                throw new IllegalArgumentException("Invalid fluid object!");
            }
            return stack;
        } else if (from instanceof JsonObject object) {
            if (object.has("tag")) {
                TagFluidStackJS stack = new TagFluidStackJS(new ResourceLocation(object.get("tag").getAsString()));
                if (object.has("amount")) {
                    stack.setAmount(object.get("amount").getAsLong());
                } else if (object.has("count")) {
                    stack.setAmount(object.get("count").getAsLong());
                }
                if (object.has("nbt")) {
                    stack.setNbt(NBTUtils.toTagCompound(object.get("nbt")));
                }
                return stack;
            }
        }
        return FluidStackJS.of(from);
    }

    public static FluidStackJS of(@Nullable Object o, long amount) {
        FluidStackJS stack = of(o);
        stack.setAmount(amount);
        return stack;
    }

    public static FluidStackJS of(@Nullable Object o, long amount, @Nullable CompoundTag nbt) {
        FluidStackJS stack = of(o, amount);
        stack.setNbt(nbt);
        return stack;
    }
}
