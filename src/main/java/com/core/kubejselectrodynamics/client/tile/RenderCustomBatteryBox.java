package com.core.kubejselectrodynamics.client.tile;

import com.core.kubejselectrodynamics.block.batterybox.BlockBatteryBoxBuilder;
import com.core.kubejselectrodynamics.block.batterybox.CustomBlockBatteryBox;
import com.core.kubejselectrodynamics.block.batterybox.TileCustomBatteryBox;
import com.mojang.blaze3d.vertex.PoseStack;
import electrodynamics.client.ClientRegister;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.MathUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class RenderCustomBatteryBox extends RenderBase<TileCustomBatteryBox.Render> {
    public RenderCustomBatteryBox(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void render(TileCustomBatteryBox.Render tileEntityIn, float pPartialTick, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockBatteryBoxBuilder builder = ((CustomBlockBatteryBox) tileEntityIn.getBlockState().getBlock()).kjs$getBlockBuilder();
        ComponentElectrodynamic el = tileEntityIn.getComponent(IComponentType.Electrodynamic);
        BakedModel ibakedmodel = getModel(builder.getModel(el.getJoulesStored(), el.getMaxJoulesStored()));

        if (ibakedmodel == null) {
            return;
        }

        switch (tileEntityIn.getBlockState().getValue(GenericEntityBlock.FACING)) {
            case NORTH -> {
                matrixStackIn.mulPose(MathUtils.rotQuaternionDeg(0, 90, 0));
                matrixStackIn.translate(-1, 0, 0);
            }
            case SOUTH -> {
                matrixStackIn.mulPose(MathUtils.rotQuaternionDeg(0, 270, 0));
                matrixStackIn.translate(0, 0, -1);
            }
            case WEST -> {
                matrixStackIn.mulPose(MathUtils.rotQuaternionDeg(0, 180, 0));
                matrixStackIn.translate(-1, 0, -1);
            }
            default -> {
            }
        }
        matrixStackIn.translate(0.5, 0.5, 0.5);
        RenderingUtils.renderModel(ibakedmodel, tileEntityIn, RenderType.solid(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }
}
