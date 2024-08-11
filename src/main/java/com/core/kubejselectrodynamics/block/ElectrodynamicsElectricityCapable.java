package com.core.kubejselectrodynamics.block;

import com.core.kubejselectrodynamics.block.batterybox.BlockBatteryBoxBuilder;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Set;

public interface ElectrodynamicsElectricityCapable<T extends BlockBuilder> {
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
