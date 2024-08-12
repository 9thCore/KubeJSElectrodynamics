package com.core.kubejselectrodynamics.block;

import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import dev.latvian.mods.kubejs.client.VariantBlockStateGenerator;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;

public class RotatableBlockBuilder<T extends RotatableBlockBuilder<T>> extends HorizontalDirectionalBlockBuilder {
    private int modelRotationOffset = 0;

    public RotatableBlockBuilder(ResourceLocation i) {
        super(i);
    }

    @Info("Applies an offset to the model's rotation when generating the block state data.")
    public T rotationOffset(int offset) {
        modelRotationOffset = getTrueRotation(offset);
        return (T)this;
    }

    private static int getTrueRotation(int rotation) {
        return rotation % 360;
    }

    /**
     * Method originally from KubeJS' source code, adapted to fit custom implementation
     */
    @Override
    protected void generateBlockStateJson(VariantBlockStateGenerator bs) {
        var modelLocation = model.isEmpty() ? newID("block/", "").toString() : model;
        bs.variant("facing=north", v -> v.model(modelLocation).y(modelRotationOffset));
        bs.variant("facing=east", v -> v.model(modelLocation).y(getTrueRotation(90 + modelRotationOffset)));
        bs.variant("facing=south", v -> v.model(modelLocation).y(getTrueRotation(180 + modelRotationOffset)));
        bs.variant("facing=west", v -> v.model(modelLocation).y(getTrueRotation(270 + modelRotationOffset)));
    }
}
