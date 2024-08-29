package com.core.kubejselectrodynamics.block.fluidpipe;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.api.network.cable.type.IFluidPipe;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBlockFluidPipe extends AbstractRefreshingConnectBlock {
    private final BlockFluidPipeBuilder builder;
    private final BlockEntityType.BlockEntitySupplier<?> supplier;

    public CustomBlockFluidPipe(BlockFluidPipeBuilder builder, BlockEntityType.BlockEntitySupplier<?> supplier, BlockBehaviour.Properties properties) {
        super(properties, 3.0D);
        this.builder = builder;
        this.supplier = supplier;
    }

    public BlockFluidPipeBuilder kjs$getBlockBuilder() {
        return builder;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return supplier.create(pos, state);
    }

    @Override
    public BlockState refreshConnections(BlockState otherState, BlockEntity otherTile, BlockState state, BlockEntity thisTile, Direction dir) {
        if (thisTile instanceof GenericConnectTile thisConnect) {
            EnumConnectType connection = EnumConnectType.NONE;
            if (otherTile instanceof IFluidPipe) {
                connection = EnumConnectType.WIRE;
            } else if (FluidUtilities.isFluidReceiver(otherTile, dir.getOpposite())) {
                connection = EnumConnectType.INVENTORY;
            }

            thisConnect.writeConnection(dir, connection);
        }
        return state;
    }

    @Override
    public IRefreshableCable getCableIfValid(BlockEntity tile) {
        if (tile instanceof IFluidPipe pipe) {
            return pipe;
        } else {
            return null;
        }
    }
}
