package com.core.kubejselectrodynamics.mixin.electrodynamics.gastank;

import com.core.kubejselectrodynamics.util.InsulationUtils;
import com.core.kubejselectrodynamics.util.mixinterfaces.ISlotRestricted;
import electrodynamics.common.inventory.container.tile.ContainerGasTankGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ContainerGasTankGeneric.class)
public class ContainerGasTankGenericMixin {
    @Inject(
            method = "addInventorySlots",
            at = @At("TAIL"),
            remap = false
    )
    private void kjsElectro$addInventorySlotsPatch(Container inv, Inventory playerinv, CallbackInfo ci) {
        ContainerGasTankGeneric tank = (ContainerGasTankGeneric) (Object) this;
        for (Slot slot : tank.slots) {
            if (slot instanceof ISlotRestricted restricted) {
                List<Item> list = new ArrayList<>(restricted.kjsElectro$getWhitelist());
                list.addAll(InsulationUtils.insulationEffectiveness.keySet());
                restricted.kjsElectro$setWhitelist(list);
            }
        }
    }
}
