package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsGasInput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's gas input faces.\nDirection.ALL is not supported, use #allGasInput() instead.")
    default T gasInputs(Direction[] directions) {
        getGasInputSet().clear();
        getGasInputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's gas input face.\nDirection.ALL is not supported, use #allGasInput() instead.")
    default T gasInput(Direction direction) {
        return gasInputs(new Direction[] {direction});
    }
    @Info("Allows gas input through every face.")
    default T allGasInput() {
        return gasInputs(Direction.values());
    }
    @Info("Disallows gas input through any face.")
    default T noGasInput() {
        return gasInputs(new Direction[0]);
    }
    @HideFromJS
    default Direction[] getGasInput() {
        return getGasInputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getGasInputSet();
}
