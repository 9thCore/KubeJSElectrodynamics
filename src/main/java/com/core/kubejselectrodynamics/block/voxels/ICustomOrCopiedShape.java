package com.core.kubejselectrodynamics.block.voxels;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.level.block.Block;

public interface ICustomOrCopiedShape<T extends BlockBuilder> extends ICustomShape<T>, ICopiedShape<T> {
    @HideFromJS
    default void registerShape(Block block) {
        if (getVoxelShape() != null) {
            registerCopiedShape(block);
        } else {
            registerCustomShape(block);
        }
    }
}
