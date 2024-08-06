package com.core.kubejselectrodynamics.plugin.item.portable_cylinder;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.item.CustomItemExtension;
import dev.latvian.mods.kubejs.item.creativetab.KubeJSCreativeTabs;
import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasHandlerItemStack;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.item.gear.tools.ItemPortableCylinder;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CustomItemPortableCylinder extends ItemPortableCylinder implements CustomItemExtension {
    public CustomItemPortableCylinder(Item.Properties properties, Supplier<CreativeModeTab> creativeTab) {
        super(properties, creativeTab);
    }

    /**
     * Method originally from Electrodynamics' source code (addCreativeModeItems), adapted to fit custom implementation
     */
    @Override
    public Collection<ItemStack> getEntries() {
        Collection<ItemStack> entries = new ArrayList<>();
        entries.add(getDefaultInstance());
        if (ElectrodynamicsCapabilities.GAS_HANDLER_ITEM != null) {
            for (Gas gas : ElectrodynamicsRegistries.gasRegistry().getValues()) {
                if (gas.isEmpty()) {
                    continue;
                }
                ItemStack temp = new ItemStack(this);
                temp.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> ((GasHandlerItemStack) cap).setGas(new GasStack(gas, getGasCapacity(), Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL)));
                entries.add(temp);
            }
        }
        return entries;
    }

    /**
     * Method originally from Electrodynamics' source code, adapted to fit custom implementation
     */
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new GasHandlerItemStack(stack, getGasCapacity(), getTemperatureCapacity(), getPressureCapacity());
    }

    /**
     * Method originally from Electrodynamics' source code, adapted to fit custom implementation
     */
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltips, TooltipFlag isAdvanced) {
        stack.getCapability(ElectrodynamicsCapabilities.GAS_HANDLER_ITEM).ifPresent(cap -> {
            GasStack gas = cap.getGasInTank(0);
            if (gas.isEmpty()) {
                tooltips.add(ElectroTextUtils.ratio(Component.literal("0"), ChatFormatter.formatFluidMilibuckets(getGasCapacity())).withStyle(ChatFormatting.GRAY));
            } else {
                tooltips.add(gas.getGas().getDescription().copy().withStyle(ChatFormatting.GRAY));
                tooltips.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(gas.getAmount()), ChatFormatter.formatFluidMilibuckets(getGasCapacity())).withStyle(ChatFormatting.DARK_GRAY));
                tooltips.add(ChatFormatter.getChatDisplayShort(gas.getTemperature(), DisplayUnit.TEMPERATURE_KELVIN).withStyle(ChatFormatting.DARK_GRAY));
                tooltips.add(ChatFormatter.getChatDisplayShort(gas.getPressure(), DisplayUnit.PRESSURE_ATM).withStyle(ChatFormatting.DARK_GRAY));
            }

        });
        if (Screen.hasShiftDown()) {
            tooltips.add(ElectroTextUtils.tooltip("maxpressure", ChatFormatter.getChatDisplayShort(getPressureCapacity(), DisplayUnit.PRESSURE_ATM)).withStyle(ChatFormatting.GRAY));
            tooltips.add(ElectroTextUtils.tooltip("maxtemperature", ChatFormatter.getChatDisplayShort(getTemperatureCapacity(), DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.GRAY));
        }
    }

    public double getGasCapacity() {
        return ItemPortableCylinder.MAX_GAS_CAPCITY;
    }

    public int getPressureCapacity() {
        return ItemPortableCylinder.MAX_PRESSURE;
    }

    public double getTemperatureCapacity() {
        return ItemPortableCylinder.MAX_TEMPERATURE;
    }
}
