package com.core.kubejselectrodynamics.plugin.event.server;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.common.reloadlistener.CombustionFuelRegister;
import electrodynamics.prefab.utilities.object.CombustionFuelSource;
import net.minecraft.resources.ResourceLocation;

public class CombustionEventJS extends EventJS {
    @Info("Register a fluid tag as a combustion fuel source with the given usage (integer) and power multiplier (double).")
    public void register(String id, int amount, double multiplier) {
        // lol
        JsonObject object = new JsonObject();
        object.addProperty(CombustionFuelSource.FLUID_KEY, id);
        object.addProperty(CombustionFuelSource.USAGE_AMOUNT, amount);
        object.addProperty(CombustionFuelSource.POWER_MULTIPLIER, multiplier);
        CombustionFuelRegister.INSTANCE.getFuels().add(CombustionFuelSource.fromJson(object));
    }

    @Info("Unregister a fluid tag as a combustion fuel source.")
    public void unregister(String id) {
        ResourceLocation location = new ResourceLocation(id);
        CombustionFuelSource foundSource = CombustionFuelSource.EMPTY;
        for (CombustionFuelSource source : CombustionFuelRegister.INSTANCE.getFuels()) {
            if (source.getTag().location().compareTo(location) == 0) {
                foundSource = source;
                break;
            }
        }

        if (foundSource.isEmpty()) {
            ConsoleJS.SERVER.warn("Fluid " + id + " is not registered as a combustion fuel source");
            return;
        }
        CombustionFuelRegister.INSTANCE.getFuels().remove(foundSource);
    }
}
