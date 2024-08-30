package com.core.kubejselectrodynamics.block.gaspipe;

import com.core.kubejselectrodynamics.item.CustomItemTab;
import com.core.kubejselectrodynamics.util.builder.BlockBuilderUtil;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockItemGasPipeBuilder extends BlockItemBuilder {
    private final BlockGasPipeBuilder builder;
    public BlockItemGasPipeBuilder(ResourceLocation i, BlockGasPipeBuilder builder) {
        super(i);
        this.builder = builder;
    }

    public Block getBlock() {
        return builder.get();
    }

    @HideFromJS
    public Component getTranslatedName() {
        return builder.getMaterialName();
    }

    @HideFromJS
    public int getMaxPressure() {
        return builder.getMaxPressure();
    }

    // Use basic item model
    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        if (modelJson != null) {
            generator.json(AssetJsonGenerator.asItemModelLocation(id), modelJson);
            return;
        }

        generator.itemModel(id, m -> {
            // Set "layer0" to either (in order of importance) the block's "layer0" texture or the item's default texture location (mod:item/id)
            String layer0 = BlockBuilderUtil.getTextureOrDefault(builder.textures, "layer0", newID("item/", "").toString());
            textureJson.addProperty("layer0", layer0);
            m.parent("minecraft:item/generated");
            m.textures(textureJson);
        });
    }

    @Override
    public Item createObject() {
        return new CustomBlockItemGasPipe(this, createItemProperties(), CustomItemTab.TAB);
    }
}
