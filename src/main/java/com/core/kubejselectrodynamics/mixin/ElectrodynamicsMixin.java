package com.core.kubejselectrodynamics.mixin;

import com.core.kubejselectrodynamics.util.VoxelShapeUtil;
import electrodynamics.Electrodynamics;
import electrodynamics.api.References;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Electrodynamics.class)
public class ElectrodynamicsMixin {
    @Inject(
            method = "onCommonSetup",
            at = @At("TAIL"),
            remap = false
    )
    private static void kjsElectro$onCommonSetupTail(CallbackInfo ci) {
        VoxelShapeUtil.registerMatching(References.ID);
    }
}
