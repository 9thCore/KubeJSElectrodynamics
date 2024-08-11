package com.core.kubejselectrodynamics.block;

import com.core.kubejselectrodynamics.item.CustomItemTab;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class BlockItemDescribableBuilder extends BlockItemBuilder {
    public BlockItemDescribableBuilder(ResourceLocation location) {
        super(location);
    }

    @Override
    public Item createObject() {
        return new BlockItemDescriptable(blockBuilder, createItemProperties(), CustomItemTab.TAB);
    }
}
