package com.core.kubejselectrodynamics.util;

import electrodynamics.common.tile.pipelines.tanks.gas.GenericTileGasTank;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InsulationUtils {
    public static Map<Item, Double> insulationEffectiveness = new HashMap<Item, Double>();

    public static void registerInsulator(Item item, double factor) {
        insulationEffectiveness.put(item, factor);
    }

    public static double getInsulationEffectiveness(ItemStack item) {
        return insulationEffectiveness.getOrDefault(item.getItem(), GenericTileGasTank.INSULATION_EFFECTIVENESS);
    }

    public static boolean isInsulator(ItemStack item) {
        return insulationEffectiveness.containsKey(item.getItem());
    }
}
