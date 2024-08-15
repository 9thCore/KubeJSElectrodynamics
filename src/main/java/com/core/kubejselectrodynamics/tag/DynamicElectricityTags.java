package com.core.kubejselectrodynamics.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DynamicElectricityTags {
    public static final TagKey<Item> BRUSH_TAG = TagKey.create(Registries.ITEM, new ResourceLocation("kubejselectrodynamics:conductorbrush"));
}
