package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsFluidInput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's fluid input faces.\nDirection.ALL is not supported, use #allFluidInput() instead.")
    default T fluidInputs(Direction[] directions) {
        getFluidInputSet().clear();
        getFluidInputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's fluid input face.\nDirection.ALL is not supported, use #allFluidInput() instead.")
    default T fluidInput(Direction direction) {
        return fluidInputs(new Direction[] {direction});
    }
    @Info("Allows fluid input through every face.")
    default T allFluidInput() {
        return fluidInputs(Direction.values());
    }
    @Info("Disallows fluid input through any face.")
    default T noFluidInput() {
        return fluidInputs(new Direction[0]);
    }
    @HideFromJS
    default Direction[] getFluidInput() {
        return getFluidInputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getFluidInputSet();
}
