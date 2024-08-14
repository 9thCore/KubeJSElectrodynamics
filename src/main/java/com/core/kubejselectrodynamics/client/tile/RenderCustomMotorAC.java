package com.core.kubejselectrodynamics.client.tile;

import com.core.kubejselectrodynamics.block.motor.MotorBlockBuilder;
import com.core.kubejselectrodynamics.block.motor.ac.TileCustomMotorAC;
import com.core.kubejselectrodynamics.block.motor.dc.TileCustomMotorDC;
import com.mojang.blaze3d.vertex.PoseStack;
import dynamicelectricity.client.ClientRegister;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;

public class RenderCustomMotorAC extends RenderBase<TileCustomMotorAC.Render> {
    public RenderCustomMotorAC(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TileCustomMotorAC.Render tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        MotorBlockBuilder.ShaftType type = tile.builder.getShaftType();
        if (type == null) {
            return;
        }

        matrix.pushPose();
        Direction facing = tile.getFacing();
        double progress = Math.sin(0.15707963267948966 * (double)partialTicks);
        float progressDegrees = 0.0F;
        if (tile.running.get()) {
            progressDegrees = 360.0F * (float)progress;
        }

        BakedModel shaft = switch (type) {
            case LV -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOTORAC_LVSHAFT);
            case MV -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOTORAC_MVSHAFT);
            case HV -> Minecraft.getInstance().getModelManager().getModel(ClientRegister.MODEL_MOTORAC_HVSHAFT);
        };

        switch (facing) {
            case EAST:
                matrix.translate(0.938, 0.5, 0.5);
                matrix.mulPose(MathUtils.rotVectorQuaternionDeg(progressDegrees, MathUtils.XN));
                break;
            case WEST:
                matrix.translate(0.5, 0.5, 0.5);
                matrix.mulPose(MathUtils.rotVectorQuaternionDeg(progressDegrees, MathUtils.XP));
                break;
            case SOUTH:
                matrix.translate(0.5, 0.5, 0.5);
                matrix.mulPose(MathUtils.rotVectorQuaternionDeg(90.0F, MathUtils.YP));
                matrix.mulPose(MathUtils.rotVectorQuaternionDeg(progressDegrees, MathUtils.XP));
                break;
            case NORTH:
                matrix.translate(0.5, 0.5, 0.062);
                matrix.mulPose(MathUtils.rotVectorQuaternionDeg(90.0F, MathUtils.YP));
                matrix.mulPose(MathUtils.rotVectorQuaternionDeg(progressDegrees, MathUtils.XN));
        }

        RenderingUtils.renderModel(shaft, tile, RenderType.solid(), matrix, bufferIn, combinedLightIn, combinedOverlayIn);
        matrix.popPose();
    }
}
