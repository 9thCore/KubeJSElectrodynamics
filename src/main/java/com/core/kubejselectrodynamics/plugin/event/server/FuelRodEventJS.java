package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.util.RadiationUtil;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.Objects;

public class FuelRodEventJS extends EventJS {
    public FuelRodEventJS() {
        RadiationUtil.customFuelRods.clear();
    }

    public void register(String id, int fuelValue) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1), fuelValue);
            return;
        }
        registerItem(id, fuelValue);
    }

    public void registerItem(String id, int fuelValue) {
        ResourceLocation location = new ResourceLocation(id);
        if (!ForgeRegistries.ITEMS.containsKey(location)) {
            ConsoleJS.SERVER.error("Could not find item " + id);
            throw new IllegalArgumentException("Invalid item " + id);
        }
        RadiationUtil.customFuelRods.put(ForgeRegistries.ITEMS.getValue(location), fuelValue);
    }

    public void registerTag(String id, int fuelValue) {
        TagKey<Item> key = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(id));
        ITag<Item> tag = Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(key);
        for (Item item : tag) {
            RadiationUtil.customFuelRods.put(item, fuelValue);
        }
    }
}
