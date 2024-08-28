package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.builder.CableBuilderUtil;
import com.core.kubejselectrodynamics.util.builder.WireCableBuilderUtil;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        textures.keySet().clear();
    }

    public BlockWireBuilder(BlockWireMainBuilder main, ResourceLocation location, SubtypeWire.WireColor wireColor, SubtypeWire.WireClass wireClass) {
        super(location);
        this.wireResistance = main.getWireResistance();
        this.wireAmpacity = main.getWireAmpacity();
        this.wireColor = wireColor;
        this.wireClass = wireClass;
        this.bareWireBuilder = main;
        builders.add(this);
        textures.keySet().clear();
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
        WireCableBuilderUtil.generatePositionModels(generator, id, textures, wireClass, bareWireBuilder);
        CableBuilderUtil.generateBlockModel(generator, id);
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

    private static int clamp(int v, int a, int b) {
        return Math.max(a, Math.min(v, b));
    }

    private static int colorClamp(int v) {
        return clamp(v, 0, 255);
    }
}
