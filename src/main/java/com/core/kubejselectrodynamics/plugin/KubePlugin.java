package com.core.kubejselectrodynamics.plugin;

import com.core.kubejselectrodynamics.block.batterybox.BlockBatteryBoxBuilder;
import com.core.kubejselectrodynamics.block.motor.ac.BlockMotorACBuilder;
import com.core.kubejselectrodynamics.block.motor.dc.BlockMotorDCBuilder;
import com.core.kubejselectrodynamics.block.radioactive.BlockRadioactiveBuilder;
import com.core.kubejselectrodynamics.block.storage.gastank.BlockGasTankBuilder;
import com.core.kubejselectrodynamics.block.storage.tank.BlockTankBuilder;
import com.core.kubejselectrodynamics.item.battery.ItemBatteryBuilder;
import com.core.kubejselectrodynamics.item.canister.ItemCanisterBuilder;
import com.core.kubejselectrodynamics.item.fuelrod.ItemFuelRodBuilder;
import com.core.kubejselectrodynamics.item.gastankinsulation.ItemGasInsulatorBuilder;
import com.core.kubejselectrodynamics.item.portable_cylinder.ItemPortableCylinderBuilder;
import com.core.kubejselectrodynamics.item.radioactive.ItemRadioactiveBuilder;
import com.core.kubejselectrodynamics.plugin.recipe.schema.*;
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
    public ResourceLocation GetRecipeType(ModHolder holder, String type) {
        return new ResourceLocation(holder.mod, type);
    }

    public void register(RegisterRecipeSchemasEvent event, ModHolder holder, String name, RecipeSchema schema) {
        if (!Platform.isModLoaded(holder.mod)) {
            return;
        }
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
        RegistryInfo.ITEM.addType("electrodynamics:gasinsulation", ItemGasInsulatorBuilder.class, ItemGasInsulatorBuilder::new, false);

        // BLOCK
        RegistryInfo.BLOCK.addType("electrodynamics:batterybox", BlockBatteryBoxBuilder.class, BlockBatteryBoxBuilder::new, false);
        RegistryInfo.BLOCK.addType("electrodynamics:tank", BlockTankBuilder.class, BlockTankBuilder::new, false);
        RegistryInfo.BLOCK.addType("electrodynamics:gastank", BlockGasTankBuilder.class, BlockGasTankBuilder::new, false);

        // DYNAMIC ELECTRICITY
        if (Platform.isModLoaded(ModHolder.DYNAMICELECTRICITY.mod)) {
            RegistryInfo.BLOCK.addType("dynamicelectricity:motordc", BlockMotorDCBuilder.class, BlockMotorDCBuilder::new, false);
            RegistryInfo.BLOCK.addType("dynamicelectricity:motorac", BlockMotorACBuilder.class, BlockMotorACBuilder::new, false);
        }

        // NUCLEAR SCIENCE
        if (Platform.isModLoaded(ModHolder.NUCLEARSCIENCE.mod)) {
            RegistryInfo.BLOCK.addType("nuclearscience:radioactive", BlockRadioactiveBuilder.class, BlockRadioactiveBuilder::new, false);
            RegistryInfo.ITEM.addType("nuclearscience:radioactive", ItemRadioactiveBuilder.class, ItemRadioactiveBuilder::new, false);
            RegistryInfo.ITEM.addType("nuclearscience:fuelrod", ItemFuelRodBuilder.class, ItemFuelRodBuilder::new, false);
        }
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        // Electrodynamics
        register(event, ModHolder.ELECTRODYNAMICS, "energized_alloyer", ItemToItem.ALLOYER_SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "lathe", ItemToItem.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "mineral_crusher", ItemToItem.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "mineral_grinder", ItemToItem.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "oxidation_furnace", ItemToItem.OXIDIZER_FURNACE_SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "reinforced_alloyer", ItemToItem.ALLOYER_SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "wire_mill", ItemToItem.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "chemical_crystallizer", FluidToItem.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "chemical_mixer", FluidItemToFluid.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "fermentation_plant", FluidItemToFluid.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "mineral_washer", FluidItemToFluid.SCHEMA);
        register(event, ModHolder.ELECTRODYNAMICS, "electrolytic_separator", FluidToGas.SCHEMA);

        // Blastcraft
        register(event, ModHolder.BLASTCRAFT, "blast_compressor", ItemToItem.SCHEMA);

        // Nuclear Science
        register(event, ModHolder.NUCLEARSCIENCE, "chemical_extractor", FluidItemToItem.SCHEMA);
        register(event, ModHolder.NUCLEARSCIENCE, "msrfuel_preprocessor", FluidItemToItem.MSRFUEL_PREPROCESSOR_SCHEMA);
        register(event, ModHolder.NUCLEARSCIENCE, "radioactive_processor", FluidItemToItem.SCHEMA);
        register(event, ModHolder.NUCLEARSCIENCE, "fission_reactor", ItemToItem.FISSION_REACTOR_SCHEMA);
        register(event, ModHolder.NUCLEARSCIENCE, "fuel_reprocessor", ItemToItem.SCHEMA);
        register(event, ModHolder.NUCLEARSCIENCE, "nuclear_boiler", FluidItemToGas.SCHEMA);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
        event.add("ElectrodynamicsFluid", ElectroFluidWrapper.class);
        event.add("ElectrodynamicsGas", ElectroGasWrapper.class);
    }

    enum ModHolder {
        ELECTRODYNAMICS("electrodynamics"),
        BLASTCRAFT("blastcraft"),
        DYNAMICELECTRICITY("dynamicelectricity"),
        NUCLEARSCIENCE("nuclearscience");

        public final String mod;
        ModHolder(String name) {
            this.mod = name;
        }
    }
}
