StartupEvents.registry("electrodynamics:gases", event => {
    event.create("kubejs:test_gas")
    .condensationFluid(Fluid.of("minecraft:lava").getFluid(), 100)
    .displayName("Gaseous Gas");
    
    event.create("kubejs:cheese_gas")
    .displayName("Cheese (Gas)");
})