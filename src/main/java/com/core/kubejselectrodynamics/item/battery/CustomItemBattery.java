package com.core.kubejselectrodynamics.item.battery;

import com.core.kubejselectrodynamics.item.ICustomItemExtension;
import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.prefab.item.ItemElectric;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CustomItemBattery extends ItemElectric implements ICustomItemExtension {
    public CustomItemBattery(ElectricItemProperties properties, Supplier<CreativeModeTab> creativeTab) {
        super(properties, creativeTab, item -> Items.AIR);
    }

    @Override
    public Collection<ItemStack> getEntries() {
        ItemStack fullStack = new ItemStack(this);
        IItemElectric electric = (IItemElectric) fullStack.getItem();
        IItemElectric.setEnergyStored(fullStack, electric.getMaximumCapacity(fullStack));
        return List.of(fullStack);
    }
}
