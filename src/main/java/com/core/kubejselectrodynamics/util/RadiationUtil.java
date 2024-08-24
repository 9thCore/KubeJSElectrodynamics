package com.core.kubejselectrodynamics.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import nuclearscience.api.radiation.RadiationRegister;

import java.util.HashMap;
import java.util.Map;

public class RadiationUtil {
    public static final Map<Item, Integer> customFuelRods = new HashMap<>();

    public static double getRadiation(ItemStack stack) {
        return RadiationRegister.get(stack.getItem()).getRadiationStrength() * stack.getCount();
    }
}
