package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

public interface ElectrodynamicsElectricityInput<T extends BlockBuilder> {
    @Info("Sets the machine's electric input faces.\nDirection.ALL is not supported, use #allElectricInput() instead.")
    T electricInputs(Direction[] directions);
    @Info("Sets the machine's electric input face.\nDirection.ALL is not supported, use #allElectricInput() instead.")
    default T electricInput(Direction direction) {
        return electricInputs(new Direction[] {direction});
    }
    @Info("Allows electric input through every face.")
    T allElectricInput();
    @HideFromJS
    Direction[] getElectricInput();
}
