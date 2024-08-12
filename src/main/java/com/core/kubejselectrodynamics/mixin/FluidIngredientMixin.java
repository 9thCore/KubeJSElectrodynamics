package com.core.kubejselectrodynamics.mixin;

import com.core.kubejselectrodynamics.util.IFluidIngredient;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FluidIngredient.class)
public abstract class FluidIngredientMixin implements IFluidIngredient {
    @Shadow
    private int amount;

    @Override
    public int kjsElectroMixin$getAmount() {
        return this.amount;
    }
}
