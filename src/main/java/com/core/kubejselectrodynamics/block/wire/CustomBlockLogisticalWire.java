package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.block.TileRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CustomBlockLogisticalWire extends CustomBlockWire {
    public CustomBlockLogisticalWire(BlockWireBuilder builder) {
        super(builder);
        stateDefinition.any().setValue(BlockStateProperties.LIT, false);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return TileRegister.LOGISTICAL_WIRE_TYPE.getSupplier().create(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(BlockStateProperties.LIT, false);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.LIT);
    }
}
