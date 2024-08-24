package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.util.mixinterfaces.ISlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.function.Predicate;

@Mixin(SlotRestricted.class)
public class SlotRestrictedMixin implements ISlotRestricted {
    @Shadow(remap = false) private List<Item> whitelist;

    @Shadow(remap = false) private Predicate<ItemStack> mayPlace;

    @Override
    public List<Item> kjsElectro$getWhitelist() {
        return whitelist;
    }

    @Override
    public void kjsElectro$setWhitelist(List<Item> items) {
        whitelist = items;
    }

    @Override
    public void kjsElectro$mayPlace(boolean forcedResult) {
        mayPlace = (stack) -> forcedResult;
    }
}
