package com.core.kubejselectrodynamics.util.mixinterfaces;

import net.minecraft.world.item.Item;

import java.util.List;

public interface ISlotRestricted {
    List<Item> kjsElectro$getWhitelist();
    void kjsElectro$setWhitelist(List<Item> items);
}
