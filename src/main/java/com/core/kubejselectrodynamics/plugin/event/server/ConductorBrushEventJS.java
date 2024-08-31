package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.util.ItemUtils;
import com.core.kubejselectrodynamics.util.MotorBrushRegister;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.item.Item;

public class ConductorBrushEventJS extends EventJS {
    @Info("Register a single item or an item tag as a conductor brush. Places tooltip at default index, 1.")
    public void register(String id) {
        register(id, 1);
    }

    @Info("Register a single item as a conductor brush. Places tooltip at default index, 1.")
    public void registerItem(String id) {
        registerItem(id, 1);
    }

    @Info("Register an item tag as a conductor brush. Places tooltip at default index, 1.")
    public void registerTag(String id) {
        registerTag(id, 1);
    }

    @Info("Register a single item or an item tag as a conductor brush. Places tooltip at index provided, as an integer.")
    public void register(String id, int index) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1), index);
            return;
        }
        registerItem(id, index);
    }

    @Info("Register a single item as a conductor brush. Places tooltip at index provided, as an integer.")
    public void registerItem(String id, int index) {
        MotorBrushRegister.register(ItemUtils.getItemFromID(id), index);
    }

    @Info("Register an item tag as a conductor brush. Places tooltip at index provided, as an integer.")
    public void registerTag(String id, int index) {
        for (Item item : ItemUtils.getTagFromID(id)) {
            MotorBrushRegister.register(item, index);
        }
    }
}
