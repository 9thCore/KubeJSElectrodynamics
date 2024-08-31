package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.util.ItemUtils;
import com.core.kubejselectrodynamics.util.MotorBrushRegister;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.item.Item;

public class ConductorBrushEventJS extends EventJS {
    @Info("Register a single item or an item tag as a conductor brush.")
    public void register(String id) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1));
            return;
        }
        registerItem(id);
    }

    @Info("Register a single item as a conductor brush.")
    public void registerItem(String id) {
        MotorBrushRegister.register(ItemUtils.getItemFromID(id));
    }

    @Info("Register an item tag as a conductor brush.")
    public void registerTag(String id) {
        for (Item item : ItemUtils.getTagFromID(id)) {
            MotorBrushRegister.register(item);
        }
    }
}
