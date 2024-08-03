package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * JS helper class.
 */
public class ElectroGasWrapper {
    @Info("ID of Electrodynamics' base hydrogen gas.")
    public static final ResourceLocation HYDROGEN_ID = new ResourceLocation("electrodynamics:hydrogen");
    @Info("ID of Electrodynamics' base oxygen gas.")
    public static final ResourceLocation OXYGEN_ID = new ResourceLocation("electrodynamics:oxygen");
    @Info("ID of Electrodynamics' base steam gas.")
    public static final ResourceLocation STEAM_ID = new ResourceLocation("electrodynamics:steam");

    @Info("Returns an empty gas instance.")
    public static ElectroGasStackJS empty() {
        return EmptyElectroGasStackJS.INSTANCE;
    }
    @Info("Returns a hydrogen gas instance.")
    public static ElectroGasStackJS hydrogen() {
        return ElectroGasStackJS.of(HYDROGEN_ID);
    }
    @Info("Returns an oxygen gas instance.")
    public static ElectroGasStackJS oxygen() {
        return ElectroGasStackJS.of(OXYGEN_ID);
    }
    @Info("Returns a steam gas instance.")
    public static ElectroGasStackJS steam() {
        return ElectroGasStackJS.of(STEAM_ID);
    }

    @Info("Returns an ElectroGasStackJS object representing the given object, with default amount (= 1000.0), default pressure (= 1) and default temperature (= 273.0).")
    public static ElectroGasStackJS of(@Nullable Object from) {
        return ElectroGasStackJS.of(from);
    }
    @Info("Returns an ElectroGasStackJS object representing the given object, with given amount, default pressure (= 1) and default temperature (= 273.0).")
    public static ElectroGasStackJS of(@Nullable Object from, double amount) {
        return ElectroGasStackJS.of(from).withAmount(amount);
    }
    @Info("Returns an ElectroGasStackJS object representing the given object, with given amount, given pressure and default temperature (= 273.0).")
    public static ElectroGasStackJS of(@Nullable Object from, double amount, int pressure) {
        return ElectroGasStackJS.of(from).withAmount(amount).withPressure(pressure);
    }
    @Info("Returns an ElectroGasStackJS object representing the given object, with given amount, given pressure and given temperature.")
    public static ElectroGasStackJS of(@Nullable Object from, double amount, int pressure, double temperature) {
        return ElectroGasStackJS.of(from).withAmount(amount).withPressure(pressure).withTemperature(temperature);
    }
}
