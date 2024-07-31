package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.util.TinyMap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class Keys {
    public static final RecipeKey<Either<InputItem[], TinyMap<String, Either<Long, Either<InputItem, TagKey<Item>>>>>> ITEM_INPUTS = Components.ARRAY_ITEM_INPUT.or(new MapRecipeComponent<>(
            Components.ARRAY_KEY,
            Components.ARRAY_ITEM_ELEMENT_INPUT,
            false
    )).key("iteminputs").preferred("itemInputs");
    public static final RecipeKey<Either<OutputItem[], TinyMap<String, Either<Long, OutputItem>>>> ITEM_BI = Components.ARRAY_ITEM_OUTPUT.or(new MapRecipeComponent<>(
            Components.ARRAY_KEY,
            Components.ARRAY_ITEM_ELEMENT_OUTPUT,
            false
    )).key("itembi").preferred("itemByproducts").defaultOptional();
    public static final RecipeKey<Either<InputFluid[], TinyMap<String, Either<Long, Either<InputFluid, TagKey<Fluid>>>>>> FLUID_INPUTS = Components.ARRAY_FLUID_INPUT.or(new MapRecipeComponent<>(
            Components.ARRAY_KEY,
            Components.ARRAY_FLUID_ELEMENT_INPUT,
            false
    )).key("fluidinputs").preferred("fluidInputs");
    /*
    // Not used by anything as of yet
    public static final RecipeKey<Either<OutputFluid[], TinyMap<String, Either<Long, OutputFluid>>>> FLUID_BI = Components.ARRAY_FLUID_OUTPUT.or(new MapRecipeComponent<>(
            Components.ARRAY_KEY,
            Components.ARRAY_FLUID_ELEMENT_OUTPUT,
            false
    )).key("fluidbi").preferred("fluidByproducts").defaultOptional();
     */
    public static final RecipeKey<OutputItem> ITEM_OUTPUT = ItemComponents.OUTPUT.key("output");
    public static final RecipeKey<Double> EXPERIENCE = NumberComponent.ANY_DOUBLE.key("experience").preferred("xp").optional(0.0D);
    public static final RecipeKey<Long> TICKS = TimeComponent.TICKS.key("ticks");
    public static final RecipeKey<Double> USAGE_PER_TICK = NumberComponent.DOUBLE.key("usagepertick").preferred("usagePerTick");
}
