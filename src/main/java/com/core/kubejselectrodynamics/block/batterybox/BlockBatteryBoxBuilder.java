package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.BlockItemDescribableBuilder;
import com.core.kubejselectrodynamics.block.ElectrodynamicsElectricityCapable;
import com.core.kubejselectrodynamics.block.TileRegister;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import dev.latvian.mods.kubejs.block.entity.BlockEntityInfo;
import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BlockBatteryBoxBuilder extends HorizontalDirectionalBlockBuilder implements ElectrodynamicsElectricityCapable<BlockBatteryBoxBuilder> {
    private int voltage = 120;
    private double capacity = 1000.0D;
    private double output = 300.0D;
    private int modelRotationOffset = 0;
    private final Boolean[] inputSides = new Boolean[] {false, false, false, true, false, false}; // SOUTH
    private final Boolean[] outputSides = new Boolean[] {false, false, true, false, false, false}; // NORTH

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

    @Info("Applies an offset to the model's rotation when generating the block state data.")
    public BlockBatteryBoxBuilder rotationOffset(int offset) {
        modelRotationOffset = getTrueRotation(offset);
        return this;
    }

    @Info("Equivalent to calling rotationOffset(270), as it is the rotation offset of base Electrodynamics' battery model rotation.")
    public BlockBatteryBoxBuilder batteryModel() {
        return rotationOffset(270);
    }

    @Override
    public BlockBatteryBoxBuilder electricInputs(Direction[] directions) {
        allElectricInput();
        for (Direction direction : directions) {
            inputSides[direction.ordinal()] = true;
        }
        return this;
    }

    @Override
    public Direction[] getElectricInput() {
        List<Direction> list = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (inputSides[direction.ordinal()]) {
                list.add(direction);
            }
        }
        return list.toArray(new Direction[0]);
    }

    @Override
    public BlockBatteryBoxBuilder allElectricInput() {
        for (Direction direction : Direction.values()) {
            inputSides[direction.ordinal()] = false;
        }
        return this;
    }

    @Override
    public BlockBatteryBoxBuilder electricOutputs(Direction[] directions) {
        allElectricOutput();
        for (Direction direction : directions) {
            outputSides[direction.ordinal()] = true;
        }
        return this;
    }

    @Override
    public Direction[] getElectricOutput() {
        List<Direction> list = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (outputSides[direction.ordinal()]) {
                list.add(direction);
            }
        }
        return list.toArray(new Direction[0]);
    }

    @Override
    public BlockBatteryBoxBuilder allElectricOutput() {
        for (Direction direction : Direction.values()) {
            outputSides[direction.ordinal()] = false;
        }
        return this;
    }

    /**
     * Method originally from KubeJS' source code, adapted to fit custom implementation
     */
    @Override
    protected void generateBlockStateJson(VariantBlockStateGenerator bs) {
        var modelLocation = model.isEmpty() ? newID("block/", "").toString() : model;
        bs.variant("facing=north", v -> v.model(modelLocation).y(modelRotationOffset));
        bs.variant("facing=east", v -> v.model(modelLocation).y(getTrueRotation(90 + modelRotationOffset)));
        bs.variant("facing=south", v -> v.model(modelLocation).y(getTrueRotation(180 + modelRotationOffset)));
        bs.variant("facing=west", v -> v.model(modelLocation).y(getTrueRotation(270 + modelRotationOffset)));
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

    private static int getTrueRotation(int rotation) {
        return rotation % 360;
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
