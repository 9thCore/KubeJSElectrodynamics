package com.core.kubejselectrodynamics.plugin;

import com.core.kubejselectrodynamics.block.batterybox.BlockBatteryBoxBuilder;
import com.core.kubejselectrodynamics.block.motor.ac.BlockMotorACBuilder;
import com.core.kubejselectrodynamics.block.motor.dc.BlockMotorDCBuilder;
import com.core.kubejselectrodynamics.item.battery.ItemBatteryBuilder;
import com.core.kubejselectrodynamics.item.canister.ItemCanisterBuilder;
import com.core.kubejselectrodynamics.item.portable_cylinder.ItemPortableCylinderBuilder;
import com.core.kubejselectrodynamics.plugin.recipe.schema.FluidItemToFluid;
import com.core.kubejselectrodynamics.plugin.recipe.schema.FluidToGas;
import com.core.kubejselectrodynamics.plugin.recipe.schema.FluidToItem;
import com.core.kubejselectrodynamics.plugin.recipe.schema.ItemToItem;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.ElectroGasWrapper;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.GasBuilder;
import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import dev.architectury.platform.Platform;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.resources.ResourceLocation;

public class KubePlugin extends KubeJSPlugin {
    public ResourceLocation GetRecipeType(RecipeHolder holder, String type) {
        return new ResourceLocation(holder.mod, type);
    }

    public void register(RegisterRecipeSchemasEvent event, RecipeHolder holder, String name, RecipeSchema schema) {
        event.register(GetRecipeType(holder, name + "_recipe"), schema);
    }

    @Override
    public void init() {
        // ELECTRODYNAMICS
        // GAS
        GasBuilder.INFO.addType("basic", GasBuilder.class, GasBuilder::new, true);

        // ITEM
        RegistryInfo.ITEM.addType("electrodynamics:portable_cylinder", ItemPortableCylinderBuilder.class, ItemPortableCylinderBuilder::new, false);
        RegistryInfo.ITEM.addType("electrodynamics:canister", ItemCanisterBuilder.class, ItemCanisterBuilder::new, false);
        RegistryInfo.ITEM.addType("electrodynamics:battery", ItemBatteryBuilder.class, ItemBatteryBuilder::new, false);

        // BLOCK
        RegistryInfo.BLOCK.addType("electrodynamics:batterybox", BlockBatteryBoxBuilder.class, BlockBatteryBoxBuilder::new, false);

        // DYNAMIC ELECTRICITY
        if (Platform.isModLoaded("dynamicelectricity")) {
            RegistryInfo.BLOCK.addType("dynamicelectricity:motordc", BlockMotorDCBuilder.class, BlockMotorDCBuilder::new, false);
            RegistryInfo.BLOCK.addType("dynamicelectricity:motorac", BlockMotorACBuilder.class, BlockMotorACBuilder::new, false);
        }
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        // Electrodynamics
        register(event, RecipeHolder.ELECTRODYNAMICS, "energized_alloyer", ItemToItem.ALLOYER_SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "lathe", ItemToItem.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "mineral_crusher", ItemToItem.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "mineral_grinder", ItemToItem.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "oxidation_furnace", ItemToItem.OXIDIZER_FURNACE_SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "reinforced_alloyer", ItemToItem.ALLOYER_SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "wire_mill", ItemToItem.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "chemical_crystallizer", FluidToItem.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "chemical_mixer", FluidItemToFluid.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "fermentation_plant", FluidItemToFluid.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "mineral_washer", FluidItemToFluid.SCHEMA);
        register(event, RecipeHolder.ELECTRODYNAMICS, "electrolytic_separator", FluidToGas.SCHEMA);

        // Blastcraft
        if (Platform.isModLoaded("blastcraft")) {
            register(event, RecipeHolder.BLASTCRAFT, "blast_compressor", ItemToItem.SCHEMA);
        }
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
        event.add("ElectrodynamicsFluid", ElectroFluidWrapper.class);
        event.add("ElectrodynamicsGas", ElectroGasWrapper.class);
    }

    enum RecipeHolder {
        ELECTRODYNAMICS("electrodynamics"),
        BLASTCRAFT("blastcraft");

        public final String mod;
        RecipeHolder(String name) {
            this.mod = name;
        }
    }
}
