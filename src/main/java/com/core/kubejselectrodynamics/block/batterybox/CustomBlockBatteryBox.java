package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableWrenchableEntityBlock;
import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;

public class CustomBlockBatteryBox extends GenericRotatableWrenchableEntityBlock {
    protected CustomBlockBatteryBox(BlockEntityType.BlockEntitySupplier<?> supplier, HorizontalDirectionalBlockBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @Override
    public @Nonnull BlockBatteryBoxBuilder kjs$getBlockBuilder() {
        return (BlockBatteryBoxBuilder) blockBuilder;
    }
}
