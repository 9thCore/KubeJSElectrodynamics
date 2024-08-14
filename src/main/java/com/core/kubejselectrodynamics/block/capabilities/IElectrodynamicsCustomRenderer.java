package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public interface IElectrodynamicsCustomRenderer {
    @HideFromJS
    boolean usesCustomRenderer();

    @HideFromJS
    default RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
