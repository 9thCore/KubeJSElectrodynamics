package com.core.kubejselectrodynamics.block.storage.tank;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableWrenchableEntityBlock;
import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class CustomBlockTank extends GenericRotatableWrenchableEntityBlock {
    public CustomBlockTank(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, @NotNull HorizontalDirectionalBlockBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @NotNull
    @Override
    public BlockTankBuilder kjs$getBlockBuilder() {
        return (BlockTankBuilder) blockBuilder;
    }
}
