package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsElectricityInput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's electric input faces.\nDirection.ALL is not supported, use #allElectricInput() instead.")
    default T electricInputs(Direction[] directions) {
        getElectricInputSet().clear();
        getElectricInputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's electric input face.\nDirection.ALL is not supported, use #allElectricInput() instead.")
    default T electricInput(Direction direction) {
        return electricInputs(new Direction[] {direction});
    }
    @Info("Allows electric input through every face.")
    default T allElectricInput() {
        getElectricInputSet().clear();
        return getThis();
    }
    @HideFromJS
    default Direction[] getElectricInput() {
        return getElectricInputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getElectricInputSet();
}
