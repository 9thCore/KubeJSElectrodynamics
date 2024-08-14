package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsFluidOutput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's fluid output faces.\nDirection.ALL is not supported, use #allFluidOutput() instead.")
    default T fluidOutputs(Direction[] directions) {
        getFluidOutputSet().clear();
        getFluidOutputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's fluid output face.\nDirection.ALL is not supported, use #allFluidOutput() instead.")
    default T fluidOutput(Direction direction) {
        return fluidOutputs(new Direction[] {direction});
    }
    @Info("Allows fluid input through every face.")
    default T allFluidOutput() {
        return fluidOutputs(Direction.values());
    }
    @Info("Disallows fluid output through any face.")
    default T noFluidOutput() {
        return fluidOutputs(new Direction[0]);
    }
    @HideFromJS
    default Direction[] getFluidOutput() {
        return getFluidOutputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getFluidOutputSet();
}
