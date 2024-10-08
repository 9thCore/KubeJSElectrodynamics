package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.GasRegistrationUtil;
import com.core.kubejselectrodynamics.util.TextUtil;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import electrodynamics.api.gas.Gas;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

/**
 * Electrodynamics Gas creator from JavaScript.
 */
public class GasBuilder extends BuilderBase<Gas> {
    public static RegistryInfo<Gas> INFO = RegistryInfo.of(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, Gas.class);
    private final ResourceLocation location;
    private double condensationTemp = Double.NaN;
    private Fluid condensationFluid = Fluids.EMPTY;
    private Item item = Items.AIR;

    public GasBuilder(ResourceLocation location) {
        super(location);
        this.location = location;
    }

    @Override
    public RegistryInfo<Gas> getRegistryType() {
        return INFO;
    }

    @Info("Gas will be condensated into given condensationFluid when its temperature is below condensationTemp.")
    public GasBuilder condensationFluid(@NotNull Fluid condensationFluid, double condensationTemp) {
        this.condensationFluid = condensationFluid;
        this.condensationTemp = condensationTemp;
        return this;
    }

    @Info("Presumably default container used for gas? Defaults to Electrodynamics Portable Cylinder.")
    public GasBuilder container(Item item) {
        this.item = item;
        return this;
    }

    @Override
    public Gas createObject() {
        Gas gas = new Gas(
            () -> item,
            TagKey.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, location),
            Component.translatable(getBuilderTranslationKey()),
            condensationTemp,
            condensationFluid
        );
        GasRegistrationUtil.KJS_REGISTERED_GASSES.add(RegistryObject.create(location, ElectrodynamicsRegistries.gasRegistry()));
        return gas;
    }
}
