package com.core.kubejselectrodynamics.mixin.dynamicelectricity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dynamicelectricity.common.tile.generic.TileMotorDC;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TileMotorDC.class)
public class TileMotorDCMixin {
    @WrapOperation(
            method = "tickServer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getDamageValue()I"
            )
    )
    private int kjsElectro$damageIfAllowed(ItemStack instance, Operation<Integer> original) {
        return instance.isDamageableItem() ? original.call(instance) : -1;
    }
}
