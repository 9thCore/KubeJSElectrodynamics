package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.BlockItemDescribableBuilder;
import com.core.kubejselectrodynamics.block.RotatableBlockBuilder;
import com.core.kubejselectrodynamics.block.capabilities.ElectrodynamicsElectricityInput;
import com.core.kubejselectrodynamics.block.capabilities.ElectrodynamicsElectricityOutput;
import com.core.kubejselectrodynamics.block.TileRegister;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.EnumSet;

public class BlockBatteryBoxBuilder extends RotatableBlockBuilder<BlockBatteryBoxBuilder> implements ElectrodynamicsElectricityInput<BlockBatteryBoxBuilder>, ElectrodynamicsElectricityOutput<BlockBatteryBoxBuilder> {
    private int voltage = 120;
    private double capacity = 1000.0D;
    private double output = 300.0D;
    private final EnumSet<Direction> electricInput = EnumSet.of(Direction.SOUTH);
    private final EnumSet<Direction> electricOutput = EnumSet.of(Direction.NORTH);

    public BlockBatteryBoxBuilder(ResourceLocation location) {
        super(location);
    }

    @Info("Sets the battery's capacity, as a double.")
    public BlockBatteryBoxBuilder capacity(double capacity) {
        this.capacity = capacity;
        return this;
    }

    @Info("Sets the battery's voltage, as an integer.")
    public BlockBatteryBoxBuilder voltage(int voltage) {
        this.voltage = voltage;
        return this;
    }

    @Info("Sets the battery's output power, as a double.")
    public BlockBatteryBoxBuilder output(double output) {
        this.output = output;
        return this;
    }

    @Info("Equivalent to calling rotationOffset(270), as it is the rotation offset of base Electrodynamics' battery model rotation.")
    public BlockBatteryBoxBuilder batteryModel() {
        return rotationOffset(270);
    }

    @Override
    public EnumSet<Direction> getElectricInputSet() {
        return electricInput;
    }

    @Override
    public EnumSet<Direction> getElectricOutputSet() {
        return electricOutput;
    }

    @Override
    public BlockBatteryBoxBuilder getThis() {
        return this;
    }

    @HideFromJS
    public int getVoltage() {
        return voltage;
    }

    @HideFromJS
    public double getOutput() {
        return output;
    }

    @HideFromJS
    public double getCapacity() {
        return capacity;
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

        Block block = new CustomBlockBatteryBox(TileCustomBatteryBox::new, this);
        TileRegister.VALID_BATTERY_BLOCKS.add(block);
        BlockItemDescriptable.addDescription(() -> block, ElectroTextUtils.voltageTooltip(voltage));
        return block;
    }
}
