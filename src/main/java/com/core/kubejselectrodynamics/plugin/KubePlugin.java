package com.core.kubejselectrodynamics.plugin;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.plugin.recipe.schema.ItemToItem;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import net.minecraft.resources.ResourceLocation;

public class KubePlugin extends KubeJSPlugin {
    public ResourceLocation GetRecipeType(String type) {
        return new ResourceLocation("electrodynamics", type);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        KubeJSElectrodynamics.LogInfo("ABC Registering recipes");
        event.register(GetRecipeType("energized_alloyer_recipe"), ItemToItem.SCHEMA);
    }
}
