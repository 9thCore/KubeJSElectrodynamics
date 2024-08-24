ServerEvents.recipes(event => {
    // Electrodynamics
    event.recipes.electrodynamics.energized_alloyer_recipe(
        // item output
        "4x minecraft:diamond",
        // inputs (2)
        [
            Ingredient.of("minecraft:iron_ingot").withCount(2),
            "4x #minecraft:logs"
        ],
        // usage per tick
        100,
        // ticks required for craft
        40,
        // experience
        1.0,
        // item byproduct (1, optional)
        Item.of("2x minecraft:stone").withChance(0.25)
    );

    event.recipes.electrodynamics.lathe_recipe(
        // item output
        "2x minecraft:heavy_weighted_pressure_plate",
        // input (1)
        "minecraft:iron_block",
        // usage per tick
        100,
        // ticks required for craft
        40,
        // experience
        1.0,
        // item byproduct (1, optional)
        Item.of("2x minecraft:iron_ingot").withChance(0.5)
    );

    // Blastcraft
    if (Platform.getMods().containsKey("blastcraft")) {
        event.recipes.blastcraft.blast_compressor_recipe(
            // item output
            "7x minecraft:blaze_powder",
            // input (1)
            [
                "2x minecraft:blaze_rod"
            ],
            // usage per tick
            500,
            // ticks required for craft
            1200,
            // experience
            2.0,
            // item byproduct (1, optional)
            // Item.of("2x minecraft:blaze_powder").withChance(0.5)
        );
    }

    if (Platform.getMods().containsKey("nuclearscience")) {
        event.recipes.nuclearscience.fuel_reprocessor_recipe(
            // item output
            "minecraft:cod",
            // input (1)
            [
                "minecraft:pufferfish"
            ],
            // usage per tick
            100,
            // ticks required for craft
            40,
            // experience
            1.0,
            // item byproduct (1, optional)
            Item.of('minecraft:spider_eye').withChance(0.25)
        );

        event.recipes.nuclearscience.fission_reactor_recipe(
            // item output
            "minecraft:pufferfish",
            // input (1)
            "minecraft:cod",
            // usage per tick
            100,
            // ticks required for craft
            40,
            // experience
            1.0
        );
    }
});