package com.core.kubejselectrodynamics.block.motor;

import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsCustomRenderer;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsFluidInput;
import com.core.kubejselectrodynamics.block.voxels.ICustomOrCopiedShape;
import com.core.kubejselectrodynamics.block.RotatableBlockBuilder;
import com.core.kubejselectrodynamics.block.voxels.shapes.CopyableShapeBlock;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class MotorBlockBuilder<T extends MotorBlockBuilder<T>> extends RotatableBlockBuilder<T> implements IElectrodynamicsFluidInput<T>, ICustomOrCopiedShape<T>, IElectrodynamicsCustomRenderer {
    protected ShaftType shaftType = null;
    protected double conversionEfficiency = 1.0D;
    protected final EnumSet<Direction> fluidInput = EnumSet.of(Direction.DOWN);
    private CopyableShapeBlock shape = null;
    public MotorBlockBuilder(ResourceLocation location) {
        super(location);
    }

    @Override
    public T setVoxelShape(CopyableShapeBlock shape) {
        this.shape = shape;
        return getThis();
    }

    public CopyableShapeBlock getVoxelShape() {
        return shape;
    }

    @Override
    public List<AABB> getCustomShape() {
        return customShape;
    }

    @Info("Renders the specified shaft on the current block.")
    public T useBaseRenderer(ShaftType shaftType) {
        this.shaftType = shaftType;
        return getThis();
    }

    @Info("Sets the motor's conversion efficiency, as a double. Cannot go below 0.")
    public T conversionEfficiency(double conversionEfficiency) {
        this.conversionEfficiency = Math.max(0.0D, conversionEfficiency);
        return getThis();
    }

    @HideFromJS
    public double getConversionEfficiency() {
        return conversionEfficiency;
    }

    @Override
    public EnumSet<Direction> getFluidInputSet() {
        return fluidInput;
    }

    @Override
    public T getThis() {
        return (T)this;
    }

    @HideFromJS
    public ShaftType getShaftType() {
        return shaftType;
    }

    @Override
    public boolean usesCustomRenderer() {
        return shaftType != null;
    }

    public enum ShaftType {
        LV,
        MV,
        HV
    }
}
