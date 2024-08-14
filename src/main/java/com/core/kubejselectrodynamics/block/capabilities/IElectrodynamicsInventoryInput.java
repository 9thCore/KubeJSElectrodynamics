package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.Direction;

import java.util.Arrays;
import java.util.EnumSet;

public interface IElectrodynamicsInventoryInput<T extends BlockBuilder> extends ISelfAccessor<T> {
    @Info("Sets the machine's inventory input faces.\nDirection.ALL is not supported, use #allInventoryInput() instead.")
    default T inventoryInputs(Direction[] directions) {
        getInventoryInputSet().clear();
        getInventoryInputSet().addAll(Arrays.asList(directions));
        return getThis();
    }
    @Info("Sets the machine's inventory input face.\nDirection.ALL is not supported, use #allInventoryInput() instead.")
    default T inventoryInput(Direction direction) {
        return inventoryInputs(new Direction[] {direction});
    }
    @Info("Allows inventory input through every face.")
    default T allInventoryInput() {
        return inventoryInputs(Direction.values());
    }
    @Info("Disallows inventory input through any face.")
    default T noInventoryInput() {
        return inventoryInputs(new Direction[0]);
    }
    @HideFromJS
    default Direction[] getInventoryInput() {
        return getInventoryInputSet().toArray(new Direction[0]);
    }
    @HideFromJS
    EnumSet<Direction> getInventoryInputSet();
}
