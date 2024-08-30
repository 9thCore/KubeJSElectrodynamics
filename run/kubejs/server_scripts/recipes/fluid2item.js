ServerEvents.recipes(event => {
    event.recipes.electrodynamics.chemical_crystallizer_recipe(
        // item output
        "minecraft:cake",
        // input (1)
        ElectrodynamicsFluid.of("minecraft:milk", 4000),
        // ticks required for craft
        10,
        // experience
        1.0
    );

    // Crsytallize lava into ice
    event.replaceOutput({
        type: "electrodynamics:chemical_crystallizer_recipe"
    }, "minecraft:obsidian", "minecraft:ice");

    // 5x ore multiplication is a bit much for silver, so let's make it 4x
    event.forEachRecipe({
        type: "electrodynamics:chemical_crystallizer_recipe",
        output: "electrodynamics:crystalsilver"
    }, recipe => {
        console.log("fluid2item: found recipe " + recipe.getId());
        event.recipes.electrodynamics.chemical_crystallizer_recipe(
            recipe.getOriginalRecipeResult(),
            recipe.getOriginalRecipeFluidIngredients()[0].withAmount(250), // 200 -> 250
            recipe.getOriginalRecipeTicks(),
            recipe.getOriginalRecipeExperience()
        ).id(recipe.getId());
    })
});