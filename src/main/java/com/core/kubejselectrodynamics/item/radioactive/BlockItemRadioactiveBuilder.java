package com.core.kubejselectrodynamics.item.radioactive;

import com.core.kubejselectrodynamics.block.radioactive.IRadioactiveBlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;

public class BlockItemRadioactiveBuilder extends BlockItemBuilder {
    private final IRadioactiveBlockBuilder<?> builder;

    public BlockItemRadioactiveBuilder(ResourceLocation location, IRadioactiveBlockBuilder<?> builder) {
        super(location);
        this.builder = builder;
    }

    public Item createObject() {
        CustomBlockItemRadioactive item = new CustomBlockItemRadioactive(builder, createItemProperties());
        RadiationRegister.register(item, new FieldRadioactiveObject(builder.getRadiation()));
        return item;
    }
}
