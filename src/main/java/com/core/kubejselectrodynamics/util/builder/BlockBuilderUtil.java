package com.core.kubejselectrodynamics.util.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.block.BlockBuilder;

import java.util.Locale;
import java.util.Map;

public class BlockBuilderUtil {
    // this sucks
    public static void copyProperties(BlockBuilder from, BlockBuilder builder) {
        builder.soundType = from.soundType;
        builder.mapColorFn = from.mapColorFn;
        builder.hardness = from.hardness;
        builder.resistance = from.resistance;
        builder.lightLevel = from.lightLevel;
        builder.opaque = from.opaque;
        builder.fullBlock = from.fullBlock;
        builder.requiresTool = from.requiresTool;
        builder.renderType = from.renderType;
        builder.tint = from.tint;
        builder.model = from.model;
        builder.customShape = from.customShape;
        builder.noCollision = from.noCollision;
        builder.notSolid = from.notSolid;
        builder.slipperiness = from.slipperiness;
        builder.speedFactor = from.speedFactor;
        builder.jumpFactor = from.jumpFactor;
        builder.randomTickCallback = from.randomTickCallback;
        builder.lootTable = from.lootTable;
        builder.blockstateJson = from.blockstateJson;
        builder.modelJson = from.modelJson;
        builder.noValidSpawns = from.noValidSpawns;
        builder.suffocating = from.suffocating;
        builder.viewBlocking = from.viewBlocking;
        builder.redstoneConductor = from.redstoneConductor;
        builder.transparent = from.transparent;
        builder.instrument = from.instrument;
        builder.blockStateProperties = from.blockStateProperties;
        builder.defaultStateModification = from.defaultStateModification;
        builder.placementStateModification = from.placementStateModification;
        builder.canBeReplacedFunction = from.canBeReplacedFunction;
        builder.stepOnCallback = from.stepOnCallback;
        builder.fallOnCallback = from.fallOnCallback;
        builder.afterFallenOnCallback = from.afterFallenOnCallback;
        builder.explodedCallback = from.explodedCallback;
        builder.rotateStateModification = from.rotateStateModification;
        builder.mirrorStateModification = from.mirrorStateModification;
        builder.rightClick = from.rightClick;
        for (Map.Entry<String, JsonElement> entry : from.textures.entrySet()) {
            builder.textures.add(entry.getKey(), entry.getValue());
        }
    }

    public static void addTextureIfMissing(JsonObject textures, String id, String value) {
        if (!textures.has(id)) {
            textures.addProperty(id, value);
        }
    }

    public static String getSerializedName(Enum<?> value) {
        return value.name().toLowerCase(Locale.ENGLISH);
    }

    public static String getTextureOrDefault(JsonObject textures, String id, String defaultValue) {
        if (textures.has(id)) {
            return textures.get(id).getAsString();
        }
        return defaultValue;
    }
}
