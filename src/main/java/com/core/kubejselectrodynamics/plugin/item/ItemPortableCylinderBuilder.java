package com.core.kubejselectrodynamics.plugin.item;

import com.core.kubejselectrodynamics.item.CustomItemRegistrationUtil;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import electrodynamics.common.item.gear.tools.ItemPortableCylinder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemPortableCylinderBuilder extends ItemBuilder {
    private double maxCapacity = ItemPortableCylinder.MAX_GAS_CAPCITY;
    private int maxPressure = ItemPortableCylinder.MAX_PRESSURE;
    private double maxTemperature = ItemPortableCylinder.MAX_TEMPERATURE;

    public ItemPortableCylinderBuilder(ResourceLocation location) {
        super(location);
        maxStackSize = 1;
    }

    @Info("Sets the maximum capacity of this portable cylinder, as a double.")
    public ItemPortableCylinderBuilder capacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }

    @Info("Sets the maximum pressure of this portable cylinder, as an integer.")
    public ItemPortableCylinderBuilder pressure(int maxPressure) {
        this.maxPressure = maxPressure;
        return this;
    }

    @Info("Sets the maximum temperature of this portable cylinder, as a double.")
    public ItemPortableCylinderBuilder temperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    @Override
    public Item createObject() {
        CustomItemPortableCylinder item = new CustomItemPortableCylinder(
                createItemProperties(),
                CustomItemRegistrationUtil.getKubeJSTab()
        ) {
            @Override
            public double getGasCapacity() {
                return maxCapacity;
            }

            @Override
            public double getTemperatureCapacity() {
                return maxTemperature;
            }

            @Override
            public int getPressureCapacity() {
                return maxPressure;
            }
        };
        CustomItemRegistrationUtil.items.add(item);
        return item;
    }
}
