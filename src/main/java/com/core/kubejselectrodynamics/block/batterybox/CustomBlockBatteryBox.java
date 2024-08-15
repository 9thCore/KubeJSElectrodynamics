package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableEntityBlock;
import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableWrenchableEntityBlock;
import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;

public class CustomBlockBatteryBox extends GenericRotatableWrenchableEntityBlock {
    protected CustomBlockBatteryBox(BlockEntityType.BlockEntitySupplier<BlockEntity> supplier, HorizontalDirectionalBlockBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @Override
    public @Nonnull BlockBatteryBoxBuilder kjs$getBlockBuilder() {
        return (BlockBatteryBoxBuilder) blockBuilder;
    }
}
