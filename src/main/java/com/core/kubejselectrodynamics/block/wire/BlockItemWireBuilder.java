package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.item.CustomItemTab;
import com.core.kubejselectrodynamics.util.builder.BlockBuilderUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BlockItemWireBuilder extends BlockItemBuilder {
    public static final List<BlockItemWireBuilder> builders = new ArrayList<>();
    public final BlockWireBuilder blockBuilder;

    public BlockItemWireBuilder(ResourceLocation location, BlockWireBuilder builder) {
        super(location);
        this.blockBuilder = builder;
        builders.add(this);
    }

    @Override
    public void generateAssetJsons(AssetJsonGenerator generator) {
        JsonObject model = new JsonObject();
        if (blockBuilder.isBare()) {
            JsonObject display = new JsonObject();
            JsonArray scale = new JsonArray();
            scale.add(0.7D);
            scale.add(0.7D);
            scale.add(0.7D);
            JsonObject gui = new JsonObject();
            gui.add("scale", scale);
            display.add("gui", gui);
            model.add("display", display);
        }
        model.addProperty("parent", "minecraft:item/generated");
        SubtypeWire.WireClass wireClass = blockBuilder.getSubtype().wireClass;
        textureJson = blockBuilder.textures;
        if (blockBuilder.hasBaseTexture()) {
            BlockBuilderUtil.addTextureIfMissing(textureJson, "layer0", getLayer0Texture(wireClass));
            if (hasLayer1Texture(wireClass)) {
                BlockBuilderUtil.addTextureIfMissing(textureJson, "layer1", getLayer1Texture(wireClass));
            }
            if (hasLayer2Texture(wireClass)) {
                BlockBuilderUtil.addTextureIfMissing(textureJson, "layer2", getLayer2Texture(wireClass));
            }
        } else {
            BlockBuilderUtil.addTextureIfMissing(textureJson, "layer0",  newID("item/", "").toString());
        }
        model.add("textures", textureJson);
        generator.json(newID("models/item/", ""), model);
    }

    @HideFromJS
    public double getResistance() {
        return blockBuilder.getWireResistance();
    }

    @HideFromJS
    public long getAmpacity() {
        return blockBuilder.getWireAmpacity();
    }

    @Override
    public Item createObject() {
        return new CustomBlockItemWire(this, CustomItemTab.TAB);
    }

    public static String getLayer0Texture(SubtypeWire.WireClass wireClass) {
        String type = switch (wireClass) {
            case BARE -> "wire";
            case INSULATED -> "wireinsulated";
            case THICK -> "wirehighlyinsulated";
            case CERAMIC -> "wireceramicinsulated";
            case LOGISTICAL -> "wirelogistics";
        };
        return String.format("electrodynamics:item/wire/%ssilver", type);
    }

    public static boolean hasLayer1Texture(SubtypeWire.WireClass wireClass) {
        return switch (wireClass) {
            case INSULATED, THICK, CERAMIC, LOGISTICAL -> true;
            default -> false;
        };
    }

    public static String getLayer1Texture(SubtypeWire.WireClass wireClass) {
        return switch (wireClass) {
            case INSULATED -> "electrodynamics:item/wire/wireinsulatedcoil";
            case THICK -> "electrodynamics:item/wire/wirehighlyinsulatedcoil";
            case CERAMIC -> "electrodynamics:item/wire/wireceramicinsulatedcolortips";
            case LOGISTICAL -> "electrodynamics:item/wire/wirelogisticscoil";
            default -> "minecraft:item/barrier";
        };
    }

    public static boolean hasLayer2Texture(SubtypeWire.WireClass wireClass) {
        return switch (wireClass) {
            case LOGISTICAL, CERAMIC -> true;
            default -> false;
        };
    }

    public static String getLayer2Texture(SubtypeWire.WireClass wireClass) {
        return switch (wireClass) {
            case CERAMIC -> "electrodynamics:item/wire/wireceramicinsulatedcoil";
            case LOGISTICAL -> "electrodynamics:item/wire/wirelogisticsredstone";
            default -> "minecraft:item/barrier";
        };
    }
}
