package com.core.kubejselectrodynamics.block.fluidpipe;

import com.core.kubejselectrodynamics.util.builder.BlockBuilderUtil;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import net.minecraft.resources.ResourceLocation;

public class BlockItemFluidPipeBuilder extends BlockItemBuilder {
    private final BlockFluidPipeBuilder builder;
    public BlockItemFluidPipeBuilder(ResourceLocation i, BlockFluidPipeBuilder builder) {
        super(i);
        this.builder = builder;
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
}
