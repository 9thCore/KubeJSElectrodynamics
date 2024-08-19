package com.core.kubejselectrodynamics.block.storage.tank;

import com.core.kubejselectrodynamics.block.BlockItemDescribableBuilder;
import com.core.kubejselectrodynamics.block.RotatableBlockBuilder;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsCustomRenderer;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsFluidInput;
import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsFluidOutput;
import com.core.kubejselectrodynamics.block.motor.MotorBlockBuilder;
import com.core.kubejselectrodynamics.block.motor.ac.CustomBlockMotorAC;
import com.core.kubejselectrodynamics.block.voxels.ICustomOrCopiedShape;
import com.core.kubejselectrodynamics.block.voxels.shapes.CopyableShapeBlock;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class BlockTankBuilder extends RotatableBlockBuilder<BlockTankBuilder> implements IElectrodynamicsFluidInput<BlockTankBuilder>, IElectrodynamicsFluidOutput<BlockTankBuilder>, IElectrodynamicsCustomRenderer, ICustomOrCopiedShape<BlockTankBuilder> {
    private CopyableShapeBlock shape = null;
    private boolean renderFluid = false;
    private int capacity = 1000;
    private final EnumSet<Direction> fluidInput = EnumSet.of(Direction.UP);
    private final EnumSet<Direction> fluidOutput = EnumSet.of(Direction.DOWN);

    public BlockTankBuilder(ResourceLocation location) {
        super(location);
    }

    @Info("Sets the tank's capacity, as an integer.")
    public BlockTankBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @Info("Renders the fluid contained within the block.")
    public BlockTankBuilder useBaseRenderer() {
        renderFluid = true;
        return this;
    }

    @HideFromJS
    public int getCapacity() {
        return capacity;
    }

    @Override
    public EnumSet<Direction> getFluidInputSet() {
        return fluidInput;
    }

    @Override
    public EnumSet<Direction> getFluidOutputSet() {
        return fluidOutput;
    }

    @Override
    public BlockTankBuilder getThis() {
        return this;
    }

    @Override
    public boolean usesCustomRenderer() {
        return renderFluid;
    }

    @Override
    public CopyableShapeBlock getVoxelShape() {
        return shape;
    }

    @Override
    public BlockTankBuilder setVoxelShape(CopyableShapeBlock block) {
        shape = block;
        return this;
    }

    @Override
    public List<AABB> getCustomShape() {
        return customShape;
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
        Block block = new CustomBlockTank(TileRegister.TANK_TYPE.getSupplier(usesCustomRenderer()), this);
        TileRegister.TANK_TYPE.valid(block);
        registerShape(block);
        BlockItemDescriptable.addDescription(
                () -> block,
                ElectroTextUtils.tooltip("fluidtank.capacity", ChatFormatter.getChatDisplayShort((double) capacity / 1000, DisplayUnit.BUCKETS))
        );
        return block;
    }
}
