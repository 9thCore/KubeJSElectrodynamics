package com.core.kubejselectrodynamics.block.motor.dc;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;

public class CustomBlockMotorDC extends GenericRotatableEntityBlock {
    public CustomBlockMotorDC(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, BlockMotorDCBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @Nonnull
    @Override
    public BlockMotorDCBuilder kjs$getBlockBuilder() {
        return (BlockMotorDCBuilder) blockBuilder;
    }
}
