package com.core.kubejselectrodynamics.plugin.recipe.schema.fluid;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.plugin.recipe.schema.Components;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.fluid.FluidLike;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.recipe.InputReplacement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.util.WrappedJS;
import dev.latvian.mods.rhino.NativeObject;
import dev.latvian.mods.rhino.mod.util.NBTUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class ElectroFluidStackJS implements WrappedJS, InputFluid, OutputFluid {
    public static final ElectroFluidStackJS EMPTY = new ElectroFluidStackJS(new ResourceLocation("minecraft:empty"), 0);
    public static final Fluid[] EMPTY_FLUID = new Fluid[0];
    public static final long DEFAULT_AMOUNT = 1000;

    private final TagKey<Fluid> tag;
    private final ResourceLocation location;
    private long amount = 0;
    private double chance = 1.0D;
    private CompoundTag nbt = null;

    public ElectroFluidStackJS(ResourceLocation location, long amount) {
        this.location = location;
        this.tag = null;
        this.amount = amount;
    }

    public ElectroFluidStackJS(TagKey<Fluid> tag, long amount) {
        this.tag = tag;
        this.location = null;
        this.amount = amount;
    }

    public static ElectroFluidStackJS of(@Nullable Object from) {
        if (from == null) {
            return EMPTY;
        } else if (from instanceof ElectroFluidStackJS stack) {
            return stack;
        } else if (from instanceof String string) {
            if (string.startsWith("#")) {
                return new ElectroFluidStackJS(new TagKey<>(Registries.FLUID, new ResourceLocation(string.substring(1))), DEFAULT_AMOUNT);
            }
            return new ElectroFluidStackJS(new ResourceLocation(string), DEFAULT_AMOUNT);
        } else if (from instanceof NativeObject object) {
            long amount = DEFAULT_AMOUNT;
            ElectroFluidStackJS stack;
            if (object.containsKey("amount")) {
                amount = Components.CastToInt(object, "amount");
            } else if (object.containsKey("count")) {
                amount = Components.CastToInt(object, "count");
            }
            if (object.containsKey("tag")) {
                String tag = (String)object.get("tag");
                stack = new ElectroFluidStackJS(new TagKey<>(Registries.FLUID, new ResourceLocation(tag)), amount);
            } else if (object.containsKey("fluid")) {
                String fluid = (String)object.get("fluid");
                stack = new ElectroFluidStackJS(new ResourceLocation(fluid), amount);
            } else {
                throw new IllegalArgumentException("Not a valid fluid object!");
            }
            if (object.containsKey("nbt")) {
                stack.nbt = NBTUtils.toTagCompound(object.get("nbt"));
            }
            return stack;
        } else if (from instanceof JsonObject object) {
            long amount = DEFAULT_AMOUNT;
            ElectroFluidStackJS stack;
            if (object.has("amount")) {
                amount = object.get("amount").getAsLong();
            } else if (object.has("count")) {
                amount = object.get("count").getAsLong();
            }
            if (object.has("tag")) {
                String tag = object.get("tag").getAsString();
                stack = new ElectroFluidStackJS(new TagKey<>(Registries.FLUID, new ResourceLocation(tag)), amount);
            } else if (object.has("fluid")) {
                String fluid = object.get("fluid").getAsString();
                stack = new ElectroFluidStackJS(new ResourceLocation(fluid), amount);
            } else {
                throw new IllegalArgumentException("Not a valid fluid object!");
            }
            if (object.has("nbt")) {
                stack.nbt = NBTUtils.toTagCompound(object.get("nbt"));
            }
            return stack;
        } else {
            throw new IllegalArgumentException("Expected JsonObject, NativeObject, String, ElectroFluidStackJS or null!");
        }
    }

    public double getChance() {
        return chance;
    }

    public ElectroFluidStackJS withChance(double chance) {
        ElectroFluidStackJS stack = copy();
        stack.chance = chance;
        return stack;
    }

    public CompoundTag getNBT() {
        return nbt;
    }

    public  ElectroFluidStackJS withNBT(@Nullable CompoundTag nbt) {
        ElectroFluidStackJS stack = copy();
        stack.nbt = nbt;
        return stack;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public ElectroFluidStackJS copy() {
        ElectroFluidStackJS stack;
        if (isTag()) {
            stack = new ElectroFluidStackJS(this.tag, this.amount);
        } else {
            stack = new ElectroFluidStackJS(this.location, this.amount);
        }
        stack.chance = this.chance;
        stack.nbt = this.nbt;
        return stack;
    }

    public boolean matches(FluidLike fluid) {
        if (fluid instanceof FluidStackJS stack) {
            if (tag != null) {
                return stack.getTags().contains(tag.location());
            }
            if (location != null) {
                return Arrays.stream(getFluids()).anyMatch(f -> f == stack.getFluid());
            }
        } else if (fluid instanceof ElectroFluidStackJS stack) {
            if (tag != null) {
                return Objects.equals(tag, stack.tag);
            }
            if (location != null) {
                return Objects.equals(location, stack.location);
            }
        }
        return false;
    }

    @Override
    public Object replaceInput(RecipeJS recipe, ReplacementMatch match, InputReplacement original) {
        KubeJSElectrodynamics.LogInfo("Replacing for recipe " + recipe.getId());
        KubeJSElectrodynamics.LogInfo(match);
        KubeJSElectrodynamics.LogInfo(original);
        return InputFluid.super.replaceInput(recipe, match, original);
    }

    public Fluid[] getFluids() {
        if (location != null) {
            return new Fluid[] {ForgeRegistries.FLUIDS.getValue(location)};
        }
        if (tag != null) {
            return Objects.requireNonNull(ForgeRegistries.FLUIDS.tags()).getTag(tag).stream().toArray(Fluid[]::new);
        }
        return EMPTY_FLUID;
    }

    public boolean isTag() {
        return tag != null;
    }

    @Override
    public long kjs$getAmount() {
        return getAmount();
    }

    @Override
    public boolean kjs$isEmpty() {
        return amount <= 0L || this == EMPTY;
    }

    @Override
    public FluidLike kjs$copy(long amount) {
        ElectroFluidStackJS stack = copy();
        stack.amount = amount;
        return stack;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("amount", amount);
        object.addProperty("chance", chance);
        if (tag != null) {
            object.addProperty("tag", tag.location().toString());
        } else if (location != null) {
            object.addProperty("fluid", location.toString());
        }
        return object;
    }
}
