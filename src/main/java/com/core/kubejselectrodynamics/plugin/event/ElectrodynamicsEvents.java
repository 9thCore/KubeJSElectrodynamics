package com.core.kubejselectrodynamics.plugin.event;

import com.core.kubejselectrodynamics.plugin.event.server.CombustionEventJS;
import com.core.kubejselectrodynamics.plugin.event.server.GasTankInsulatorEventJS;
import com.core.kubejselectrodynamics.plugin.event.server.ThermoelectricEventJS;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface ElectrodynamicsEvents {
    EventGroup GROUP = EventGroup.of("ElectrodynamicsEvents");
    EventHandler THERMOELECTRIC_GENERATOR = GROUP.server("thermoelectricGenerator", () -> ThermoelectricEventJS.class);
    EventHandler COMBUSTION_CHAMBER = GROUP.server("combustionChamber", () -> CombustionEventJS.class);
    EventHandler GAS_TANK_INSULATION = GROUP.server("gasTankInsulation", () -> GasTankInsulatorEventJS.class);
}
