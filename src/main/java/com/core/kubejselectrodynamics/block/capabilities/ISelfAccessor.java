package com.core.kubejselectrodynamics.block.capabilities;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.rhino.util.HideFromJS;

/**
 * Helper interface that several other interfaces extend to access the builder object.
 * Only requires one override for all interfaces to function.
 */
public interface ISelfAccessor<T extends BuilderBase<?>> {
    @HideFromJS
    T getThis();
}
