package com.core.kubejselectrodynamics.item.fuelrod;

import com.core.kubejselectrodynamics.item.radioactive.CustomItemRadioactive;
import com.core.kubejselectrodynamics.item.radioactive.IRadioactiveItemBuilder;
import com.core.kubejselectrodynamics.util.RadiationUtil;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import nuclearscience.api.radiation.FieldRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.item.ItemRadioactive;

public class ItemFuelRodBuilder extends ItemBuilder implements IRadioactiveItemBuilder<ItemFuelRodBuilder> {
    private double radiation = 0.0D;
    private double radiationRadius = 0.0D;
    private int fuelValue = 0;

    public ItemFuelRodBuilder(ResourceLocation location) {
        super(location);
    }

    @Info("""
        Sets the item's fuel value, as an integer.
        In a Fission Reactor, fuel value is summed up and the total is used for heating up and explosion radius.
        The higher fuel value, the faster heating up and the higher the explosion radius.
        Base Nuclear Science fuel rods use 2 or 3.
        Cannot go below 0.
        """)
    public ItemFuelRodBuilder fuelValue(int fuelValue) {
        this.fuelValue = Math.max(0, fuelValue);
        return this;
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
    public ItemFuelRodBuilder getThis() {
        return this;
    }

    @Override
    public Item createObject() {
        ItemRadioactive item = new CustomItemRadioactive(this, createItemProperties());
        RadiationRegister.register(item, new FieldRadioactiveObject(getRadiation()));
        RadiationUtil.fuelRods.put(item, fuelValue);
        return item;
    }
}
