package com.core.kubejselectrodynamics.block.voxels;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.block.voxelshapes.VoxelShapes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import java.util.List;

public interface ICustomShape<T extends BlockBuilder> {
    @HideFromJS
    List<AABB> getCustomShape();

    @HideFromJS
    default void registerCustomShape(Block block) {
        if (getCustomShape().isEmpty()) {
            getCustomShape().add(new AABB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D));
        }
        VoxelShapes.registerShape(block, BlockBuilder.createShape(getCustomShape()), Direction.NORTH);
    }
}
