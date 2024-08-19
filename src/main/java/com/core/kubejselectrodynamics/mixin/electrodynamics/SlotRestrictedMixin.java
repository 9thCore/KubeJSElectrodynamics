package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.util.mixinterfaces.ISlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(SlotRestricted.class)
public class SlotRestrictedMixin implements ISlotRestricted {
    @Shadow(remap = false) private List<Item> whitelist;

    @Override
    public List<Item> kjsElectro$getWhitelist() {
        return whitelist;
    }

    @Override
    public void kjsElectro$setWhitelist(List<Item> items) {
        whitelist = items;
    }
}
