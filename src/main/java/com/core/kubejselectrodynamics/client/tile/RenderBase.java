package com.core.kubejselectrodynamics.client.tile;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class RenderBase<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public BlockEntityRendererProvider.Context context;
    public RenderBase(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }
}
