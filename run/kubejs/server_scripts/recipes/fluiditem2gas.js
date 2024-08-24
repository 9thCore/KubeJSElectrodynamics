ServerEvents.recipes(event => {
    if (Platform.getMods().containsKey("nuclearscience")) {
        event.recipes.nuclearscience.nuclear_boiler_recipe(
            // gas output
            ElectrodynamicsGas.of("kubejs:cheese_gas", 250),
            // fluid input (1)
            Fluid.of("minecraft:lava", 500),
            // item input (1)
            Ingredient.of("minecraft:copper_ingot").withCount(4),
            // usage per tick
            100,
            // ticks required for craft
            40,
            // experience
            1.0
        );
    }
})