package com.core.kubejselectrodynamics.mixin.electrodynamics.event;

import com.core.kubejselectrodynamics.plugin.event.ElectrodynamicsEvents;
import com.core.kubejselectrodynamics.plugin.event.server.CombustionEventJS;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CombustionFuelRegister.class)
public class CombustionFuelRegisterMixin {
    @Inject(
            method = "lambda$getDatapackSyncListener$1",
            at = @At("HEAD"),
            remap = false
    )
    private void kjsElectro$datapackSyncListenerPatch(SimpleChannel channel, OnDatapackSyncEvent event, CallbackInfo ci) {
        ElectrodynamicsEvents.COMBUSTION_CHAMBER.post(new CombustionEventJS());
    }
}
