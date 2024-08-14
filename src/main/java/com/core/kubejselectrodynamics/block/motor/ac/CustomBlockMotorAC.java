package com.core.kubejselectrodynamics.block.motor.ac;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableEntityBlock;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class CustomBlockMotorAC extends GenericRotatableEntityBlock {
    public CustomBlockMotorAC(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, BlockMotorACBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @Nonnull
    @Override
    public BlockMotorACBuilder kjs$getBlockBuilder() {
        return (BlockMotorACBuilder) blockBuilder;
    }
}
