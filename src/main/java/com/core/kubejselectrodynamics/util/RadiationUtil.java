package com.core.kubejselectrodynamics.util;

import net.minecraft.world.item.ItemStack;
import nuclearscience.api.radiation.RadiationRegister;

public class RadiationUtil {
    public static double getRadiation(ItemStack stack) {
        return RadiationRegister.get(stack.getItem()).getRadiationStrength() * stack.getCount();
    }
}
