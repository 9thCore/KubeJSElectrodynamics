// Protection in case the mod is currently not loaded (testing purposes)
if (Platform.getMods().containsKey("nuclearscience")) {
    NuclearScienceEvents.fuelRod(event => {
        event.register("kubejs:highly_radioactive_iron_rod", 1); // Lower than all other rods in base mod
        event.register("minecraft:netherite_hoe", 4, "minecraft:diamond_hoe"); // Higher than all other rods in base mod
        event.register("nuclearscience:fuelheuo2", 3, "nuclearscience:fuelleuo2"); // Set spent rod item for the Highly Enriched Fuel Rod to its lesser variant
        event.register("nuclearscience:fuelleuo2", 2, "minecraft:air"); // Remove spent rod item for the Enriched Fuel Rod
    });
}