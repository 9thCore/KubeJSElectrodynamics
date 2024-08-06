package com.core.kubejselectrodynamics.item;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class CustomItemTab {
    public static DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), KubeJSElectrodynamics.MODID);
    public static final List<CustomItemExtension> items = new ArrayList<>();
    public static final RegistryObject<CreativeModeTab> TAB = REGISTER.register("electrodynamics_items", () -> CreativeModeTab.builder()
            .icon(() -> ElectrodynamicsItems.ITEM_PORTABLECYLINDER.get().getDefaultInstance())
            .displayItems((params, output) -> {
                for (CustomItemExtension item : items) {
                    output.acceptAll(item.getEntries());
                }
            })
            .title(Component.translatable("tab.kubejselectrodynamics"))
            .build()
    );
}
