package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IForgeEnergyOutput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's FE output faces.\nDirection.ALL is not supported, use #allFEOutput() instead.")
    default T feOutputs(Direction[] directions) {
        getFEOutputSet().clear();
        getFEOutputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's FE output face.\nDirection.ALL is not supported, use #allFEOutput() instead.")
    default T feOutput(Direction direction) {
        return feOutputs(new Direction[] {direction});
    }
    @Info("Allows FE output through every face.")
    default T allFEOutput() {
        return feOutputs(Direction.values());
    }
    @HideFromJS
    default Direction[] getFEOutput() {
        return getFEOutputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getFEOutputSet();
}
