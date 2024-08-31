package com.core.kubejselectrodynamics.mixin.dynamicelectricity;

import com.core.kubejselectrodynamics.util.MotorBrushRegister;
import com.core.kubejselectrodynamics.util.mixinterfaces.ISlotRestricted;
import dynamicelectricity.common.inventory.container.ContainerMotorDC;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ContainerMotorDC.class)
public class ContainerMotorDCMixin {
    @Inject(
            method = "addInventorySlots",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$alterRestriction(Container inv, Inventory playerInv, CallbackInfo ci) {
        ISlotRestricted slot = (ISlotRestricted)(((ContainerMotorDC)(Object)this).slots.get(0));
        List<Item> whitelist = slot.kjsElectro$getWhitelist();
        // This sucks
        List<Item> newWhitelist = new ArrayList<>(whitelist);
        newWhitelist.addAll(MotorBrushRegister.brushes.keySet());
        slot.kjsElectro$setWhitelist(newWhitelist);
    }
}
