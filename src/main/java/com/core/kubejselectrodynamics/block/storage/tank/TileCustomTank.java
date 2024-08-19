package com.core.kubejselectrodynamics.block.storage.tank;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.mixinterfaces.ComponentInterfaces;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.common.tile.pipelines.tanks.fluid.GenericTileFluidTank;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class TileCustomTank extends GenericTileFluidTank {
    public final BlockTankBuilder builder;

    public TileCustomTank(BlockPos pos, BlockState state) {
        this(TileRegister.TANK_TYPE.getBasicType(), pos, state, ((CustomBlockTank) state.getBlock()).kjs$getBlockBuilder());
    }

    public TileCustomTank(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, BlockTankBuilder builder) {
        super(tileEntityTypeIn, builder.getCapacity(), SubtypeMachine.tanksteel, pos, state);
        this.builder = builder;

        // Hacks
        ((ComponentInterfaces.IComponentContainerProvider) getComponent(IComponentType.ContainerProvider)).kjsElectro$setName(builder.getBuilderTranslationKey());

        ComponentFluidHandlerSimple fluidHandlerSimple = getComponent(IComponentType.FluidHandler);
        fluidHandlerSimple.setInputDirections(builder.getFluidInput());
        fluidHandlerSimple.setOutputDirections(builder.getFluidOutput());
    }

    public void tickServer(ComponentTickable tickable) {
        ComponentFluidHandlerSimple handler = getComponent(IComponentType.FluidHandler);
        FluidUtilities.drainItem(this, handler.toArray());
        FluidUtilities.fillItem(this, handler.toArray());
        for (Direction direction : Direction.values()) {
            if (!builder.getFluidOutputSet().contains(direction)) {
                continue;
            }
            FluidUtilities.outputToPipe(this, handler.toArray(), direction);
        }
    }

    public static class Render extends TileCustomTank {
        public Render(BlockPos pos, BlockState state) {
            super(TileRegister.TANK_TYPE.getRenderedType(), pos, state, ((CustomBlockTank) state.getBlock()).kjs$getBlockBuilder());
        }
    }
}
