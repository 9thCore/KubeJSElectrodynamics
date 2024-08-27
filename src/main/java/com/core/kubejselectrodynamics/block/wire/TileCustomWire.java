package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.electricitygrid.GenericTileWire;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileCustomWire extends GenericTileWire {
    public Property<Double> transmit;
    public SubtypeWire wire;
    public SubtypeWire.WireColor color;
    private final BlockWireBuilder builder;

    public TileCustomWire(BlockPos pos, BlockState state) {
        this(((CustomBlockWire) state.getBlock()).kjs$getBlockBuilder(), TileRegister.WIRE_TYPE.getType(), pos, state);
    }

    public TileCustomWire(BlockWireBuilder builder, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
        this.builder = builder;
        this.transmit = this.property(new Property<>(PropertyType.Double, "transmit", 0.0));
        this.wire = builder.getSubtype();
        this.color = builder.getWireColor();
        this.addComponent(new ComponentTickable(this));
    }

    @Override
    public SubtypeWire getWireType() {
        return builder.getSubtype();
    }

    @Override
    public SubtypeWire.WireColor getWireColor() {
        return builder.getWireColor();
    }

    public double getResistance() {
        return builder.getWireResistance();
    }

    public long getAmpacity() {
        return builder.getWireAmpacity();
    }

    @Override
    public double getMaxTransfer() {
        return getAmpacity();
    }
}
