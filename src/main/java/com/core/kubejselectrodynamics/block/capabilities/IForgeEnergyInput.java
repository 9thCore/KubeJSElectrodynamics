package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IForgeEnergyInput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's FE input faces.\nDirection.ALL is not supported, use #allFEInput() instead.")
    default T feInputs(Direction[] directions) {
        getFEInputSet().clear();
        getFEInputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's FE input face.\nDirection.ALL is not supported, use #allFEInput() instead.")
    default T feInput(Direction direction) {
        return feInputs(new Direction[] {direction});
    }
    @Info("Allows FE output through every face.")
    default T allFEInput() {
        return feInputs(Direction.values());
    }
    @Info("Disallows FE input through any face.")
    default T noFEInput() {
        return feInputs(new Direction[0]);
    }
    @HideFromJS
    default Direction[] getFEInput() {
        return getFEInputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getFEInputSet();
}
