package com.core.kubejselectrodynamics.item.canister;

import com.core.kubejselectrodynamics.item.CustomItemTab;
import com.core.kubejselectrodynamics.util.ItemUtils;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemCanisterBuilder extends ItemBuilder {
    private String emptyFluid = "minecraft:water";
    private int capacity = ItemCanister.MAX_FLUID_CAPACITY;
    private boolean fill = true;

    public ItemCanisterBuilder(ResourceLocation location) {
        super(location);
        maxStackSize = 1;
    }

    @Info("Sets the maximum capacity of this canister, as an integer.")
    public ItemCanisterBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @Info("""
        Set the fluid shown in the item when it is empty.
        Base Electrodynamics uses "minecraft:water", while "minecraft:empty" is clear.
        """)
    public ItemCanisterBuilder emptyTexture(String emptyTexture) {
        this.emptyFluid = emptyTexture;
        return this;
    }

    @Info("Prevents adding any prefilled stacks of this item to the \"KubeJS Electrodynamics Fills\" tab.")
    public ItemCanisterBuilder dontFillTab() {
        fill = false;
        return this;
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        if (modelJson != null) {
            generator.json(AssetJsonGenerator.asItemModelLocation(id), modelJson);
            return;
        }

        // Custom model
        if (!parentModel.isEmpty()) {
            generator.itemModel(id, m -> {
                m.parent(parentModel);
                if (textureJson.size() == 0) {
                    texture("layer0", newID("item/", "").toString());
                }
                m.textures(textureJson);
            });
            return;
        }

        if (!textureJson.has("base")) {
            textureJson.addProperty("base", textureJson.has("layer0") ? textureJson.get("layer0").getAsString() : newID("item/", "").toString());
        }
        if (!textureJson.has("fluid")) {
            textureJson.addProperty("fluid", textureJson.has("layer0") ? textureJson.get("layer0").getAsString() : newID("item/", "_fluid").toString());
        }

        generator.json(
                AssetJsonGenerator.asItemModelLocation(id),
                ItemUtils.fluidContainerModel(
                        textureJson,
                        emptyFluid,
                        true,
                        true
                )
        );
    }

    @Override
    public Item createObject() {
        CustomItemCanister item = new CustomItemCanister(
                createItemProperties(),
                CustomItemTab.TAB
        ) {
            @Override
            public int getCapacity() {
                return capacity;
            }
        };
        if(fill) {
            CustomItemTab.items.add(item);
        }
        return item;
    }
}
