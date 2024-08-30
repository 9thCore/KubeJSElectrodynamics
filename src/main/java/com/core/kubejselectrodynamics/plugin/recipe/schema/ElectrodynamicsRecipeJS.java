package com.core.kubejselectrodynamics.plugin.recipe.schema;

import com.core.kubejselectrodynamics.plugin.recipe.schema.fluid.TagFluidStackJS;
import com.core.kubejselectrodynamics.plugin.recipe.schema.gas.ElectroGasStackJS;
import com.core.kubejselectrodynamics.util.ElectroFluidWrapper;
import com.core.kubejselectrodynamics.util.ItemUtils;
import com.google.gson.JsonElement;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.recipe.recipeutils.AbstractMaterialRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.GasIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ElectrodynamicsRecipeJS extends RecipeJS {
    @Info("Return the original recipe's item ingredients only.")
    public Ingredient[] getOriginalRecipeItemIngredients() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get item ingredients");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            List<Ingredient> allIngredients = recipe.getIngredients();

            // Use an array so we don't lose extended class data
            Ingredient[] itemIngredients = new Ingredient[allIngredients.size()];
            int i = 0;
            for (Ingredient ingredient : allIngredients) {
                if (!(ingredient instanceof FluidIngredient) && !(ingredient instanceof GasIngredient)) {
                    itemIngredients[i++] = ingredient;
                }
            }

            // Since we may or may not have included all ingredients, we have to re-create it with the correct length
            Ingredient[] output = new Ingredient[i];
            for (int j = 0; j < i; j++) {
                output[j] = itemIngredients[j];
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe is not of Electrodynamics type - could not get item ingredients");
        return null;
    }

    @Info("Return the original recipe's item byproducts only.")
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

    @Info("Return the original recipe's fluid ingredients only.")
    public FluidStackJS[] getOriginalRecipeFluidIngredients() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get fluid ingredients");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            List<FluidIngredient> fluidIngredients = recipe.getFluidIngredients();
            FluidStackJS[] output = new FluidStackJS[fluidIngredients.size()];
            int i = 0;
            for (FluidIngredient fluid : fluidIngredients) {
                output[i++] = ElectroFluidWrapper.of(fluid);
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe is not of Electrodynamics type - could not get fluid ingredients");
        return null;
    }

    @Info("Return the original recipe's fluid result only.")
    public FluidStackJS getOriginalRecipeFluidResult() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get fluid result");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return ElectroFluidWrapper.of(FluidStackHooksForge.fromForge(recipe.getFluidRecipeOutput()));
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get fluid result");
        return null;
    }

    @Info("Return the original recipe's fluid byproducts only.")
    public FluidStackJS[] getOriginalRecipeFluidByproducts() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get fluid byproducts");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            FluidStackJS[] output = new FluidStackJS[recipe.getFluidBiproductCount()];
            int i = 0;
            for (FluidStack stack : recipe.getFullFluidBiStacks()) {
                output[i++] = ElectroFluidWrapper.of(stack);
            }
            return output;
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get fluid byproducts");
        return null;
    }

    @Info("Return the original recipe's gas ingredients only.")
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

    @Info("Return the original recipe's gas byproducts only.")
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

    @Info("Return the original recipe's gas result only.")
    public ElectroGasStackJS getOriginalRecipeGasResult() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get gas result");
            return null;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return ElectroGasStackJS.of(recipe.getGasRecipeOutput());
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get gas result");
        return null;
    }

    /*
    @Info("Return the original recipe's power usage per tick.")
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
     */

    @Info("Return the original recipe's ticks required to craft.")
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

    @Info("Return the original recipe's experience.")
    public double getOriginalRecipeExperience() {
        if (getOriginalRecipe() == null) {
            ConsoleJS.SERVER.warn("Original recipe is null - could not get experience");
            return 0;
        }

        if (getOriginalRecipe() instanceof AbstractMaterialRecipe recipe) {
            return recipe.getXp();
        }

        ConsoleJS.SERVER.warn("Original recipe not of Electrodynamics type - could not get experience");
        return 0;
    }


    @Override
    public OutputFluid readOutputFluid(Object from) {
        return ElectroFluidWrapper.of(from);
    }

    @Override
    public InputFluid readInputFluid(Object from) {
        return ElectroFluidWrapper.of(from);
    }

    @Override
    public JsonElement writeInputItem(InputItem item) {
        return ItemUtils.writeInputItem(item);
    }
}
