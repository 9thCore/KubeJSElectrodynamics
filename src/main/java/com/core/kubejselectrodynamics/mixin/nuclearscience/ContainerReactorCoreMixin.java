package com.core.kubejselectrodynamics.mixin.nuclearscience;

import com.core.kubejselectrodynamics.util.RadiationUtil;
import com.core.kubejselectrodynamics.util.mixinterfaces.ISlotRestricted;
import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ContainerReactorCore.class)
public class ContainerReactorCoreMixin {
    @Inject(
            method = "addInventorySlots",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$addInventorySlotsPatch(CallbackInfo callbackInfo) {
        NonNullList<Slot> slots = ((ContainerReactorCore)(Object) this).slots;
        // Adjust restriction of fuel rod inputs
        for (int i = 0; i < 4; i++) {
            if (slots.get(i) instanceof ISlotRestricted restricted) {
                List<Item> whitelist = restricted.kjsElectro$getWhitelist();
                List<Item> newWhitelist = new ArrayList<>(whitelist);
                newWhitelist.addAll(RadiationUtil.customFuelRods.keySet());
                restricted.kjsElectro$setWhitelist(newWhitelist);
            }
        }
        // Adjust restriction of "Deuterium" slot to allow every item instead
        if (slots.get(TileFissionReactorCore.DUETERIUM_SLOT) instanceof ISlotRestricted restricted) {
            restricted.kjsElectro$mayPlace(true);
        }
    }
}
