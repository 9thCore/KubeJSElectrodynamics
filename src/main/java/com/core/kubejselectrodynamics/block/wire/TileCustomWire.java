package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.electricitygrid.TileWire;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileCustomWire extends TileWire {
    private final BlockWireBuilder builder;

    public TileCustomWire(BlockPos pos, BlockState state) {
        this(((CustomBlockWire) state.getBlock()).kjs$getBlockBuilder(), pos, state);
    }

    public TileCustomWire(BlockWireBuilder builder, BlockPos pos, BlockState state) {
        super(pos, state);
        this.builder = builder;
    }

    @Override
    public BlockEntityType<?> getType() {
        return TileRegister.WIRE_TYPE.getType();
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
