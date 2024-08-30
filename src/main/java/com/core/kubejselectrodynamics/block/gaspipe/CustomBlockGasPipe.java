package com.core.kubejselectrodynamics.block.gaspipe;

import electrodynamics.api.network.cable.IRefreshableCable;
import electrodynamics.api.network.cable.type.IGasPipe;
import electrodynamics.common.block.connect.BlockGasPipe;
import electrodynamics.common.block.connect.util.AbstractRefreshingConnectBlock;
import electrodynamics.common.block.connect.util.EnumConnectType;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.network.type.GasNetwork;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.common.tile.pipelines.gas.TileGasPipe;
import electrodynamics.prefab.tile.types.GenericConnectTile;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CustomBlockGasPipe extends AbstractRefreshingConnectBlock {
    private final BlockGasPipeBuilder builder;
    private final BlockEntityType.BlockEntitySupplier<?> supplier;

    public CustomBlockGasPipe(BlockGasPipeBuilder builder, BlockEntityType.BlockEntitySupplier<?> supplier, Properties properties) {
        super(properties, 3.0D);
        this.builder = builder;
        this.supplier = supplier;
    }

    public BlockGasPipeBuilder kjs$getBlockBuilder() {
        return builder;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return supplier.create(pos, state);
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return builder.getPipe().insulationMaterial.canCombust;
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        if (!builder.getPipe().insulationMaterial.canCombust) {
            return 0;
        } else {
            return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 150;
        }
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        if (!builder.getPipe().insulationMaterial.canCombust) {
            return 0;
        } else {
            return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) ? 0 : 400;
        }
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter) {
        super.onCaughtFire(state, world, pos, face, igniter);
        Scheduler.schedule(5, () -> {
            world.setBlockAndUpdate(pos, builder.getBurnt().defaultBlockState());
        });
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        TileGasPipe tile = (TileGasPipe)worldIn.getBlockEntity(pos);
        if (!worldIn.isClientSide() && tile != null && (tile.getNetwork() != null || !(tile.getNetwork().transmittedThisTick <= 0.0))) {
            GasNetwork network = tile.getNetwork();
            double multipler = builder.getPipe().insulationMaterial == SubtypeGasPipe.InsulationMaterial.NONE ? 1.0 : 1.2;
            if (network.temperatureOfTransmitted >= 327.0 * multipler) {
                entityIn.hurt(entityIn.damageSources().inFire(), 1.0F);
            } else if (network.temperatureOfTransmitted > 0.0 && network.temperatureOfTransmitted <= 260.0 / multipler) {
                entityIn.hurt(entityIn.damageSources().freeze(), 1.0F);
            }

        }
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public BlockState refreshConnections(BlockState otherState, BlockEntity otherTile, BlockState state, BlockEntity thisTile, Direction dir) {
        if (!(thisTile instanceof GenericConnectTile thisConnect)) {
            return state;
        }
        EnumConnectType connection = EnumConnectType.NONE;
        if (otherTile instanceof IGasPipe) {
            connection = EnumConnectType.WIRE;
        } else if (GasUtilities.isGasReciever(otherTile, dir.getOpposite())) {
            connection = EnumConnectType.INVENTORY;
        }

        thisConnect.writeConnection(dir, connection);
        return state;
    }

    /**
     * Method originally from Electrodynamics' source
     */
    public IRefreshableCable getCableIfValid(BlockEntity tile) {
        if (tile instanceof IGasPipe pipe) {
            return pipe;
        } else {
            return null;
        }
    }
}
