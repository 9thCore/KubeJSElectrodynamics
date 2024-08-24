ElectrodynamicsEvents.thermoelectricGenerator(event => {
    event.register("#minecraft:water", 0.25); // Same as event.registerTag("minecraft:water", 0.25)
    event.unregister("minecraft:flowing_lava"); // Same as event.unregisterFluid("minecraft:flowing_lava"); disable the flowing block only
})