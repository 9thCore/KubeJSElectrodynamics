package com.core.kubejselectrodynamics.util;

import net.minecraft.world.item.Item;

import java.util.HashSet;
import java.util.Set;

public class MotorBrushRegister {
    public static final Set<Item> brushes = new HashSet<>();

    public static void register(Item item) {
        brushes.add(item);
    }
}
