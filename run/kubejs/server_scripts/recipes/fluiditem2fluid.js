ServerEvents.recipes(event => {
    event.recipes.electrodynamics.mineral_washer_recipe(
        // fluid output
        Fluid.of("minecraft:lava", 500),
        // fluid input (1)
        Fluid.of("minecraft:lava", 400),
        // item input (1)
        Ingredient.of("minecraft:cobblestone").withCount(8),
        // ticks required for craft
        40,
        // experience
        1.0
    );

    // Make Fermentation Plant recipes available in the Chemical Mixer at 1.5x crafting time and 2x fluid output
    event.forEachRecipe({
        type: "electrodynamics:fermentation_plant_recipe"
    }, recipe => {
        console.log("fluiditem2item: found recipe: " + recipe);
        const result = recipe.getOriginalRecipeFluidResult();
        event.recipes.electrodynamics.chemical_mixer_recipe(
            result.withAmount(result.getAmount() * 2),
            recipe.getOriginalRecipeFluidIngredients(),
            recipe.getOriginalRecipeItemIngredients(),
            recipe.getOriginalRecipeTicks() * 1.5,
            recipe.getOriginalRecipeExperience()
        );
    });
});