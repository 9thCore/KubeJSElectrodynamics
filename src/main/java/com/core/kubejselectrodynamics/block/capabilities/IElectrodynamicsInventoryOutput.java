package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsInventoryOutput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's inventory output faces.\nDirection.ALL is not supported, use #allInventoryOutput() instead.")
    default T inventoryOutputs(Direction[] directions) {
        getInventoryOutputSet().clear();
        getInventoryOutputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's inventory output face.\nDirection.ALL is not supported, use #allInventoryOutput() instead.")
    default T inventoryOutput(Direction direction) {
        return inventoryOutputs(new Direction[] {direction});
    }
    @Info("Allows inventory input through every face.")
    default T allInventoryOutput() {
        return inventoryOutputs(Direction.values());
    }
    @HideFromJS
    default Direction[] getInventoryOutput() {
        return getInventoryOutputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getInventoryOutputSet();
}
