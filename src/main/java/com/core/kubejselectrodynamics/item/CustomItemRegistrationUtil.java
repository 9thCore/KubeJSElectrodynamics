package com.core.kubejselectrodynamics.item;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.latvian.mods.kubejs.item.creativetab.KubeJSCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Not much reason to have this here besides main file decluttering and organisational purposes
 */
@Mod.EventBusSubscriber(modid = KubeJSElectrodynamics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CustomItemRegistrationUtil {
    public static final List<CustomItemExtension> items = new ArrayList<>();

    @SubscribeEvent
    public static void addItemsToCreativeModeTab(BuildCreativeModeTabContentsEvent event) {
        for (CustomItemExtension item : items) {
            if (item.isCorrectTab(event.getTab())) {
                item.registerIntoTab(event);
            }
        }
    }

    public static RegistrySupplier<CreativeModeTab> getKubeJSTab() {
        return KubeJSCreativeTabs.REGISTER.iterator().next(); // slightly hacky
    }
}
