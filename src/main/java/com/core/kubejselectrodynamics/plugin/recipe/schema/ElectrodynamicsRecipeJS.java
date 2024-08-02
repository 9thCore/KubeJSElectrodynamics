package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.ElectroGasStackJS;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.GasIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ElectrodynamicsRecipeJS extends RecipeJS {
    public ItemStack[] getOriginalRecipeItemByproducts() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get item byproducts");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return recipe.getFullItemBiStacks();
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get item byproducts");
        return null;
    }

    public FluidStackJS[] getOriginalRecipeFluidIngredients() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get fluid ingredients");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            List<FluidIngredient> ingredients = recipe.getFluidIngredients();
            FluidStackJS[] output = new FluidStackJS[ingredients.size()];
            int i = 0;
            for (FluidIngredient ingredient : ingredients) {
                FluidStack stack = ingredient.getFluidStack();
                output[i++] = FluidStackJS.of(stack.getFluid(), stack.getAmount(), stack.getTag());
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe is not of Electrodynamics type - could not get fluid ingredients");
        return null;
    }

    public FluidStackJS[] getOriginalRecipeFluidByproducts() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get fluid byproducts");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            FluidStackJS[] output = new FluidStackJS[recipe.getFluidBiproductCount()];
            int i = 0;
            for (FluidStack stack : recipe.getFullFluidBiStacks()) {
                output[i++] = FluidStackJS.of(stack.getFluid(), stack.getAmount(), stack.getTag());
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get fluid byproducts");
        return null;
    }

    public ElectroGasStackJS[] getOriginalRecipeGasIngredients() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get gas ingredients");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            List<GasIngredient> ingredients = recipe.getGasIngredients();
            ElectroGasStackJS[] output = new ElectroGasStackJS[ingredients.size()];
            int i = 0;
            for (GasIngredient ingredient : ingredients) {
                output[i++] = ElectroGasStackJS.of(ingredient.getGasStack());
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe is not of Electrodynamics type - could not get gas ingredients");
        return null;
    }

    public ElectroGasStackJS[] getOriginalRecipeGasByproducts() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get gas byproducts");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            ElectroGasStackJS[] output = new ElectroGasStackJS[recipe.getGasBiproductCount()];
            int i = 0;
            for (GasStack gas : recipe.getFullGasBiStacks()) {
                output[i++] = ElectroGasStackJS.of(gas);
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get gas byproducts");
        return null;
    }

    public ElectroGasStackJS getOriginalRecipeGasOutput() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get output gas");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return ElectroGasStackJS.of(recipe.getGasRecipeOutput());
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get output gas");
        return null;
    }

    public double getOriginalRecipeUsagePerTick() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get usage per tick");
            return 0.0D;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return recipe.getUsagePerTick();
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get usage per tick");
        return 0.0D;
    }

    public int getOriginalRecipeTicks() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get ticks");
            return 0;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return recipe.getTicks();
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get ticks");
        return 0;
    }
}
