package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.util.ICountableIngredient;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CountableIngredient.class)
public class CountableIngredientMixin implements ICountableIngredient {
    @Shadow(remap = false) private Ingredient INGREDIENT;

    @Override
    public Ingredient kjsElectro$getIngredient() {
        return this.INGREDIENT;
    }
}
