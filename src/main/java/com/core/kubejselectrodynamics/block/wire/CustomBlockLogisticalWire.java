package com.core.kubejselectrodynamics.block.wire;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CustomBlockLogisticalWire extends CustomBlockWire {
    public CustomBlockLogisticalWire(BlockWireBuilder builder, BlockEntityType.BlockEntitySupplier<?> supplier) {
        super(builder, supplier);
        stateDefinition.any().setValue(BlockStateProperties.LIT, false);
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
