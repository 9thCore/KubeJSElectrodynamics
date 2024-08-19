package com.core.kubejselectrodynamics.block.storage.gastank;

import com.core.kubejselectrodynamics.block.BlockItemDescribableBuilder;
import com.core.kubejselectrodynamics.block.RotatableBlockBuilder;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsGasInput;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsGasOutput;
import com.core.kubejselectrodynamics.block.voxels.ICustomOrCopiedShape;
import com.core.kubejselectrodynamics.block.voxels.shapes.CopyableShapeBlock;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.common.tile.pipelines.tanks.gas.GenericTileGasTank;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class BlockGasTankBuilder extends RotatableBlockBuilder<BlockGasTankBuilder> implements ICustomOrCopiedShape<BlockGasTankBuilder>, IElectrodynamicsGasInput<BlockGasTankBuilder>, IElectrodynamicsGasOutput<BlockGasTankBuilder> {
    private CopyableShapeBlock shape = null;
    private int capacity = 1000;
    private int pressure = 1000;
    private double temperature = 1000.0D;
    private double heatLoss = GenericTileGasTank.HEAT_LOSS;
    private final EnumSet<Direction> gasInput = EnumSet.of(Direction.UP);
    private final EnumSet<Direction> gasOutput = EnumSet.of(Direction.DOWN);
    public BlockGasTankBuilder(ResourceLocation location) {
        super(location);
    }

    @Info("Sets the tank's capacity, as an integer.")
    public BlockGasTankBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @Info("Sets the tank's maximum pressure, as an integer.")
    public BlockGasTankBuilder pressure(int pressure) {
        this.pressure = pressure;
        return this;
    }

    @Info("Sets the tank's maximum temperature, as a double.")
    public BlockGasTankBuilder temperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    @Info("Sets the tank's per tick heat loss, as a double. Cannot go below 0.")
    public BlockGasTankBuilder heatLoss(double heatLoss) {
        this.heatLoss = Math.max(0.0D, heatLoss);
        return this;
    }

    @Override
    public CopyableShapeBlock getVoxelShape() {
        return shape;
    }

    @Override
    public BlockGasTankBuilder setVoxelShape(CopyableShapeBlock block) {
        shape = block;
        return this;
    }

    @Override
    public List<AABB> getCustomShape() {
        return customShape;
    }

    @Override
    public EnumSet<Direction> getGasInputSet() {
        return gasInput;
    }

    @Override
    public EnumSet<Direction> getGasOutputSet() {
        return gasOutput;
    }

    @Override
    public BlockGasTankBuilder getThis() {
        return this;
    }

    @HideFromJS
    public int getCapacity() {
        return capacity;
    }

    @HideFromJS
    public int getPressure() {
        return pressure;
    }

    @HideFromJS
    public double getTemperature() {
        return temperature;
    }

    @HideFromJS
    public double getHeatLoss() {
        return heatLoss;
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
        Block block = new CustomBlockGasTank(TileRegister.GAS_TANK_TYPE.getSupplier(), this);
        TileRegister.TANK_TYPE.valid(block);
        registerShape(block);
        BlockItemDescriptable.addDescription(
                () -> block,
                ElectroTextUtils.tooltip("gastank.capacity", ChatFormatter.getChatDisplayShort((double) capacity / 1000, DisplayUnit.BUCKETS))
        );
        return block;
    }
}
