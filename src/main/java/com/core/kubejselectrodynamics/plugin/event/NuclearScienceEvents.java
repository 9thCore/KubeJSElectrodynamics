package com.core.kubejselectrodynamics.plugin.event;

import com.core.kubejselectrodynamics.plugin.event.server.FuelRodEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface NuclearScienceEvents {
    EventGroup GROUP = EventGroup.of("NuclearScienceEvents");
    EventHandler FUEL_ROD = GROUP.server("fuelRod", () -> FuelRodEventJS.class);
}
