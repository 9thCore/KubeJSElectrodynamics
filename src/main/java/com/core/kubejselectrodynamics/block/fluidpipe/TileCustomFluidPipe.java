package com.core.kubejselectrodynamics.block.fluidpipe;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.tile.pipelines.fluids.TileFluidPipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileCustomFluidPipe extends TileFluidPipe {
    private final BlockFluidPipeBuilder builder;

    public TileCustomFluidPipe(BlockPos pos, BlockState state) {
        super(pos, state);
        this.builder = ((CustomBlockFluidPipe) state.getBlock()).kjs$getBlockBuilder();
    }

    @Override
    public BlockEntityType<?> getType() {
        return TileRegister.FLUID_PIPE_TYPE.getType();
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
