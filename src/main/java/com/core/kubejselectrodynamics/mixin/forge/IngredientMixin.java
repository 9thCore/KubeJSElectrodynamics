package com.core.kubejselectrodynamics.mixin.forge;

import com.core.kubejselectrodynamics.util.IIngredient;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Ingredient.class)
public class IngredientMixin implements IIngredient {

    @Shadow(remap = false) @Final private Ingredient.Value[] values;

    @Override
    public Ingredient.Value[] kjsElectro$getIngredientValues() {
        return this.values;
    }
}
