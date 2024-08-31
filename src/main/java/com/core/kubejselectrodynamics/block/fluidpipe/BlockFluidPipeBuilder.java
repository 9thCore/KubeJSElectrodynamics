package com.core.kubejselectrodynamics.block.fluidpipe;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.builder.CableBuilderUtil;
import com.core.kubejselectrodynamics.util.builder.PipeCableBuilderUtil;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.block.BlockItemBuilder;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import electrodynamics.common.block.subtype.SubtypeFluidPipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockFluidPipeBuilder extends BlockBuilder {
    public static final List<BlockFluidPipeBuilder> BUILDERS = new ArrayList<>();
    private long maxTransfer = SubtypeFluidPipe.copper.maxTransfer;
    private Component materialName = Component.translatable("tooltip.kubejselectrodynamics.fluidpipe.defaultname");

    public BlockFluidPipeBuilder(ResourceLocation i) {
        super(i);
        textures.keySet().clear();
        BUILDERS.add(this);
    }

    @Info("Sets the maximum transfer per tick, as a long.")
    public BlockFluidPipeBuilder maxTransfer(long maxTransfer) {
        this.maxTransfer = maxTransfer;
        return this;
    }

    @Info("Sets the pipe's material. Only affects the guidebook.")
    public BlockFluidPipeBuilder pipeMaterial(Component materialName) {
        this.materialName = materialName;
        return this;
    }

    @HideFromJS
    public long getMaxTransfer() {
        return maxTransfer;
    }

    @HideFromJS
    public Component getMaterialName() {
        return materialName;
    }

    @Override
    protected BlockItemBuilder getOrCreateItemBuilder() {
        if (itemBuilder == null) {
            itemBuilder = new BlockItemFluidPipeBuilder(id, this);
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
        CustomBlockFluidPipe block = new CustomBlockFluidPipe(this, TileRegister.FLUID_PIPE_TYPE.getSupplier(), createProperties());
        TileRegister.FLUID_PIPE_TYPE.valid(block);
        return block;
    }
}
