package com.core.kubejselectrodynamics.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.registers.NuclearScienceItems;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class RadiationUtil {
    // rod: [fuelValue, emptyRod]
    // Default emptyRod is base mod Spent Fuel Rod
    public static final Map<Item, Pair<Integer, Item>> customFuelRods = new HashMap<>();
    public static final Pair<Integer, Item> NULL = new Pair<>(0, Items.AIR);

    public static void register(Item item, int fuelValue, Item spent) {
        customFuelRods.put(item, new Pair<>(fuelValue, spent));
    }

    public static void register(Item item, int fuelValue) {
        register(item, fuelValue, NuclearScienceItems.ITEM_FUELSPENT.get());
    }

    public static int getFuelValue(Item item) {
        return customFuelRods.getOrDefault(item, NULL).getA();
    }

    public static Item getSpentFuel(Item item) {
        return customFuelRods.getOrDefault(item, NULL).getB();
    }

    public static boolean isFuelRod(Item item) {
        return customFuelRods.containsKey(item);
    }

    public static double getRadiation(ItemStack stack) {
        return RadiationRegister.get(stack.getItem()).getRadiationStrength() * stack.getCount();
    }
}
