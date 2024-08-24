ServerEvents.recipes(event => {
    event.recipes.electrodynamics.electrolytic_separator_recipe(
        // gas output
        ElectrodynamicsGas.of("kubejs:test_gas", 500).withTemperature(500),
        // fluid input (1)
        Fluid.of("minecraft:lava", 1000),
        // usage per tick
        500,
        // ticks
        200,
        // experience
        1.0,
        // gas byproduct (1, required)
        {
            gas: "electrodynamics:oxygen",
            amount: 500,
            temperature: 700
        }
    );
    
    event.recipes.electrodynamics.electrolytic_separator_recipe(
        // gas output
        ElectrodynamicsGas.of("kubejs:cheese_gas", 1000, 20),
        // fluid input (1)
        Fluid.of("minecraft:milk", 500),
        // usage per tick
        200,
        // ticks
        40,
        // experience
        1.0,
        // gas byproduct (1, required)
        ElectrodynamicsGas.hydrogen().withAmount(250)
    );

    // Replace recipe: water to oxygen and hydrogen (it is the only one that matches)
    event.forEachRecipe({
        type: "electrodynamics:electrolytic_separator_recipe"
    }, recipe => {
        console.log("fluid2gas: found recipe " + recipe.getId());
        const gasses = recipe.getOriginalRecipeGasByproducts();
        const electroGasses = [];
        gasses.forEach(gas => {
            console.log("Gas output: " + gas);
            const electroGas = ElectrodynamicsGas.of(gas).withAmount(gas.getAmount() + 500);
            console.log("Electrodynamics gas id: " + gas.getID());
            electroGasses.push(electroGas);
        });

        // Make it last one second longer and use 100 more power per tick
        event.recipes.electrodynamics.electrolytic_separator_recipe(
            recipe.getOriginalRecipeGasResult().withAmount(2000),
            recipe.getOriginalRecipeFluidIngredients()[0].withAmount(4000),
            recipe.getOriginalRecipeUsagePerTick() + 100,
            recipe.getOriginalRecipeTicks() + 20,
            recipe.getOriginalRecipeExperience(),
            electroGasses
        ).id(recipe.getId());
    });
});