package com.core.kubejselectrodynamics.mixin.electrodynamics.event;

import com.core.kubejselectrodynamics.plugin.event.ElectrodynamicsEvents;
import com.core.kubejselectrodynamics.plugin.event.server.CombustionEventJS;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(CombustionFuelRegister.class)
public class CombustionFuelRegisterMixin {
    @Shadow @Final private HashSet<CombustionFuelSource> fuels;

    @Inject(
            method = "lambda$getDatapackSyncListener$1",
            at = @At("HEAD"),
            remap = false
    )
    private void kjsElectro$datapackSyncListenerPatch(SimpleChannel channel, OnDatapackSyncEvent event, CallbackInfo ci) {
        ElectrodynamicsEvents.COMBUSTION_CHAMBER.post(new CombustionEventJS());
    }
}
