package com.core.kubejselectrodynamics.block.electroblocks;

import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import electrodynamics.prefab.tile.IWrenchable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GenericRotatableWrenchableEntityBlock extends GenericRotatableEntityBlock implements IWrenchable {
    public GenericRotatableWrenchableEntityBlock(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, @NotNull HorizontalDirectionalBlockBuilder blockBuilder) {
        super(supplier, blockBuilder);
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
