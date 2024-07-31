package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.component.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class Components {
    public static final OrRecipeComponent<InputItem, TagKey<Item>> ITEM_OR_TAG = ItemComponents.INPUT.or(TagKeyComponent.ITEM);
    public static final OrRecipeComponent<Long, Either<InputItem, TagKey<Item>>> ARRAY_ITEM_ELEMENT_INPUT = NumberComponent.LONG.or(ITEM_OR_TAG);
    public static final OrRecipeComponent<InputFluid, TagKey<Fluid>> FLUID_OR_TAG = FluidComponents.INPUT.or(TagKeyComponent.FLUID);
    public static final OrRecipeComponent<Long, Either<InputFluid, TagKey<Fluid>>> ARRAY_FLUID_ELEMENT_INPUT = NumberComponent.LONG.or(FLUID_OR_TAG);
    public static final OrRecipeComponent<Long, OutputItem> ARRAY_ITEM_ELEMENT_OUTPUT = NumberComponent.LONG.or(ItemComponents.OUTPUT);
    public static final OrRecipeComponent<Long, OutputFluid> ARRAY_FLUID_ELEMENT_OUTPUT = NumberComponent.LONG.or(FluidComponents.OUTPUT);
    public static final StringComponent ARRAY_KEY = new StringComponent("must be \"count\" or a number in a string", s -> s.matches("^(count|[0-9]+)$"));
}
