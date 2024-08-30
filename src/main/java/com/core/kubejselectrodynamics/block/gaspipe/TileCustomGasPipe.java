package com.core.kubejselectrodynamics.block.gaspipe;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.tile.pipelines.fluids.GenericTileFluidPipe;
import electrodynamics.common.tile.pipelines.gas.GenericTileGasPipe;
import electrodynamics.common.tile.pipelines.gas.TileGasPipe;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileCustomGasPipe extends TileGasPipe {
    private final BlockGasPipeBuilder builder;

    public TileCustomGasPipe(BlockPos pos, BlockState state) {
        super(pos, state);
        this.builder = ((CustomBlockGasPipe) state.getBlock()).kjs$getBlockBuilder();
    }

    @Override
    public BlockEntityType<?> getType() {
        return TileRegister.GAS_PIPE_TYPE.getType();
    }

    @Override
    public SubtypeGasPipe getPipeType() {
        return builder.getPipe();
    }

    public int getMaxPressure() {
        return builder.getMaxPressure();
    }

    @Override
    public double getMaxTransfer() {
        return builder.getMaxTransfer();
    }

    @Override
    public double getEffectiveHeatLoss() {
        return builder.getEffectiveHeatLoss();
    }

    @Override
    public boolean isInsulationFlammable() {
        return builder.isInsulationFlammable();
    }

    @Override
    public boolean canCorrodeFromAcid() {
        return builder.isCorrodedByAcid();
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        // don't save anything
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        // don't load anything
    }
}
