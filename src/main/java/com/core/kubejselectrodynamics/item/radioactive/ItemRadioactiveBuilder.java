package com.core.kubejselectrodynamics.item.radioactive;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.item.ItemRadioactive;
import nuclearscience.common.tile.fissionreactor.TileMeltedReactor;

public class ItemRadioactiveBuilder extends ItemBuilder implements IRadioactiveItemBuilder<ItemRadioactiveBuilder> {
    private double radiation = 0.0D;
    private double radiationRadius = TileMeltedReactor.RADIATION_RADIUS;

    public ItemRadioactiveBuilder(ResourceLocation location) {
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
    public ItemRadioactiveBuilder getThis() {
        return this;
    }

    @Override
    public Item createObject() {
        ItemRadioactive item = new CustomItemRadioactive(this, createItemProperties());
        RadiationRegister.register(item, new FieldRadioactiveObject(radiation));
        return item;
    }
}
