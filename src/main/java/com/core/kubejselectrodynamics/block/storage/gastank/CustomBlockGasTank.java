package com.core.kubejselectrodynamics.block.storage.gastank;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableWrenchableEntityBlock;
import com.core.kubejselectrodynamics.block.storage.tank.BlockTankBuilder;
import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class CustomBlockGasTank extends GenericRotatableWrenchableEntityBlock {
    public CustomBlockGasTank(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, @NotNull HorizontalDirectionalBlockBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @NotNull
    @Override
    public BlockGasTankBuilder kjs$getBlockBuilder() {
        return (BlockGasTankBuilder) blockBuilder;
    }
}
