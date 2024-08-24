// Protection in case the mod is currently not loaded (testing purposes)
if (Platform.getMods().containsKey("nuclearscience")) {
    NuclearScienceEvents.fuelRod(event => {
        event.register("kubejs:highly_radioactive_iron_rod", 1); // Lower than all other rods in base mod
        event.register("minecraft:netherite_hoe", 4); // Higher than all other rods in base mod
    });
}