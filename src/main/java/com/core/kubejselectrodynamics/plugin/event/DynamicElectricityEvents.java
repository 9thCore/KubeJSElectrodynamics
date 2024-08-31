package com.core.kubejselectrodynamics.plugin.event;

import com.core.kubejselectrodynamics.plugin.event.server.ConductorBrushEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface DynamicElectricityEvents {
    EventGroup GROUP = EventGroup.of("DynamicElectricityEvents");
    EventHandler CONDUCTOR_BRUSH = GROUP.server("conductorBrush", () -> ConductorBrushEventJS.class);
}
