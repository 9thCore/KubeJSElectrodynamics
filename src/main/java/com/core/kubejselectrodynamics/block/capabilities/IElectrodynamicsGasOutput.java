package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsGasOutput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's gas output faces.\nDirection.ALL is not supported, use #allGasOutut() instead.")
    default T gasOutputs(Direction[] directions) {
        getGasOutputSet().clear();
        getGasOutputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's gas output face.\nDirection.ALL is not supported, use #allGasOutput() instead.")
    default T gasOutput(Direction direction) {
        return gasOutputs(new Direction[] {direction});
    }
    @Info("Allows gas input through every face.")
    default T allGasOutput() {
        return gasOutputs(Direction.values());
    }
    @Info("Disallows gas input through any face.")
    default T noGasOutput() {
        return gasOutputs(new Direction[0]);
    }
    @HideFromJS
    default Direction[] getGasOutput() {
        return getGasOutputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getGasOutputSet();
}
