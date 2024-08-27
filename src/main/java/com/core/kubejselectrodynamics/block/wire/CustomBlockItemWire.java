package com.core.kubejselectrodynamics.block.wire;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.blockitem.types.BlockItemWire;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class CustomBlockItemWire extends BlockItemWire {
    private final BlockItemWireBuilder builder;

    public CustomBlockItemWire(BlockItemWireBuilder builder, Supplier<CreativeModeTab> creativeTab) {
        super(builder.blockBuilder.getBlockWire(), builder.createItemProperties(), creativeTab);
        this.builder = builder;
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        SubtypeWire wire = builder.blockBuilder.getBlockWire().wire;
        tooltip.add(ElectroTextUtils.tooltip("itemwire.resistance", ChatFormatter.getChatDisplayShort(builder.getResistance(), DisplayUnit.RESISTANCE)).withStyle(ChatFormatting.GRAY));
        tooltip.add(ElectroTextUtils.tooltip("itemwire.maxamps", ChatFormatter.getChatDisplayShort(builder.getAmpacity(), DisplayUnit.AMPERE)).withStyle(ChatFormatting.GRAY));
        if (wire.insulation.shockVoltage == 0) {
            tooltip.add(ElectroTextUtils.tooltip("itemwire.info.uninsulated"));
        } else {
            tooltip.add(ElectroTextUtils.tooltip("itemwire.info.insulationrating", ChatFormatter.getChatDisplayShort(wire.insulation.shockVoltage, DisplayUnit.VOLTAGE)));
        }
    }
}
