package com.core.kubejselectrodynamics.util;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.EnumSet;

public class TileUtils {
    /**
     * Rotate the given directions according to the given orientation
     */
    public static EnumSet<Direction> getGlobalDirections(EnumSet<Direction> localDirections, Direction facing) {
        EnumSet<Direction> result = EnumSet.noneOf(Direction.class);
        for (Direction direction : localDirections) {
            result.add(BlockEntityUtils.getRelativeSide(facing, direction));
        }
        return result;
    }

    /**
     * Method originally from Electrodynamics' source code
     * GenericTile capability code extracted out so it can be called instead of going through the superclass
     */
    public static <T> LazyOptional<T> genericTileGetCapability(GenericTile tile, Capability<T> cap, Direction side) {
        if (cap == ElectrodynamicsCapabilities.ELECTRODYNAMIC && tile.hasComponent(IComponentType.Electrodynamic)) {
            return tile.getComponent(IComponentType.Electrodynamic).getCapability(cap, side, null);
        } else if (cap == ForgeCapabilities.FLUID_HANDLER && tile.hasComponent(IComponentType.FluidHandler)) {
            return tile.getComponent(IComponentType.FluidHandler).getCapability(cap, side, null);
        } else if (cap == ForgeCapabilities.ITEM_HANDLER && tile.hasComponent(IComponentType.Inventory)) {
            return tile.getComponent(IComponentType.Inventory).getCapability(cap, side, null);
        } else if (cap == ElectrodynamicsCapabilities.GAS_HANDLER && tile.hasComponent(IComponentType.GasHandler)) {
            return tile.getComponent(IComponentType.GasHandler).getCapability(cap, side, null);
        }
        return LazyOptional.empty();
    }

    /**
     * First ResourceLocation is of the format "base", subsequent is of the format "base{i}", with i starting at 2
     */
    public static ResourceLocation[] generateModelArray(int length, String base) {
        ResourceLocation[] array = new ResourceLocation[length];
        array[0] = new ResourceLocation(base);
        for (int i = 1; i < array.length; i++) {
            array[i] = new ResourceLocation(base + String.valueOf(i + 1));
        }
        return array;
    }
}
