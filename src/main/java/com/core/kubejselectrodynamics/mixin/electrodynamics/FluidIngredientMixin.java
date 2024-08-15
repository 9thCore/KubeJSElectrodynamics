package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.util.IFluidIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FluidIngredient.class)
public abstract class FluidIngredientMixin implements IFluidIngredient {
    @Shadow(remap = false) private int amount;

    @Override
    public int kjsElectroMixin$getAmount() {
        return this.amount;
    }
}
