package com.core.kubejselectrodynamics.block.gaspipe;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.blockitem.BlockItemElectrodynamics;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class CustomBlockItemGasPipe extends BlockItemElectrodynamics {
    private final BlockItemGasPipeBuilder builder;

    public CustomBlockItemGasPipe(BlockItemGasPipeBuilder builder, Properties properties, Supplier<CreativeModeTab> creativeTab) {
        super(builder.blockBuilder.get(), properties, creativeTab);
        this.builder = builder;
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltips, TooltipFlag advanced) {
        super.appendHoverText(stack, level, tooltips, advanced);
        tooltips.add(ElectroTextUtils.tooltip("pipematerial", builder.getTranslatedName()).withStyle(ChatFormatting.GRAY));
        // tooltips.add(TextUtils.tooltip("pipeinsulationmaterial", pipe.pipe.insulationMaterial.getTranslatedName()).withStyle(ChatFormatting.GRAY));
        tooltips.add(ElectroTextUtils.tooltip("pipemaximumpressure", ChatFormatter.getChatDisplayShort(builder.getMaxPressure(), DisplayUnit.PRESSURE_ATM)).withStyle(ChatFormatting.GRAY));
        // tooltips.add(TextUtils.tooltip("pipeheatloss", ChatFormatter.getChatDisplayShort(pipe.pipe.effectivePipeHeatLoss, DisplayUnit.TEMPERATURE_KELVIN)).withStyle(ChatFormatting.GRAY));
    }
}
