package com.core.kubejselectrodynamics.util;

import electrodynamics.common.block.voxelshapes.VoxelShapes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class VoxelShapeUtil {
    public static Map<Block, CopiableVoxelShape> map = new HashMap<>();

    public static void register(Block block, String copyModID, Block copy, Direction direction) {
        map.put(block, new CopiableVoxelShape(copy, copyModID, direction));
    }

    public static void registerMatching(String modID) {
        VoxelShapeUtil.map.forEach((block, properties) -> {
            if (properties.modID.equals(modID)) {
                VoxelShapes.registerShape(block, VoxelShapes.getShape(properties.block(), properties.direction()), properties.direction());
            }
        });
    }

    public record CopiableVoxelShape(Block block, String modID, Direction direction) {}
}
