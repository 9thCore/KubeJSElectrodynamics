package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.util.InsulationUtils;
import com.core.kubejselectrodynamics.util.ItemUtils;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.item.Item;

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
        InsulationUtils.registerInsulator(ItemUtils.getItemFromID(id), effectiveness);
    }

    @Info("Register an item tag as a gas tank insulator with the given insulation.")
    public void registerTag(String id, double effectiveness) {
        for (Item item : ItemUtils.getTagFromID(id)) {
            InsulationUtils.registerInsulator(item, effectiveness);
        }
    }
}
