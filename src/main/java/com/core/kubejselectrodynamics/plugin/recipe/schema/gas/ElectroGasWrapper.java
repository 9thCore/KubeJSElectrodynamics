package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ElectroGasWrapper {
    public static final ResourceLocation HYDROGEN_ID = new ResourceLocation("electrodynamics:hydrogen");
    public static final ResourceLocation OXYGEN_ID = new ResourceLocation("electrodynamics:oxygen");
    public static final ResourceLocation STEAM_ID = new ResourceLocation("electrodynamics:steam");

    public static ElectroGasStackJS empty() {
        return EmptyElectroGasStackJS.INSTANCE;
    }
    public static ElectroGasStackJS hydrogen() {
        return ElectroGasStackJS.of(HYDROGEN_ID);
    }
    public static ElectroGasStackJS oxygen() {
        return ElectroGasStackJS.of(OXYGEN_ID);
    }
    public static ElectroGasStackJS steam() {
        return ElectroGasStackJS.of(STEAM_ID);
    }

    public static ElectroGasStackJS of(@Nullable Object from) {
        return ElectroGasStackJS.of(from);
    }
    public static ElectroGasStackJS of(@Nullable Object from, double amount) {
        return ElectroGasStackJS.of(from).withAmount(amount);
    }
    public static ElectroGasStackJS of(@Nullable Object from, double amount, int pressure) {
        return ElectroGasStackJS.of(from).withAmount(amount).withPressure(pressure);
    }
    public static ElectroGasStackJS of(@Nullable Object from, double amount, int pressure, double temperature) {
        return ElectroGasStackJS.of(from).withAmount(amount).withPressure(pressure).withTemperature(temperature);
    }
}
