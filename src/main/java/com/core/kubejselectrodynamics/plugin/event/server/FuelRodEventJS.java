package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.util.ItemUtils;
import com.core.kubejselectrodynamics.util.RadiationUtil;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
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

    @Info("Register a single item or an item tag as a fuel rod with the given fuel value and default spent fuel item.")
    public void register(String id, int fuelValue) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1), fuelValue);
            return;
        }
        registerItem(id, fuelValue);
    }

    @Info("Register a single item as a fuel rod with the given fuel value and default spent fuel item.")
    public void registerItem(String id, int fuelValue) {
        RadiationUtil.register(ItemUtils.getItemFromID(id), fuelValue);
    }

    @Info("Register an item tag as a fuel rod with the given fuel value and default spent fuel item.")
    public void registerTag(String id, int fuelValue) {
        for (Item item : ItemUtils.getTagFromID(id)) {
            RadiationUtil.register(item, fuelValue);
        }
    }

    @Info("Register a single item or an item tag as a fuel rod with the given fuel value and given spent fuel item.")
    public void register(String id, int fuelValue, String spent) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1), fuelValue, spent);
            return;
        }
        registerItem(id, fuelValue, spent);
    }

    @Info("Register a single item as a fuel rod with the given fuel value and given spent fuel item.")
    public void registerItem(String id, int fuelValue, String spent) {
        RadiationUtil.register(ItemUtils.getItemFromID(id), fuelValue, ItemUtils.getItemFromID(spent));
    }

    @Info("Register an item tag as a fuel rod with the given fuel value and given spent fuel item.")
    public void registerTag(String id, int fuelValue, String spent) {
        for (Item item : ItemUtils.getTagFromID(id)) {
            RadiationUtil.register(item, fuelValue, ItemUtils.getItemFromID(spent));
        }
    }
}
