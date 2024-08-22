package com.core.kubejselectrodynamics.item.radioactive;

import com.core.kubejselectrodynamics.block.capabilities.ISelfAccessor;
import com.core.kubejselectrodynamics.radioactive.IRadioactiveBuilder;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;

public interface IRadioactiveItemBuilder<T extends ItemBuilder> extends IRadioactiveBuilder, ISelfAccessor<T> {
    @Info("Sets the item's radiation quantity, as a double.")
    default T radiation(double radiation) {
        setRadiation(radiation);
        return getThis();
    }

    @Info("Sets the item's radiation radius, as a double.")
    default T radiationRadius(double radiationRadius) {
        setRadiationRadius(radiationRadius);
        return getThis();
    }
}
