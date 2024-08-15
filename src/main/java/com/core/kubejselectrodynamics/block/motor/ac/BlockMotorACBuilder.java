package com.core.kubejselectrodynamics.block.motor.ac;

import com.core.kubejselectrodynamics.block.BlockItemDescribableBuilder;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsElectricityInput;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsInventoryInput;
import com.core.kubejselectrodynamics.block.capabilities.IForgeEnergyOutput;
import com.core.kubejselectrodynamics.block.motor.MotorBlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.EnumSet;

public class BlockMotorACBuilder extends MotorBlockBuilder<BlockMotorACBuilder> implements IElectrodynamicsElectricityInput<BlockMotorACBuilder>, IElectrodynamicsInventoryInput<BlockMotorACBuilder>, IForgeEnergyOutput<BlockMotorACBuilder> {
    private int voltage = 120;
    private int joulesConsumption = 1000;
    private final EnumSet<Direction> inventoryInput = EnumSet.allOf(Direction.class);
    private final EnumSet<Direction> electricInput = EnumSet.of(Direction.NORTH);
    private final EnumSet<Direction> feOutput = EnumSet.of(Direction.SOUTH);
    public BlockMotorACBuilder(ResourceLocation location) {
        super(location);
    }

    @Info("Sets the motor's voltage, as an integer.")
    public BlockMotorACBuilder voltage(int voltage) {
        this.voltage = voltage;
        return this;
    }

    @Info("Sets the motor's Joule consumption rate, as an integer.")
    public BlockMotorACBuilder joulesConsumption(int feConsumption) {
        this.joulesConsumption = feConsumption;
        return this;
    }

    @Override
    public EnumSet<Direction> getElectricInputSet() {
        return electricInput;
    }

    @Override
    public EnumSet<Direction> getInventoryInputSet() {
        return inventoryInput;
    }

    @Override
    public EnumSet<Direction> getFEOutputSet() {
        return feOutput;
    }

    @HideFromJS
    public int getVoltage() {
        return voltage;
    }

    @HideFromJS
    public int getJoulesConsumption() {
        return joulesConsumption;
    }

    @Override
    protected BlockItemBuilder getOrCreateItemBuilder() {
        if (itemBuilder == null) {
            itemBuilder = new BlockItemDescribableBuilder(id);
        }
        return itemBuilder;
    }

    @Override
    public Block createObject() {
        if (blockEntityInfo != null) {
            ConsoleJS.SERVER.error("#blockEntity() unsupported operation, cannot change block entity from battery block entity");
            throw new UnsupportedOperationException("#blockEntity() not supported");
        }

        Block block = new CustomBlockMotorAC(TileRegister.MOTOR_AC_TYPE.getSupplier(usesCustomRenderer()), this);
        TileRegister.MOTOR_AC_TYPE.valid(block);
        registerShape(block);
        BlockItemDescriptable.addDescription(
                () -> block,
                Component.translatable(
                        "tooltip.kubejselectrodynamics.dynamicelectricity.motorac",
                        ChatFormatter.getChatDisplayShort(joulesConsumption, DisplayUnit.JOULES),
                        ChatFormatter.getChatDisplayShort(voltage, DisplayUnit.VOLTAGE),
                        ChatFormatter.getChatDisplayShort(joulesConsumption * conversionEfficiency, DisplayUnit.FORGE_ENERGY_UNIT)
                )
        );
        return block;
    }
}
