package com.core.kubejselectrodynamics.plugin.event.server;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.common.reloadlistener.ThermoelectricGeneratorHeatRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

import java.util.Objects;

public class ThermoelectricEventJS extends EventJS {
    @Info("Register a single fluid or a fluid tag as a heat source with the given heat.")
    public void register(String id, double heat) {
        if (id.startsWith("#")) {
            registerTag(id.substring(1), heat);
            return;
        }
        registerFluid(id, heat);
    }

    @Info("Register a single fluid as a heat source with the given heat.")
    public void registerFluid(String id, double heat) {
        ResourceLocation location = new ResourceLocation(id);
        if (!ForgeRegistries.FLUIDS.containsKey(location)) {
            ConsoleJS.SERVER.error("Could not find fluid " + id);
            throw new IllegalArgumentException("Invalid fluid " + id);
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(location);
        ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatSources().put(fluid, heat);
    }

    @Info("Register a fluid tag as a heat source with the given heat.")
    public void registerTag(String id, double heat) {
        TagKey<Fluid> key = TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(), new ResourceLocation(id));
        ITag<Fluid> tag = Objects.requireNonNull(ForgeRegistries.FLUIDS.tags()).getTag(key);
        for (Fluid fluid : tag) {
            ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatSources().put(fluid, heat);
        }
    }

    @Info("Unregister a single fluid or a fluid tag as a heat source.")
    public void unregister(String id) {
        if (id.startsWith("#")) {
            unregisterTag(id.substring(1));
            return;
        }
        unregisterFluid(id);
    }

    @Info("Unregister a single fluid as a heat source.")
    public void unregisterFluid(String id) {
        ResourceLocation location = new ResourceLocation(id);
        if (!ForgeRegistries.FLUIDS.containsKey(location)) {
            ConsoleJS.SERVER.error("Could not find fluid " + id);
            throw new IllegalArgumentException("Invalid fluid " + id);
        }
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(location);
        if (!ThermoelectricGeneratorHeatRegister.INSTANCE.isHeatSource(fluid)) {
            ConsoleJS.SERVER.warn("Fluid " + id + " is not registered as a heat source");
            return;
        }
        ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatSources().remove(fluid);
    }

    @Info("Unregister a fluid tag as a heat source.")
    public void unregisterTag(String id) {
        TagKey<Fluid> key = TagKey.create(ForgeRegistries.FLUIDS.getRegistryKey(), new ResourceLocation(id));
        ITag<Fluid> tag = Objects.requireNonNull(ForgeRegistries.FLUIDS.tags()).getTag(key);
        for (Fluid fluid : tag) {
            if (!ThermoelectricGeneratorHeatRegister.INSTANCE.isHeatSource(fluid)) {
                ConsoleJS.SERVER.warn("Fluid " + id + " is not registered as a heat source");
                continue;
            }
            ThermoelectricGeneratorHeatRegister.INSTANCE.getHeatSources().remove(fluid);
        }
    }
}
