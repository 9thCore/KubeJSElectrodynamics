package com.core.kubejselectrodynamics.block.voxels;

import com.core.kubejselectrodynamics.block.voxels.shapes.CopyableShapeBlock;
import com.core.kubejselectrodynamics.util.VoxelShapeUtil;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.level.block.Block;

public interface ICopiedShape<T extends BlockBuilder> {
    String ELECTRODYNAMICSID = "electrodynamics";

    @HideFromJS
    CopyableShapeBlock getVoxelShape();
    @Info("Copies the passed machine's voxel shape into this block.")
    default T copyVoxelShape(CopyableShapeBlock block) {
        if (!Platform.isModLoaded(block.modID)) {
            ConsoleJS.STARTUP.warn("Mod " + block.modID + " is not loaded, cannot copy voxel shape from " + block.name() + ".");
            return (T)this;
        }
        return setVoxelShape(block);
    }
    @HideFromJS
    T setVoxelShape(CopyableShapeBlock block);

    @HideFromJS
    default void registerCopiedShape(Block block) {
        VoxelShapeUtil.register(block, getVoxelShape().modID, getVoxelShape().getBlock(), getVoxelShape().direction);
    }

}
