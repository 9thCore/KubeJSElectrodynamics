package com.core.kubejselectrodynamics.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public interface CustomItemExtension {
    void registerIntoTab(BuildCreativeModeTabContentsEvent event);
    boolean isCorrectTab(CreativeModeTab tab);
}
