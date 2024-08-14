package com.core.kubejselectrodynamics.util;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import java.util.EnumSet;

public class TileUtils {
    public static EnumSet<Direction> getGlobalDirections(EnumSet<Direction> localDirections, Direction facing) {
        EnumSet<Direction> result = EnumSet.noneOf(Direction.class);
        for (Direction direction : localDirections) {
            result.add(BlockEntityUtils.getRelativeSide(facing, direction));
        }
        return result;
    }

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
}
