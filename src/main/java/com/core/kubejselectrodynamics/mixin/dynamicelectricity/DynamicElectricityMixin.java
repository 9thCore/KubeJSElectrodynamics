package com.core.kubejselectrodynamics.mixin.dynamicelectricity;

import com.core.kubejselectrodynamics.util.VoxelShapeUtil;
import dynamicelectricity.DynamicElectricity;
import dynamicelectricity.References;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DynamicElectricity.class)
public class DynamicElectricityMixin {
    @Inject(
            method = "onCommonSetup",
            at = @At("TAIL"),
            remap = false
    )
    private static void kjsElectro$onCommonSetupTail(CallbackInfo ci) {
        VoxelShapeUtil.registerMatching(References.ID);
    }
}
