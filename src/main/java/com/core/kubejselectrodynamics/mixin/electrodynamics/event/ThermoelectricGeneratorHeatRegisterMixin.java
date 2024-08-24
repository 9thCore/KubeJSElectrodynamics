package com.core.kubejselectrodynamics.mixin.electrodynamics.event;

import com.core.kubejselectrodynamics.plugin.event.ElectrodynamicsEvents;
import com.core.kubejselectrodynamics.plugin.event.server.ThermoelectricEventJS;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThermoelectricGeneratorHeatRegister.class)
public class ThermoelectricGeneratorHeatRegisterMixin {
    @Inject(
            method = "generateTagValues",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$generateTagValuesPatch(CallbackInfo ci) {
        ElectrodynamicsEvents.THERMOELECTRIC_GENERATOR.post(new ThermoelectricEventJS());
    }
}
