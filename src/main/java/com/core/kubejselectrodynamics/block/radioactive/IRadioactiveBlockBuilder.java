package com.core.kubejselectrodynamics.block.radioactive;

import com.core.kubejselectrodynamics.block.capabilities.ISelfAccessor;
import com.core.kubejselectrodynamics.radioactive.IRadioactiveBuilder;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.level.block.Block;

public interface IRadioactiveBlockBuilder<T extends BlockBuilder> extends IRadioactiveBuilder, ISelfAccessor<T> {
    @Info("Sets the block's radiation quantity, as a double.")
    default T radiation(double radiation) {
        setRadiation(radiation);
        return getThis();
    }

    @Info("Sets the block's radiation radius, as a double.")
    default T radiationRadius(double radiationRadius) {
        setRadiationRadius(radiationRadius);
        return getThis();
    }

    Block get();
}
