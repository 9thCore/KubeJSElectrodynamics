package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.block.gaspipe.BlockGasPipeBuilder;
import com.core.kubejselectrodynamics.block.gaspipe.TileCustomGasPipe;
import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import electrodynamics.api.gas.GasStack;
import electrodynamics.api.network.cable.type.IGasPipe;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.common.network.type.GasNetwork;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.network.AbstractNetwork;
import electrodynamics.prefab.utilities.Scheduler;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

@Mixin(GasNetwork.class)
public abstract class GasNetworkMixin extends AbstractNetwork<IGasPipe, SubtypeGasPipe, BlockEntity, GasStack> {
    // Use custom max pressure instead of PipeMaterial#maxPressure
    @WrapOperation(
            method = "updateConductorStatistics(Lelectrodynamics/api/network/cable/type/IGasPipe;)V",
            at = @At(
                    value = "FIELD",
                    target = "Lelectrodynamics/common/block/subtype/SubtypeGasPipe$PipeMaterial;maxPressure:I"
            ),
            remap = false
    )
    private int kjsElectro$updateConductorStatisticsMaxPressurePatch(SubtypeGasPipe.PipeMaterial instance, Operation<Integer> original, @Local(argsOnly = true) IGasPipe cable) {
        if (cable instanceof TileCustomGasPipe pipe) {
            return pipe.getMaxPressure();
        }
        return original.call(instance);
    }

    // Filter out custom pipes from base mod logic
    @Definition(id = "conductorTypeMap", field = "Lelectrodynamics/common/network/type/GasNetwork;conductorTypeMap:Ljava/util/HashMap;")
    @Definition(id = "get", method = "Ljava/util/HashMap;get(Ljava/lang/Object;)Ljava/lang/Object;")
    @Definition(id = "iterator", method = "Ljava/util/HashSet;iterator()Ljava/util/Iterator;")
    @Definition(id = "HashSet", type = HashSet.class)
    @Expression("((HashSet) this.conductorTypeMap.get(?)).iterator()")
    @ModifyExpressionValue(
            method = "checkForOverloadAndHandle",
            at = @At("MIXINEXTRAS:EXPRESSION"),
            remap = false
    )
    private Iterator<Object> kjsElectro$filterSubtypeGasPipeIterator(Iterator<Object> original) {
        return Iterators.filter(original, o -> !(o instanceof TileCustomGasPipe));
    }

    // Patch overload method to handle custom gas pipes
    @Inject(
            method = "checkForOverloadAndHandle",
            at = @At("TAIL"),
            remap = false,
            cancellable = true
    )
    private void kjsElectro$checkForOverloadAndHandlePatch(GasStack stack, boolean live, CallbackInfoReturnable<Boolean> cir) {
        boolean isCorrosive = Objects.requireNonNull(ElectrodynamicsRegistries.gasRegistry().tags()).getTag(ElectrodynamicsTags.Gases.IS_CORROSIVE).contains(stack.getGas());
        for (SubtypeGasPipe pipe : BlockGasPipeBuilder.POSSIBLE_SUBTYPES) {
            for (IGasPipe gasPipe : conductorTypeMap.get(pipe)) {
                if (gasPipe instanceof TileCustomGasPipe customPipe && kjsElectro$isOverloaded(stack, isCorrosive, customPipe)) {
                    Scheduler.schedule(1, gasPipe::destroyViolently);
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Unique
    private boolean kjsElectro$isOverloaded(GasStack stack, boolean corrosive, TileCustomGasPipe pipe) {
        return (pipe.getMaxPressure() < stack.getPressure()) || (corrosive && pipe.canCorrodeFromAcid());
    }
}
