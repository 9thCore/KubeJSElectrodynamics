package com.core.kubejselectrodynamics.block.capabilities;

import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InputOutputUtil {
    public static Direction[] DirectionSetToArray(Set<Direction> set) {
       return set.toArray(new Direction[0]);
    }

    public static void ClearDirectionSet(Set<Direction> set) {
        set.clear();
    }

    public static void AssignMaskArray(Boolean[] mask, Direction[] directions) {
        for (Direction direction : directions) {
            mask[direction.ordinal()] = true;
        }
    }
}
