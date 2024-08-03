package com.core.kubejselectrodynamics.mixin;

import com.core.kubejselectrodynamics.util.IngredientInterface;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Ingredient.class)
public class IngredientMixin implements IngredientInterface {

    @Shadow @Final private Ingredient.Value[] values;

    @Override
    public Ingredient.Value[] kjsElectro$getIngredientValues() {
        return this.values;
    }
}
