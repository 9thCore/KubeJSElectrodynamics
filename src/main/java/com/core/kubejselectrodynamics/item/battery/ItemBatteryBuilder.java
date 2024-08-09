package com.core.kubejselectrodynamics.item.battery;

import com.core.kubejselectrodynamics.item.CustomItemTab;
import dev.latvian.mods.kubejs.bindings.ItemWrapper;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemBatteryBuilder extends ItemBuilder {
    private ResourceLocation container = null; // Private access in ItemBuilder
    private double capacity = 1000.0D;
    private double outputVoltage = 120.0D;
    private double outputJoules = 100.0D;
    private double inputVoltage = 120.0D;
    private double inputJoules = 100.0D;

    public ItemBatteryBuilder(ResourceLocation location) {
        super(location);
        maxStackSize = 1;
    }

    @Override
    @Info("Sets the item's container item, e.g. a bucket for a milk bucket.")
    public ItemBuilder containerItem(ResourceLocation id) {
        container = id;
        return this;
    }

    @Info("Sets the battery's input and output voltage, as a double.")
    public ItemBatteryBuilder voltage(double voltage) {
        this.outputVoltage = voltage;
        this.inputVoltage = voltage;
        return this;
    }

    @Info("Sets the battery's input and output joules, as a double.")
    public ItemBatteryBuilder joules(double joules) {
        this.outputJoules = joules;
        this.inputJoules = joules;
        return this;
    }

    @Info("Sets the battery's maximum capacity, as a double.")
    public ItemBatteryBuilder capacity(double capacity) {
        this.capacity = capacity;
        return this;
    }

    @Info("Sets the battery's output joules and voltage, as doubles.")
    public ItemBatteryBuilder output(double joules, double voltage) {
        this.outputJoules = joules;
        this.outputVoltage = voltage;
        return this;
    }

    @Info("Sets the battery's output joules, as a double.")
    public ItemBatteryBuilder output(double joules) {
        this.outputJoules = joules;
        return this;
    }

    @Info("Sets the battery's input joules and voltage, as doubles.")
    public ItemBatteryBuilder input(double joules, double voltage) {
        this.inputJoules = joules;
        this.inputVoltage = voltage;
        return this;
    }

    @Info("Sets the battery's input joules, as a double.")
    public ItemBatteryBuilder input(double joules) {
        this.inputJoules = joules;
        return this;
    }

    /**
     * Method originally from KubeJS' source code, adapted to fit custom implementation
     */
    @Override
    public Item.Properties createItemProperties() {
        ElectricItemProperties properties = new ElectricItemProperties()
                .capacity(capacity)
                .receive(TransferPack.joulesVoltage(inputJoules, inputVoltage))
                .extract(TransferPack.joulesVoltage(outputJoules, outputVoltage))
                .setIsEnergyStorageOnly();

        if (maxDamage > 0) {
            properties.durability(maxDamage);
        } else {
            properties.stacksTo(maxStackSize);
        }

        properties.rarity(rarity);

        if (container != null) {
            properties.craftRemainder(ItemWrapper.getItem(container));
        }

        if (foodBuilder != null) {
            properties.food(foodBuilder.build());
        }

        if (fireResistant) {
            properties.fireResistant();
        }

        return properties;
    }

    @Override
    public Item createObject() {
        CustomItemBattery item = new CustomItemBattery(
                (ElectricItemProperties) createItemProperties(),
                CustomItemTab.TAB
        );
        CustomItemTab.items.add(item);
        return item;
    }
}
