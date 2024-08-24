StartupEvents.registry("item", event => {
    event.create("kubejs:test_cylinder", "electrodynamics:portable_cylinder")
    .capacity(2500.0) // Half the capacity of the Electrodynamics Portable Cylinder
    .pressure(2500.0) // But more than double its max pressure
    .temperature(1500.0) // And 1.5x its max temperature!
    .texture("electrodynamics:item/portablecylinder")
    .displayName("Testing Cylinder");

    event.create("kubejs:test_canister", "electrodynamics:canister")
    .dontFillTab() // This is basically the base Electrodynamics canister so don't bother with all those items with fluids; the empty canister is still available in the KubeJS tab
    .capacity(3000) // Right in the middle between Bucket (1000) and Reinforced Canister (5000)
    .texture("base", "electrodynamics:item/canisterreinforced/base") // Use base Electrodynamics texture
    .texture("fluid", "electrodynamics:item/canisterreinforced/fluid"); // Use base Electrodynamics texture

    event.create("kubejs:fluid_orb", "electrodynamics:canister")
    .capacity(500)
    .emptyTexture("minecraft:lava") // Make it lava when the texture is empty
    .texture("minecraft:item/ender_pearl"); // Use this texture for both case and fill

    event.create("kubejs:transformative_battery", "electrodynamics:battery")
    .capacity(1e7)
    .input(1e6, 240) // 240V for input
    .output(1e6, 480) // 480V for output
    .texture("electrodynamics:item/lithiumbattery");

    // Too big to fit in base mod 120V electrical items!
    event.create("kubejs:cool_battery", "electrodynamics:battery")
    .voltage(120) // 120V for both input and output
    .capacity(1e7)
    .input(1e1)
    .output(1e4)
    .texture("electrodynamics:item/battery");

    // Much smaller than the base 120V battery
    event.create("kubejs:small_battery", "electrodynamics:battery")
    .joules(1e3) // 1e3 joule transfer for both input and output
    .voltage(120) // 120V for both input and output
    .capacity(1e5)
    .texture("electrodynamics:item/battery");

    // Gas tank insulator
    // Insulation registration in server_scripts/registers/insulation.js
    event.create("kubejs:endgame_insulation")
    .texture("electrodynamics:item/fiberglasssheet");
    
    // Technically this is not required here, because brushes don't have a special type
    if (Platform.getMods().containsKey("dynamicelectricity")) {
        event.create("kubejs:bad_brush") // No special type...
        .tag("kubejselectrodynamics:conductorbrush") // ...just a special tag!
        .maxDamage(100)
        .texture("minecraft:item/brush")
        .tooltip(Text.gray("This doesn't seem very good at conducting electricity..."));
        
        event.create("kubejs:unbreakable_brush")
        .tag("kubejselectrodynamics:conductorbrush")
        .texture("minecraft:item/nether_star");
        // Notice the absence of a #maxDamage() call!
    } else {
        console.warn("Could not create Dynamic Electricity items, because the mod is not loaded!");
    }

    if (Platform.getMods().containsKey("nuclearscience")) {
        event.create("kubejs:highly_radioactive_iron", "nuclearscience:radioactive")
        .radiation(500) // Decently lethal radiation
        .color(0, Color.rgba(127, 255, 127, 255))
        .texture("minecraft:item/iron_ingot");
        
        // Fuel rod registration in server_scripts/registers/fuelrod.js
        event.create("kubejs:highly_radioactive_iron_rod", "nuclearscience:radioactive")
        .radiation(750) // Low radiation, but infinite durability
        .unstackable()
        .color(0, Color.rgba(127, 255, 127, 255))
        .texture("nuclearscience:item/fuelplutonium");
    } else {
        console.warn("Could not create Nuclear Science items, because the mod is not loaded!");
    }
})