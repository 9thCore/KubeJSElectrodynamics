package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

public interface ElectrodynamicsElectricityOutput<T extends BlockBuilder> {
    @Info("Sets the machine's electric output faces.\nDirection.ALL is not supported, use #allElectricOutput() instead.")
    T electricOutputs(Direction[] directions);
    @Info("Sets the machine's electric output face.\nDirection.ALL is not supported, use #allElectricOutput() instead.")
    default T electricOutput(Direction direction) {
        return electricOutputs(new Direction[] {direction});
    }
    @Info("Allows electric output through every face.")
    T allElectricOutput();
    @HideFromJS
    Direction[] getElectricOutput();
}
