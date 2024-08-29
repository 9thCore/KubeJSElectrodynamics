package com.core.kubejselectrodynamics.util.builder;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import net.minecraft.resources.ResourceLocation;

public class PipeCableBuilderUtil {
    public static void generatePositionModels(AssetJsonGenerator generator, ResourceLocation id, JsonObject textures) {
        textures.remove("layer0"); // We don't need the item's texture here
        for (CableBuilderUtil.Position position : CableBuilderUtil.Position.values()) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", position.getFluidPipeBlockParent());
            model.addProperty("render_type", "minecraft:cutout");
            BlockBuilderUtil.addTextureIfMissing(textures, "texture", String.format("%s:block/%s", id.getNamespace(), id.getPath()));
            BlockBuilderUtil.addTextureIfMissing(textures, "particle", "#texture");
            model.add("textures", textures);
            generator.json(position.getAbsoluteModelID(id), model);
        }
    }
}
