StartupEvents.registry("block", event => {
    // ELECTRODYNAMICS
    event.create("kubejs:big_battery_box", "electrodynamics:batterybox")
    .voltage(960)
    .capacity(1e9)
    .output(1e7)
    .waterlogged()
    .batteryModelRotation() // Utility method for quickly using base Electrodynamics battery box models
    .useBaseRenderer("lithiumbatterybox")
    // .model("electrodynamics:block/lithiumbatterybox") // This is not needed, as the call above handles drawing it!
    .displayName("The BIG Battery Box");
    
    event.create("kubejs:small_battery_box", "electrodynamics:batterybox")
    .voltage(120)
    .capacity(1e6)
    .output(5)
    .batteryModelRotation()
    .useCustomRenderer(["electrodynamics:block/lithiumbatterybox7", "electrodynamics:block/batterybox4", "electrodynamics:block/carbynebatterybox"]) // Make the bar decrease in increments of half the length!
    .notSolid() // Needed if using custom rendering (be it via #useBaseRenderer() or #useCustomRenderer())
    .displayName("small battery box");
    
    event.create("kubejs:all_output_box", "electrodynamics:batterybox")
    .voltage(240)
    .capacity(1e5)
    .output(1e3)
    .allElectricOutput() // Allow electric output through every face
    .model("minecraft:block/target");
    
    event.create("kubejs:vertical_battery", "electrodynamics:batterybox")
    .voltage(240)
    .capacity(5e6)
    .output(1e4)
    .electricInput(Direction.DOWN) // Input from below
    .electricOutputs([Direction.UP, Direction.SOUTH]) // Output above and in front
    .model("minecraft:block/reinforced_deepslate");

    event.create("kubejs:mathematically_impossible_tank", "electrodynamics:tank")
    .capacity(1024000)
    .useBaseRenderer() // Fluid inside will render if called (can be omitted if the fluid will never be seen)
    .fluidInputs(["down", "north"]) // Strings also work! Input from the bottom or the back
    .fluidOutputs(["up", "south"]) // Output above or in front
    .notSolid() // Important if you enable the fluid rendering, otherwise the fluid will be darkened
    .model("electrodynamics:block/tankreinforced");

    event.create("kubejs:refrigerated_gas_storage", "electrodynamics:gastank")
    .capacity(4000) // Small tank, but...
    .heatLoss(0) // ...it doesn't lose heat!
    .gasInput("down")
    .gasOutputs([Direction.NORTH, "up"])
    .copyVoxelShape("hslagastank")
    .model("electrodynamics:block/gastankhsla");

    // Do note you need two textures: test_wire and logistical_test_wire
    // See assets directory for an example
    event.create("kubejs:test_wire", "electrodynamics:wire")
    .wireTint(255, 127, 0, 255) // Orange wire tint
    .resistance(0.05) // 5x the resistance of Iron
    .ampacity(600) // Same ampacity as Silver
    .texture("conductor", "kubejs:block/wire/test_wire") // Texture used in the center of the wire
    .texture("logistical_conductor", "kubejs:block/wire/logistical_test_wire") // The texture on the logistical wire specifically, defaults to the wire's conductor (and looks bad)
    .notSolid();

    if (Platform.getMods().containsKey("dynamicelectricity")) {
        // DYNAMIC ELECTRICTY
        event.create("kubejs:impossibly_efficient_dc_motor", "dynamicelectricity:motordc")
        .voltage(480)
        .feConsumption(100000) // How much FE to try to consume every tick
        .conversionEfficiency(2) // FE:Joules ratio. Cannot go below 0, but can go above 1, leading to free FE/Joules (1 FE -> 2 Joules -> 2 FE -> 4 Joules...)
        .motorModelRotation()
        .copyVoxelShape("motordclv")
        .useBaseRenderer("mv") // Shaft will render if called (can be omitted if the shaft will never be seen)
        .feInputs([Direction.NORTH, Direction.DOWN]) // Insert FE through the back or the bottom
        .fluidInput(Direction.UP) // Input Lubricant through the top only
        .inventoryInputs([Direction.EAST]) // Restrict inventory input through the side
        .model("dynamicelectricity:block/motordclv")
        .displayName("Impossibly Efficient DC Motor");
        
        event.create("kubejs:incredibly_inefficient_ac_motor", "dynamicelectricity:motorac")
        .voltage(120)
        .joulesConsumption(1000) // How many Joules to try to consume every tick
        .conversionEfficiency(0.25) // Joules:FE ratio
        .motorModelRotation()
        .copyVoxelShape("motorachv")
        .useBaseRenderer("lv")
        .noFluidInput() // Disallow putting in the fluid directly. Only way to input lubricant now is through the fluid canister! Non-automatable, unless another mod can extract out
        .model("dynamicelectricity:block/motorachv")
        .displayName("Incredibly Inefficient AC Motor");
    } else {
        console.warn("Could not create Dynamic Electricity blocks, because the mod is not loaded!");
    }

    if (Platform.getMods().containsKey("nuclearscience")) {
        event.create("kubejs:highly_radioactive_iron_block", "nuclearscience:radioactive")
        .radiation(4500) // Highly lethal radiation (9x ingot form)
        .color(0, Color.rgba(127, 255, 127, 255))
        .hardness(0)
        .textureAll("minecraft:block/iron_block");
    } else {
        console.warn("Could not create Nuclear Science blocks, because the mod is not loaded!");
    }
})