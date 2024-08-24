ElectrodynamicsEvents.gasTankInsulation(event => {
    // Too high a value! It's multiplicative, so 6 of these (maxed) in a gas tank results in 4096 insulation! Practically no heat loss under normal conditions
    event.register("kubejs:endgame_insulation", 4);
})