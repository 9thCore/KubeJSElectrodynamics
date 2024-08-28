package com.core.kubejselectrodynamics.util.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import electrodynamics.common.block.subtype.SubtypeWire;
import net.minecraft.resources.ResourceLocation;

public class CableBuilderUtil {
    public static void generateBlockModel(AssetJsonGenerator generator, ResourceLocation id) {
        String parentSide = Position.SIDE.getRelativeModelID(id).toString();
        String parentNone = Position.NONE.getRelativeModelID(id).toString();

        JsonObject model = new JsonObject();
        model.add("inventory", getModel(parentSide));
        model.add("none", getModel(parentNone));
        model.add("wire", getModel(parentSide));
        model.addProperty("loader", "electrodynamics:electrodynamicscableloader");
        model.addProperty("render_type", "minecraft:cutout");
        model.addProperty("parent", "minecraft:block/cube");
        generator.json(new ResourceLocation(id.getNamespace(), "models/block/" + id.getPath()), model);
    }

    protected static JsonElement getModel(String parent) {
        JsonObject element = new JsonObject();
        element.addProperty("parent", parent);
        return element;
    }

    public enum Position {
        NONE(
                // Bare, Insulated, Thick, Ceramic, Logistical
                new String[] {"", "insulationwool_center", "insulationwool_center", "insulationceramic_center_base", "logisticswireinsulation_center"},
                "logisticswireredstone_center"
        ),
        SIDE(
                // Side texture ceramic insulation has two keys, so don't bother here
                new String[] {"", "insulationwool", "insulationwool", "", "logisticswireinsulation_side"},
                "logisticswireredstone_side"
        );

        private final String[] insulationTextures;
        private final String redstoneTexture;

        Position(String[] insulationTextures, String redstoneTexture) {
            this.insulationTextures = insulationTextures;
            this.redstoneTexture = redstoneTexture;
        }

        public ResourceLocation getRelativeModelID(ResourceLocation id) {
            return new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_" + BlockBuilderUtil.getSerializedName(this));
        }

        public ResourceLocation getAbsoluteModelID(ResourceLocation id) {
            return new ResourceLocation(id.getNamespace(), "models/" + getRelativeModelID(id).getPath());
        }

        public String getInsulation(SubtypeWire.WireClass wireClass) {
            return String.format("electrodynamics:block/wire/%s", insulationTextures[wireClass.ordinal()]);
        }

        public String getRedstoneTexture() {
            return String.format("electrodynamics:block/wire/%s", redstoneTexture);
        }

        public String getBlockParent(SubtypeWire.WireClass wireClass) {
            String wire = switch (wireClass) {
                case BARE -> "wire";
                case INSULATED -> "insulatedwire";
                case THICK -> "highlyinsulatedwire";
                case CERAMIC -> "ceramicinsulatedwire";
                case LOGISTICAL -> "logisticswire";
            };
            return String.format("electrodynamics:parent/%s_%s", wire, BlockBuilderUtil.getSerializedName(this));
        }

        public String getFluidPipeBlockParent() {
            return String.format("electrodynamics:parent/pipe_%s", BlockBuilderUtil.getSerializedName(this));
        }
    }
}
