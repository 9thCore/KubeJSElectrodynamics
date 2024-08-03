package com.core.kubejselectrodynamics.mixin;

import com.core.kubejselectrodynamics.util.CountableIngredientInterface;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CountableIngredient.class)
public class CountableIngredientMixin implements CountableIngredientInterface {
    @Shadow private Ingredient INGREDIENT;

    @Override
    public Ingredient kjsElectro$getIngredient() {
        return this.INGREDIENT;
    }
}
