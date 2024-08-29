package com.core.kubejselectrodynamics.block.fluidpipe;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.tile.pipelines.fluids.GenericTileFluidPipe;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileCustomFluidPipe extends GenericTileFluidPipe {
    private final BlockFluidPipeBuilder builder;
    public Property<Double> transmit;

    public TileCustomFluidPipe(BlockPos pos, BlockState state) {
        super(TileRegister.FLUID_PIPE_TYPE.getType(), pos, state);
        this.transmit = this.property(new Property<>(PropertyType.Double, "transmit", 0.0));
        this.builder = ((CustomBlockFluidPipe) state.getBlock()).kjs$getBlockBuilder();
    }

    @Override
    public SubtypeFluidPipe getPipeType() {
        return SubtypeFluidPipe.copper;
    }

    @Override
    public double getMaxTransfer() {
        return builder.getMaxTransfer();
    }
}
