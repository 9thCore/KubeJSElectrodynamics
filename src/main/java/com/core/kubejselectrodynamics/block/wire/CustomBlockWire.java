package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.common.block.connect.BlockWire;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomBlockWire extends BlockWire {
    private final BlockWireBuilder builder;

    public CustomBlockWire(BlockWireBuilder builder) {
        super(builder.getSubtype());
        this.builder = builder;
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

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileRegister.WIRE_TYPE.getSupplier().create(pos, state);
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
}
