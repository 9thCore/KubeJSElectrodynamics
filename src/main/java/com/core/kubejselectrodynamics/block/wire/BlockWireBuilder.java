package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BlockWireBuilder extends BlockBuilder {
    public static final SubtypeWire[] POSSIBLE_SUBTYPES = new SubtypeWire[] {
            SubtypeWire.superconductive,
            SubtypeWire.ceramicinsulatedsuperconductiveblack,
            SubtypeWire.highlyinsulatedsuperconductiveblack,
            SubtypeWire.insulatedsuperconductiveblack,
            SubtypeWire.logisticssuperconductiveblack
    };
    public static final List<BlockWireBuilder> builders = new ArrayList<>();
    private final SubtypeWire.WireColor wireColor;
    private final SubtypeWire.WireClass wireClass;
    private final BlockWireBuilder bareWireBuilder;
    private Color wireTint = null;
    private double wireResistance = SubtypeWire.Conductor.COPPER.resistance;
    private long wireAmpacity = SubtypeWire.Conductor.COPPER.ampacity;

    public BlockWireBuilder(ResourceLocation location, SubtypeWire.WireColor wireColor, SubtypeWire.WireClass wireClass) {
        super(location);
        this.wireColor = wireColor;
        this.wireClass = wireClass;
        this.bareWireBuilder = this;
        builders.add(this);
    }

    public BlockWireBuilder(BlockWireMainBuilder main, ResourceLocation location, SubtypeWire.WireColor wireColor, SubtypeWire.WireClass wireClass) {
        super(location);
        this.wireColor = wireColor;
        this.wireResistance = main.getWireResistance();
        this.wireAmpacity = main.getWireAmpacity();
        this.bareWireBuilder = main;
        this.wireClass = wireClass;
        builders.add(this);
    }

    @Info("Sets the resistance of the wire, as a double.")
    public BlockWireBuilder resistance(double resistance) {
        this.wireResistance = resistance;
        return this;
    }

    @Info("Sets the ampacity of the wire, as a long.")
    public BlockWireBuilder ampacity(long ampacity) {
        this.wireAmpacity = ampacity;
        return this;
    }

    @Info("Sets the ampacity of the  wire to the maximum possible value, not representable in JavaScript. Results in same ampacity as Superconductive, or practically infinite.")
    public BlockWireBuilder maximumAmpacity() {
        this.wireAmpacity = Long.MAX_VALUE;
        return this;
    }

    @Info("Sets the tint of the wire in item form, as ints. Defaults to white (255, 255, 255, 255).")
    public BlockWireBuilder wireTint(int r, int g, int b, int a) {
        wireTint = new Color(colorClamp(r), colorClamp(g), colorClamp(b), colorClamp(a));
        return this;
    }

    @HideFromJS
    public CustomBlockWire getBlockWire() {
        return (CustomBlockWire) get();
    }

    @HideFromJS
    public boolean isBare() {
        return wireClass == SubtypeWire.WireClass.BARE;
    }

    @HideFromJS
    public Block getBare() {
        return bareWireBuilder.get();
    }

    @HideFromJS
    public SubtypeWire.WireColor getWireColor() {
        return wireColor;
    }

    @HideFromJS
    public Color getWireTint() {
        return wireTint;
    }

    @HideFromJS
    public boolean hasBaseTexture() {
        return wireTint != null;
    }

    /**
     * @return A variant of a Superconductive wire. Superconductive ensures it won't blow up, allowing custom logic
     */
    @HideFromJS
    public SubtypeWire getSubtype() {
        return switch (wireClass) {
            case BARE -> SubtypeWire.superconductive;
            case INSULATED -> SubtypeWire.insulatedsuperconductiveblack;
            case THICK -> SubtypeWire.highlyinsulatedsuperconductiveblack;
            case CERAMIC -> SubtypeWire.ceramicinsulatedsuperconductiveblack;
            case LOGISTICAL -> SubtypeWire.logisticssuperconductiveblack;
        };
    }

    @HideFromJS
    public double getWireResistance() {
        return wireResistance;
    }

    @HideFromJS
    public long getWireAmpacity() {
        return wireAmpacity;
    }

    @Override
    protected BlockItemBuilder getOrCreateItemBuilder() {
        if (itemBuilder == null) {
            itemBuilder = new BlockItemWireBuilder(id, this);
        }
        return itemBuilder;
    }

    @Override
    protected void generateBlockModelJsons(AssetJsonGenerator generator) {
        for (Direction direction : Direction.values()) {
            // We don't need these
            textures.remove(getSerializedName(direction));
        }
        textures.remove("particle");

        for (Position position : Position.values()) {
            JsonObject model = new JsonObject();
            model.addProperty("parent", position.getBlockParent(wireClass));
            model.addProperty("render_type", "minecraft:cutout");
            JsonObject texturesCopy = textures.deepCopy();
            ensureRequiredTextures(texturesCopy, position);
            model.add("textures", texturesCopy);
            generator.json(position.getAbsoluteModelID(id), model);
        }

        String parentSide = Position.SIDE.getRelativeModelID(id).toString();
        String parentNone = Position.NONE.getRelativeModelID(id).toString();

        JsonObject model = new JsonObject();
        model.add("inventory", getModel(parentSide));
        model.add("none", getModel(parentNone));
        model.add("wire", getModel(parentSide));
        model.addProperty("loader", "electrodynamics:electrodynamicscableloader");
        model.addProperty("render_type", "minecraft:cutout");
        model.addProperty("parent", "minecraft:block/cube");
        generator.json(newID("models/block/", ""), model);
    }

    protected JsonElement getModel(String parent) {
        JsonObject element = new JsonObject();
        element.addProperty("parent", parent);
        return element;
    }

    @Override
    public Block createObject() {
        CustomBlockWire block;
        if (wireClass.conductsRedstone) {
            block = new CustomBlockLogisticalWire(this, TileRegister.LOGISTICAL_WIRE_TYPE.getSupplier());
            TileRegister.LOGISTICAL_WIRE_TYPE.valid(block);
        } else {
            block = new CustomBlockWire(this, TileRegister.WIRE_TYPE.getSupplier());
            TileRegister.WIRE_TYPE.valid(block);
        }
        return block;
    }

    /**
     * Ensure all required textures exist in the textures object
     */
    private void ensureRequiredTextures(JsonObject textures, Position position) {
        if (position == Position.NONE) {
            addTextureIfMissing(textures, "conductor", String.format("%s:block/%s", bareWireBuilder.id.getNamespace(), bareWireBuilder.id.getPath()));
            // Hack for the logistical model
            if (wireClass == SubtypeWire.WireClass.LOGISTICAL) {
                textures.addProperty("conductor", getTextureOrDefault(textures, "logistical_conductor", "conductor"));
            }
        }

        // Insulation
        if (wireClass == SubtypeWire.WireClass.CERAMIC && position == Position.SIDE) {
            // Different textures for side...
            addTextureIfMissing(textures, "insulationbase", "electrodynamics:block/wire/insulationceramic_base");
            addTextureIfMissing(textures, "insulationcolor", "electrodynamics:block/wire/insulationceramic");
            addTextureIfMissing(textures, "particle", "#insulationcolor");
        }
        else if (!isBare()) {
            addTextureIfMissing(textures, "insulation", position.getInsulation(wireClass));
            addTextureIfMissing(textures, "particle", "#insulation");
        }

        if (wireClass == SubtypeWire.WireClass.LOGISTICAL) {
            addTextureIfMissing(textures, "redstone", position.getRedstoneTexture());
        }

        addTextureIfMissing(textures, "particle", "#conductor");
    }

    public static void addTextureIfMissing(JsonObject textures, String id, String value) {
        if (!textures.has(id)) {
            textures.addProperty(id, value);
        }
    }

    private static String getTextureOrDefault(JsonObject textures, String id, String defaultID) {
        if (textures.has(id)) {
            return textures.get(id).getAsString();
        }
        return textures.get(defaultID).getAsString();
    }

    protected static String getSerializedName(Enum<?> color) {
        return color.name().toLowerCase(Locale.ENGLISH);
    }

    private static int clamp(int v, int a, int b) {
        return Math.max(a, Math.min(v, b));
    }

    private static int colorClamp(int v) {
        return clamp(v, 0, 255);
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
            return new ResourceLocation(id.getNamespace(), "block/" + id.getPath() + "_" + getSerializedName(this));
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
            return String.format("electrodynamics:parent/%s_%s", wire, getSerializedName(this));
        }
    }
}
