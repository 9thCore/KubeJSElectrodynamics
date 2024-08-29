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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class BlockFluidPipeBuilder extends BlockBuilder {
    private long maxTransfer = SubtypeFluidPipe.copper.maxTransfer;

    public BlockFluidPipeBuilder(ResourceLocation i) {
        super(i);
        textures.keySet().clear();
    }

    @Info("Sets the maximum transfer per tick, as a long.")
    public BlockFluidPipeBuilder maxTransfer(long maxTransfer) {
        this.maxTransfer = maxTransfer;
        return this;
    }

    @HideFromJS
    public long getMaxTransfer() {
        return maxTransfer;
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
