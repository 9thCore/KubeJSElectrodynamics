// Protection in case the mod is currently not loaded (testing purposes)
if (Platform.getMods().containsKey("dynamicelectricity")) {
    DynamicElectricityEvents.conductorBrush(event => {
        event.register("kubejs:bad_brush");
        event.registerItem("kubejs:unbreakable_brush");
    })
}