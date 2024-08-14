package com.core.kubejselectrodynamics.block.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.BiConsumer;

public interface IBlockStateModifyChecker {
    Direction getLastKnownDirection();
    void setLastKnownDirection(Direction direction);
    default void checkUpdate(BlockState state, BiConsumer<BlockState, BlockState> callback) {
        if (!state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            return;
        }
        Direction current = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (current != getLastKnownDirection()) {
            setLastKnownDirection(current);
            callback.accept(state, state);
        }
    }
}
