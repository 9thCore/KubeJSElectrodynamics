package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.block.wire.BlockWireBuilder;
import com.core.kubejselectrodynamics.block.wire.TileCustomWire;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import electrodynamics.api.network.cable.type.IConductor;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.network.type.ElectricNetwork;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElectricNetwork.class)
public abstract class ElectricNetworkMixin extends AbstractNetwork<IConductor, SubtypeWire, BlockEntity, TransferPack> {
    @Shadow(remap = false) private double voltage;

    @Shadow(remap = false) private double resistance;

    // Patch method to not exit early if we have custom wires that are overloaded
    @ModifyExpressionValue(
            method = "checkForOverload",
            at = @At(value = "INVOKE", target = "Ljava/util/HashSet;isEmpty()Z"),
            remap = false
    )
    private boolean kjsElectro$wrapEmptyCheck(boolean original) {
        // If it's already going to pass by default, then don't bother continuing
        if (!original) {
            return false;
        }
        // Try to find a custom wire that is overloaded
        for (SubtypeWire wire : BlockWireBuilder.POSSIBLE_SUBTYPES) {
            for (IConductor conductor : conductorTypeMap.get(wire)) {
                if (conductor instanceof TileCustomWire customWire) {
                    if (kjsElectro$isOverloaded(customWire)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Patch method to check for custom wire overload
    @Inject(
            method = "checkForOverload",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$checkForOverloadPatch(CallbackInfoReturnable<Boolean> cir) {
        for (SubtypeWire wire : BlockWireBuilder.POSSIBLE_SUBTYPES) {
            for (IConductor conductor : conductorTypeMap.get(wire)) {
                if (conductor instanceof TileCustomWire customWire) {
                    if (kjsElectro$isOverloaded(customWire)) {
                        Scheduler.schedule(1, conductor::destroyViolently);
                    }
                }
            }
        }
    }

    // Patch method to add up resistance properly
    @Inject(
            method = "updateConductorStatistics(Lelectrodynamics/api/network/cable/type/IConductor;)V",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$updateConductorStatisticsPatch(IConductor cable, CallbackInfo ci) {
        if (cable instanceof TileCustomWire customWire) {
            resistance -= cable.getWireType().resistance;
            resistance += customWire.getResistance();
        }
    }

    private boolean kjsElectro$isOverloaded(TileCustomWire wire) {
        return wire.getAmpacity() <= transmittedLastTick / voltage * 20 && wire.getAmpacity() <= transmittedThisTick / voltage * 20;
    }
}
