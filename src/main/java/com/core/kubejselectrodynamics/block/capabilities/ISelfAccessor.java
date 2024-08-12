package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;

/**
 * Helper interface that several other interfaces extend to access the builder object.
 * Only requires one override for all interfaces to function.
 */
public interface ISelfAccessor<T extends BlockBuilder> {
    @HideFromJS
    T getThis();
}
