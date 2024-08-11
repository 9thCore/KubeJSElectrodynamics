package com.core.kubejselectrodynamics.mixin;

import com.core.kubejselectrodynamics.block.batterybox.TileCustomBatteryBox;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class UtilsMixins {
    @Mixin(ElectricityUtils.class)
    public static class ElectricityUtilsMixin {
        /**
         * Ensure the mod does not consider modded batteries as tiles with the ForgeEnergy capability
         */
        @Inject(
                method = "receivePower",
                at = @At(
                        value = "INVOKE_ASSIGN",
                        target = "Lelectrodynamics/prefab/utilities/object/TransferPack;joulesVoltage(DD)Lelectrodynamics/prefab/utilities/object/TransferPack;",
                        shift = At.Shift.AFTER
                ),
                remap = false,
                cancellable = true
        )
        private static void kjsElectro$receivePower(BlockEntity tile, Direction direction, TransferPack transfer, boolean debug, CallbackInfoReturnable<TransferPack> cir) {
            if (tile instanceof TileCustomBatteryBox) {
                cir.setReturnValue(TransferPack.EMPTY);
                cir.cancel();
            }
        }
    }
}
