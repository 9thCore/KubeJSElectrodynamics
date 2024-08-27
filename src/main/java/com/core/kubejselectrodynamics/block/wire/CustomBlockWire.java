package com.core.kubejselectrodynamics.block.wire;

import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tile.electricitygrid.TileLogisticalWire;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBlockWire extends BlockWire {
    private final BlockWireBuilder builder;
    private final BlockEntityType.BlockEntitySupplier<?> supplier;

    public CustomBlockWire(BlockWireBuilder builder, BlockEntityType.BlockEntitySupplier<?> supplier) {
        super(builder.getSubtype());
        this.builder = builder;
        this.supplier = supplier;
    }

    public BlockWireBuilder kjs$getBlockBuilder() {
        return builder;
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
        super.onCaughtFire(state, world, pos, face, igniter);
        Scheduler.schedule(5, () -> {
            SubtypeWire wire = SubtypeWire.getWire(this.wire.conductor, SubtypeWire.InsulationMaterial.BARE, SubtypeWire.WireClass.BARE, SubtypeWire.WireColor.NONE);
            if (wire == null) {
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            } else {
                world.setBlockAndUpdate(pos, builder.getBare().defaultBlockState());
            }

        });
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public BlockState refreshConnections(BlockState otherState, BlockEntity otherTile, BlockState state, BlockEntity thisTile, Direction dir) {
        if(!(thisTile instanceof GenericConnectTile)) {
            return state;
        }
        GenericConnectTile thisConnect = (GenericConnectTile) thisTile;
        EnumConnectType connection = EnumConnectType.NONE;
        if (thisTile instanceof IConductor thisConductor && otherTile instanceof IConductor otherConductor) {
            if(otherConductor.getWireType().isDefaultColor() || wire.isDefaultColor()
                    || otherConductor.getWireColor() == thisConductor.getWireColor()) {
                connection = EnumConnectType.WIRE;
            }
        } else if (ElectricityUtils.isElectricReceiver(otherTile, dir.getOpposite()) || checkRedstone(otherState)) {
            connection = EnumConnectType.INVENTORY;
        }
        thisConnect.writeConnection(dir, connection);
        return state;
    }

    /**
     * Method originally from Electrodynamics' source
     */
    private boolean checkRedstone(BlockState otherState) {
        return otherState.isSignalSource() && wire.wireClass.conductsRedstone;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return supplier.create(pos, state);
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        BlockEntity tile = blockAccess.getBlockEntity(pos);
        if (tile instanceof TileCustomLogisticalWire w) {
            return w.isPowered ? 15 : 0;
        } else {
            return 0;
        }
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn.getBlockEntity(pos) instanceof TileCustomWire tile &&
                tile.getNetwork() != null && tile.getNetwork().getActiveTransmitted() > 0) {
            int shockVoltage = tile.wire.insulation.shockVoltage;
            if (shockVoltage == 0 || tile.getNetwork().getActiveVoltage() > shockVoltage) {
                ElectricityUtils.electrecuteEntity(entityIn, TransferPack.joulesVoltage(tile.getNetwork().getActiveTransmitted(), tile.getNetwork().getActiveVoltage()));
            }
        }
    }
}
