ElectrodynamicsEvents.combustionChamber(event => {
    event.register("minecraft:lava", 500, 0.5); // This is a tag! No method to register a single fluid
    event.unregister("forge:hydrogen"); // Again, tag
})