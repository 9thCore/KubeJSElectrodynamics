package com.core.kubejselectrodynamics.block.radioactive;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.item.radioactive.BlockItemRadioactiveBuilder;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;

public class BlockRadioactiveBuilder extends BlockBuilder implements IRadioactiveBlockBuilder<BlockRadioactiveBuilder> {
    private double radiation = 0.0D;
    private double radiationRadius = 0.0D;

    public BlockRadioactiveBuilder(ResourceLocation location) {
        super(location);
    }

    @Override
    public void setRadiation(double radiation) {
        this.radiation = radiation;
    }

    @Override
    public void setRadiationRadius(double radiationRadius) {
        this.radiationRadius = radiationRadius;
    }

    @Override
    public double getRadiation() {
        return radiation;
    }

    @Override
    public double getRadiationRadius() {
        return radiationRadius;
    }

    @Override
    public BlockRadioactiveBuilder getThis() {
        return this;
    }

    @Override
    protected BlockItemBuilder getOrCreateItemBuilder() {
        if (itemBuilder == null) {
            itemBuilder = new BlockItemRadioactiveBuilder(id, this);
        }
        return itemBuilder;
    }

    @Override
    public Block createObject() {
        Block block = new CustomBlockRadioactive(TileRegister.RADIOACTIVE_TYPE.getSupplier(), this);
        RadiationRegister.register(block, new FieldRadioactiveObject(radiation));
        TileRegister.RADIOACTIVE_TYPE.valid(block);
        return block;
    }
}
