package com.core.kubejselectrodynamics.util.builder;

import com.core.kubejselectrodynamics.block.wire.BlockWireBuilder;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.resources.ResourceLocation;

public class WireCableBuilderUtil {
    public static void generatePositionModels(AssetJsonGenerator generator, ResourceLocation id, JsonObject textures, SubtypeWire.WireClass wireClass, BlockWireBuilder bareWireBuilder) {
        for (CableBuilderUtil.Position position : CableBuilderUtil.Position.values()) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", position.getBlockParent(wireClass));
            model.addProperty("render_type", "minecraft:cutout");
            JsonObject texturesCopy = textures.deepCopy();
            ensureRequiredTextures(texturesCopy, position, bareWireBuilder, wireClass);
            model.add("textures", texturesCopy);
            generator.json(position.getAbsoluteModelID(id), model);
        }
    }

    static void ensureRequiredTextures(JsonObject textures, CableBuilderUtil.Position position, BlockWireBuilder bareWireBuilder, SubtypeWire.WireClass wireClass) {
        if (position == CableBuilderUtil.Position.NONE) {
            BlockBuilderUtil.addTextureIfMissing(textures, "conductor", String.format("%s:block/%s", bareWireBuilder.id.getNamespace(), bareWireBuilder.id.getPath()));
            // Hack for the logistical model
            if (wireClass == SubtypeWire.WireClass.LOGISTICAL) {
                textures.addProperty("conductor", BlockBuilderUtil.getTextureOrDefault(textures, "logistical_conductor", "conductor"));
            }
        }

        // Insulation
        if (wireClass == SubtypeWire.WireClass.CERAMIC && position == CableBuilderUtil.Position.SIDE) {
            // Different textures for side...
            BlockBuilderUtil.addTextureIfMissing(textures, "insulationbase", "electrodynamics:block/wire/insulationceramic_base");
            BlockBuilderUtil.addTextureIfMissing(textures, "insulationcolor", "electrodynamics:block/wire/insulationceramic");
            BlockBuilderUtil.addTextureIfMissing(textures, "particle", "#insulationcolor");
        }
        else if (wireClass != SubtypeWire.WireClass.BARE) {
            BlockBuilderUtil.addTextureIfMissing(textures, "insulation", position.getInsulation(wireClass));
            BlockBuilderUtil.addTextureIfMissing(textures, "particle", "#insulation");
        }

        if (wireClass == SubtypeWire.WireClass.LOGISTICAL) {
            BlockBuilderUtil.addTextureIfMissing(textures, "redstone", position.getRedstoneTexture());
        }

        BlockBuilderUtil.addTextureIfMissing(textures, "particle", "#conductor");
    }
}
