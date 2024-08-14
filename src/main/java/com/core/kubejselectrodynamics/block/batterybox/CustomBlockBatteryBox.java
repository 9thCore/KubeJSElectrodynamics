package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.electroblocks.GenericRotatableEntityBlock;
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

public class CustomBlockBatteryBox extends GenericRotatableEntityBlock implements IWrenchable {
    protected CustomBlockBatteryBox(BlockEntityType.BlockEntitySupplier<BlockEntity> supplier, HorizontalDirectionalBlockBuilder blockBuilder) {
        super(supplier, blockBuilder);
    }

    @Override
    public @Nonnull BlockBatteryBoxBuilder kjs$getBlockBuilder() {
        return (BlockBatteryBoxBuilder) blockBuilder;
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void onRotate(ItemStack itemStack, BlockPos blockPos, Player player) {
        if (player.level().getBlockState(blockPos).hasProperty(FACING)) {
            BlockState state = this.rotate(player.level().getBlockState(blockPos), Rotation.CLOCKWISE_90);
            player.level().setBlockAndUpdate(blockPos, state);
        }
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void onPickup(ItemStack itemStack, BlockPos blockPos, Player player) {
        Level world = player.level();
        world.destroyBlock(blockPos, true, player);
    }
}
