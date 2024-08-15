package com.core.kubejselectrodynamics.block.motor.dc;

import com.core.kubejselectrodynamics.block.BlockItemDescribableBuilder;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsElectricityOutput;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsFluidInput;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsInventoryInput;
import com.core.kubejselectrodynamics.block.capabilities.IForgeEnergyInput;
import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableEntityBlock;
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

public class BlockMotorDCBuilder extends MotorBlockBuilder<BlockMotorDCBuilder> implements IElectrodynamicsElectricityOutput<BlockMotorDCBuilder>, IElectrodynamicsInventoryInput<BlockMotorDCBuilder>, IForgeEnergyInput<BlockMotorDCBuilder> {
    private int voltage = 120;
    private int feConsumption = 1000;
    private final EnumSet<Direction> inventoryInput = EnumSet.of(Direction.UP, Direction.WEST, Direction.EAST);
    private final EnumSet<Direction> electricOutput = EnumSet.of(Direction.SOUTH);
    private final EnumSet<Direction> feInput = EnumSet.of(Direction.NORTH);
    public BlockMotorDCBuilder(ResourceLocation location) {
        super(location);
        conversionEfficiency = 0.95D;
    }

    @Info("Sets the motor's voltage, as an integer.")
    public BlockMotorDCBuilder voltage(int voltage) {
        this.voltage = voltage;
        return this;
    }

    @Info("Sets the motor's FE consumption rate, as an integer.")
    public BlockMotorDCBuilder feConsumption(int feConsumption) {
        this.feConsumption = feConsumption;
        return this;
    }

    @Override
    public EnumSet<Direction> getElectricOutputSet() {
        return electricOutput;
    }

    @Override
    public EnumSet<Direction> getInventoryInputSet() {
        return inventoryInput;
    }

    @Override
    public EnumSet<Direction> getFEInputSet() {
        return feInput;
    }

    @HideFromJS
    public int getVoltage() {
        return voltage;
    }

    @HideFromJS
    public int getFeConsumption() {
        return feConsumption;
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

        Block block = new CustomBlockMotorDC(TileRegister.MOTOR_DC_TYPE.getSupplier(usesCustomRenderer()), this);
        TileRegister.MOTOR_DC_TYPE.valid(block);
        registerShape(block);
        BlockItemDescriptable.addDescription(
                () -> block,
                Component.translatable(
                        "tooltip.kubejselectrodynamics.dynamicelectricity.motordc",
                        ChatFormatter.getChatDisplayShort(feConsumption, DisplayUnit.FORGE_ENERGY_UNIT),
                        ChatFormatter.getChatDisplayShort(feConsumption * conversionEfficiency, DisplayUnit.JOULES),
                        ChatFormatter.getChatDisplayShort(voltage, DisplayUnit.VOLTAGE)
                )
        );
        return block;
    }
}
