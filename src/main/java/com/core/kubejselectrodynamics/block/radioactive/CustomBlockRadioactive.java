package com.core.kubejselectrodynamics.block.radioactive;

import dev.latvian.mods.kubejs.block.custom.BasicBlockJS;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CustomBlockRadioactive extends BasicBlockJS.WithEntity {
    private final BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier;

    public CustomBlockRadioactive(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, BlockRadioactiveBuilder builder) {
        super(builder);
        this.supplier = supplier;
    }

    @Override
    public BlockRadioactiveBuilder kjs$getBlockBuilder() {
        return ((BlockRadioactiveBuilder) blockBuilder);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return supplier.create(pPos, pState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (l, pos, s, tile) -> {
            if (tile instanceof GenericTile generic) {
                if (generic.getComponent(IComponentType.Tickable) instanceof ComponentTickable tickable) {
                    tickable.performTick(l);
                }
            }
        };
    }
}
