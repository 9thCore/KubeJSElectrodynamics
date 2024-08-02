package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

// Creates Electrodynamics gasses from JS
public class GasBuilder extends BuilderBase<Gas> {
    public static RegistryInfo<Gas> INFO = RegistryInfo.of(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, Gas.class);
    private final ResourceLocation location;
    private Component name;
    private double condensationTemp = Double.NaN;
    private Fluid condensationFluid = Fluids.EMPTY;
    private Item item;

    public GasBuilder(ResourceLocation location) {
        super(location);
        this.location = location;
    }

    @Override
    public RegistryInfo<Gas> getRegistryType() {
        return INFO;
    }

    public GasBuilder setName(Component name) {
        this.name = name;
        return this;
    }

    public GasBuilder setCondensationFluid(@NotNull Fluid condensationFluid, double condensationTemp) {
        this.condensationFluid = condensationFluid;
        this.condensationTemp = condensationTemp;
        return this;
    }

    public GasBuilder setContainer(Item item) {
        this.item = item;
        return this;
    }

    @Override
    public Gas createObject() {
        return new Gas(
                () -> item,
                TagKey.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, location),
                name,
                condensationTemp,
                condensationFluid
        );
    }
}
