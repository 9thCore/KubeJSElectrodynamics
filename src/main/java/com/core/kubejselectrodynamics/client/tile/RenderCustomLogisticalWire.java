package com.core.kubejselectrodynamics.client.tile;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.wire.TileCustomLogisticalWire;
import com.mojang.blaze3d.vertex.PoseStack;
import electrodynamics.client.render.tile.AbstractTileRenderer;
import electrodynamics.common.block.BlockMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class RenderCustomLogisticalWire extends AbstractTileRenderer<TileCustomLogisticalWire> {
    public RenderCustomLogisticalWire(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull TileCustomLogisticalWire tile, float v, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, int i1) {
        if (!tile.getBlockState().getValue(BlockMachine.ON)) {
            return;
        }

        Minecraft minecraft = minecraft();

        BlockPos pos = tile.getBlockPos();

        RandomSource random = minecraft.level.getRandom();

        if (random.nextFloat() > 0.02) {
            return;
        }

        minecraft.level.addParticle(new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, random.nextFloat()), pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, 0, 0);
    }
}
