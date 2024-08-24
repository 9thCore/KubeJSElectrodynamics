package com.core.kubejselectrodynamics.mixin.nuclearscience;

import com.core.kubejselectrodynamics.util.RadiationUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileFissionReactorCore.class)
public abstract class TileFissionReactorCoreMixin {
    @Shadow(remap = false) @Final public static int FUEL_ROD_COUNT;

    @Shadow(remap = false) public Property<Integer> fuelCount;

    @Inject(
            method = "onInventoryChange",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$onInventoryChangePatch(ComponentInventory inv, int slot, CallbackInfo ci) {
        if (slot == -1 || slot < FUEL_ROD_COUNT) {
            int delta = 0;
            for (int i = 0; i < FUEL_ROD_COUNT; i++) {
                ItemStack stack = inv.getItem(i);
                if (!stack.isEmpty() && RadiationUtil.customFuelRods.containsKey(stack.getItem())) {
                    delta += RadiationUtil.customFuelRods.get(stack.getItem()) * stack.getCount();
                }
            }
            fuelCount.set(fuelCount.get() + delta);
        }
    }

    @WrapOperation(
            method = "tickServer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getDamageValue()I"
            ),
            remap = false
    )
    private int kjsElectro$damageIfAllowed(ItemStack instance, Operation<Integer> original) {
        return instance.isDamageableItem() ? original.call(instance) : -1;
    }
}
