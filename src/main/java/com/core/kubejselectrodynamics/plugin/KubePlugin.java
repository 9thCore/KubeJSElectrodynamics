package com.core.kubejselectrodynamics.plugin;

import com.core.kubejselectrodynamics.item.canister.ItemCanisterBuilder;
import com.core.kubejselectrodynamics.item.portable_cylinder.ItemPortableCylinderBuilder;
import com.core.kubejselectrodynamics.plugin.recipe.schema.FluidItemToFluid;
import com.core.kubejselectrodynamics.plugin.recipe.schema.FluidToGas;
import com.core.kubejselectrodynamics.plugin.recipe.schema.FluidToItem;
import com.core.kubejselectrodynamics.plugin.recipe.schema.ItemToItem;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.ElectroGasWrapper;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.GasBuilder;
import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.resources.ResourceLocation;

public class KubePlugin extends KubeJSPlugin {
    public ResourceLocation GetRecipeType(String type) {
        return new ResourceLocation("electrodynamics", type);
    }

    public void register(RegisterRecipeSchemasEvent event, String name, RecipeSchema schema) {
        event.register(GetRecipeType(name + "_recipe"), schema);
    }

    @Override
    public void init() {
        GasBuilder.INFO.addType("basic", GasBuilder.class, GasBuilder::new, true);
        RegistryInfo.ITEM.addType("electrodynamics:portable_cylinder", ItemPortableCylinderBuilder.class, ItemPortableCylinderBuilder::new, false);
        RegistryInfo.ITEM.addType("electrodynamics:canister", ItemCanisterBuilder.class, ItemCanisterBuilder::new, false);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
       register(event, "energized_alloyer", ItemToItem.ALLOYER_SCHEMA);
        register(event, "lathe", ItemToItem.SCHEMA);
        register(event, "mineral_crusher", ItemToItem.SCHEMA);
        register(event, "mineral_grinder", ItemToItem.SCHEMA);
        register(event, "oxidation_furnace", ItemToItem.OXIDIZER_FURNACE_SCHEMA);
        register(event, "reinforced_alloyer", ItemToItem.ALLOYER_SCHEMA);
        register(event, "wire_mill", ItemToItem.SCHEMA);
        register(event, "chemical_crystallizer", FluidToItem.SCHEMA);
        register(event, "chemical_mixer", FluidItemToFluid.SCHEMA);
        register(event, "fermentation_plant", FluidItemToFluid.SCHEMA);
        register(event, "mineral_washer", FluidItemToFluid.SCHEMA);
        register(event, "electrolytic_separator", FluidToGas.SCHEMA);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
        event.add("ElectrodynamicsFluid", ElectroFluidWrapper.class);
        event.add("ElectrodynamicsGas", ElectroGasWrapper.class);
    }
}
