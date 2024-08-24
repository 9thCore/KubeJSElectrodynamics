package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.util.InsulationUtils;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.Objects;

public class GasTankInsulatorEventJS extends EventJS {
    public GasTankInsulatorEventJS() {
        InsulationUtils.insulationEffectiveness.clear();
    }

    @Info("Register a single item or an item tag as a gas tank insulator with the given insulation.")
    public void register(String id, double effectiveness) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1), effectiveness);
            return;
        }
        registerItem(id, effectiveness);
    }

    @Info("Register a single item as a gas tank insulator with the given insulation.")
    public void registerItem(String id, double effectiveness) {
        ResourceLocation location = new ResourceLocation(id);
        if (!ForgeRegistries.ITEMS.containsKey(location)) {
            ConsoleJS.SERVER.error("Could not find item " + id);
            throw new IllegalArgumentException("Invalid item " + id);
        }
        InsulationUtils.registerInsulator(ForgeRegistries.ITEMS.getValue(location), effectiveness);
    }

    @Info("Register an item tag as a gas tank insulator with the given insulation.")
    public void registerTag(String id, double effectiveness) {
        TagKey<Item> key = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(id));
        ITag<Item> tag = Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(key);
        for (Item item : tag) {
            InsulationUtils.registerInsulator(item, effectiveness);
        }
    }
}
