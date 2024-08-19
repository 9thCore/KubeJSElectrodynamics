package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.*;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsCustomRenderer;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsElectricityInput;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsElectricityOutput;
import com.core.kubejselectrodynamics.block.voxels.ICustomOrCopiedShape;
import com.core.kubejselectrodynamics.block.voxels.shapes.CopyableShapeBlock;
import com.core.kubejselectrodynamics.util.TileUtils;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class BlockBatteryBoxBuilder extends RotatableBlockBuilder<BlockBatteryBoxBuilder> implements IElectrodynamicsElectricityInput<BlockBatteryBoxBuilder>, IElectrodynamicsElectricityOutput<BlockBatteryBoxBuilder>, ICustomOrCopiedShape<BlockBatteryBoxBuilder>, IElectrodynamicsCustomRenderer {
    private static final ResourceLocation[] BATTERYBOXMODELS = TileUtils.generateModelArray(7, "electrodynamics:block/batterybox");
    private static final ResourceLocation[] LITHIUMBATTERYBOXMODELS = TileUtils.generateModelArray(7, "electrodynamics:block/lithiumbatterybox");
    private static final ResourceLocation[] CARBYNEBATTERYBOXMODELS = TileUtils.generateModelArray(7, "electrodynamics:block/carbynebatterybox");
    private int voltage = 120;
    private double capacity = 1000.0D;
    private double output = 300.0D;
    private final EnumSet<Direction> electricInput = EnumSet.of(Direction.SOUTH);
    private final EnumSet<Direction> electricOutput = EnumSet.of(Direction.NORTH);
    private CopyableShapeBlock shape = null;
    private ResourceLocation[] models = null;

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

    @Info("Uses the base Electrodynamics Battery Box renderer provided.")
    public BlockBatteryBoxBuilder useBaseRenderer(BatteryType type) {
        models = switch(type) {
            case LITHIUMBATTERYBOX -> LITHIUMBATTERYBOXMODELS;
            case CARBYNEBATTERYBOX -> CARBYNEBATTERYBOXMODELS;
            default -> BATTERYBOXMODELS;
        };
        if (models.length > 0) {
            model(models[0].toString());
        }
        return this;
    }

    @Info("Uses the specified models as the renderer.")
    public BlockBatteryBoxBuilder useCustomRenderer(String[] models) {
        this.models = new ResourceLocation[models.length];
        for (int i = 0; i < models.length; i++) {
            this.models[i] = new ResourceLocation(models[i]);
        }
        if (models.length > 0) {
            model(models[0]);
        }
        return this;
    }

    @Override
    public boolean usesCustomRenderer() {
        return models != null;
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
    public CopyableShapeBlock getVoxelShape() {
        return shape;
    }

    @Override
    public BlockBatteryBoxBuilder setVoxelShape(CopyableShapeBlock block) {
        shape = block;
        return this;
    }

    @Override
    public List<AABB> getCustomShape() {
        return customShape;
    }

    @HideFromJS
    public ResourceLocation getModel(double currentEnergy, double maxEnergy) {
        if (models.length == 0) {
            return null;
        }
        int index = (int) (currentEnergy / maxEnergy * (models.length - 1));
        return models[Math.max(0, Math.min(models.length - 1, index))];
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
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

        Block block = new CustomBlockBatteryBox(TileRegister.BATTERY_BOX_TYPE.getSupplier(usesCustomRenderer()), this);
        TileRegister.BATTERY_BOX_TYPE.valid(block);
        registerShape(block);
        BlockItemDescriptable.addDescription(() -> block, ElectroTextUtils.voltageTooltip(voltage));
        return block;
    }

    public enum BatteryType {
        BATTERYBOX,
        LITHIUMBATTERYBOX,
        CARBYNEBATTERYBOX
    }
}
