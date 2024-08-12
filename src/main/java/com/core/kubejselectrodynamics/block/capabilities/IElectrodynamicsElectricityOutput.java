package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsElectricityOutput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's electric output faces.\nDirection.ALL is not supported, use #allElectricOutput() instead.")
    default T electricOutputs(Direction[] directions) {
        getElectricOutputSet().clear();
        getElectricOutputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's electric output face.\nDirection.ALL is not supported, use #allElectricOutput() instead.")
    default T electricOutput(Direction direction) {
        return electricOutputs(new Direction[] {direction});
    }
    @Info("Allows electric output through every face.")
    default T allElectricOutput() {
        getElectricOutputSet().clear();
        return getThis();
    }
    @HideFromJS
    default Direction[] getElectricOutput() {
        return getElectricOutputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getElectricOutputSet();
}
