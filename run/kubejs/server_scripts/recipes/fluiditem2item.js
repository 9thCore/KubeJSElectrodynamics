ServerEvents.recipes(event => {
    if (Platform.getMods().containsKey("nuclearscience")) {
        event.recipes.nuclearscience.chemical_extractor_recipe(
            // item output
            Item.of("minecraft:cake"),
            // fluid input (1)
            Fluid.of("minecraft:milk", 200),
            // item input (1)
            Ingredient.of("minecraft:wheat").withCount(3),
            // usage per tick
            100,
            // ticks required for craft
            40,
            // experience
            1.0
        );
        
        event.recipes.nuclearscience.msrfuel_preprocessor_recipe(
            // item output
            Item.of("minecraft:diamond", 8),
            // fluid input (1)
            Fluid.of("minecraft:lava", 5000),
            // item inputs (3)
            [
                Ingredient.of("minecraft:coal").withCount(4),
                Ingredient.of("minecraft:redstone").withCount(16),
                Ingredient.of("minecraft:lapis_lazuli").withCount(12)
            ],
            // usage per tick
            100,
            // ticks required for craft
            40,
            // experience
            1.0
        );
        
        event.recipes.nuclearscience.radioactive_processor_recipe(
            // item output
            Item.of("minecraft:stone"),
            // fluid input (1)
            Fluid.of("minecraft:water", 400),
            // item input (1)
            Ingredient.of("minecraft:redstone").withCount(2),
            // usage per tick
            100,
            // ticks required for craft
            40,
            // experience
            1.0
        );
    }
})