package com.core.kubejselectrodynamics.block.gaspipe;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.builder.CableBuilderUtil;
import com.core.kubejselectrodynamics.util.builder.PipeCableBuilderUtil;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockGasPipeBuilder extends BlockBuilder {
    public static final List<BlockGasPipeBuilder> BUILDERS = new ArrayList<>();
    public static final SubtypeGasPipe[] POSSIBLE_SUBTYPES = new SubtypeGasPipe[] {
            SubtypeGasPipe.UNINSULATEDCOPPER
    };
    private double maxTransfer = SubtypeGasPipe.UNINSULATEDCOPPER.maxTransfer;
    private double heatLoss = SubtypeGasPipe.PipeMaterial.COPPER.heatLoss;
    private int maxPressure = SubtypeGasPipe.PipeMaterial.COPPER.maxPressure;
    private boolean corrodedByAcid = false;
    private Component materialName = Component.translatable("tooltip.kubejselectrodynamics.gaspipe.defaultname");

    public BlockGasPipeBuilder(ResourceLocation i) {
        super(i);
        textures.keySet().clear();
        BUILDERS.add(this);
    }

    @Info("Sets the maximum transfer per tick, as a double.")
    public BlockGasPipeBuilder maxTransfer(double maxTransfer) {
        this.maxTransfer = maxTransfer;
        return this;
    }

    @Info("Sets the heat loss, as a double.")
    public BlockGasPipeBuilder heatLoss(double heatLoss) {
        this.heatLoss = heatLoss;
        return this;
    }

    @Info("Sets the heat loss, as an integer.")
    public BlockGasPipeBuilder maxPressure(int maxPressure) {
        this.maxPressure = maxPressure;
        return this;
    }

    @Info("Sets the pipe to be corrodable by acid.")
    public BlockGasPipeBuilder corrodedByAcid() {
        this.corrodedByAcid = true;
        return this;
    }

    @Info("Sets the pipe's material. Only affects the tooltip.")
    public BlockGasPipeBuilder pipeMaterial(Component materialName) {
        this.materialName = materialName;
        return this;
    }

    @HideFromJS
    public double getMaxTransfer() {
        return maxTransfer;
    }

    @HideFromJS
    public boolean isInsulationFlammable() {
        return false;
    }

    @HideFromJS
    public double getHeatLoss() {
        return heatLoss;
    }

    @HideFromJS
    public double getEffectiveHeatLoss() {
        return heatLoss;
    }

    @HideFromJS
    public int getMaxPressure() {
        return maxPressure;
    }

    @HideFromJS
    public boolean isCorrodedByAcid() {
        return corrodedByAcid;
    }

    @HideFromJS
    public Component getMaterialName() {
        return materialName;
    }

    @HideFromJS
    public SubtypeGasPipe getPipe() {
        return POSSIBLE_SUBTYPES[0];
    }

    @HideFromJS
    public Block getBurnt() {
        return get();
    }

    @Override
    protected BlockItemBuilder getOrCreateItemBuilder() {
        if (itemBuilder == null) {
            itemBuilder = new BlockItemGasPipeBuilder(id, this);
        }
        return itemBuilder;
    }

    @Override
    public void generateBlockModelJsons(AssetJsonGenerator generator) {
        PipeCableBuilderUtil.generatePositionModels(generator, id, textures.deepCopy());
        CableBuilderUtil.generateBlockModel(generator, id);
    }

    @Override
    public Block createObject() {
        CustomBlockGasPipe block = new CustomBlockGasPipe(this, TileRegister.GAS_PIPE_TYPE.getSupplier(), createProperties());
        TileRegister.GAS_PIPE_TYPE.valid(block);
        return block;
    }
}
