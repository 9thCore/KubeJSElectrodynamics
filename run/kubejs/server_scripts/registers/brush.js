// Protection in case the mod is currently not loaded (testing purposes)
if (Platform.getMods().containsKey("dynamicelectricity")) {
    DynamicElectricityEvents.conductorBrush(event => {
        event.register("kubejs:bad_brush", 2); // We have a custom tooltip, so put the durability tooltip at index 2!
        event.registerItem("kubejs:unbreakable_brush");
    })
}