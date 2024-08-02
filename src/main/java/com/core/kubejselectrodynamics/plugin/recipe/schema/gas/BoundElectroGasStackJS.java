package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import electrodynamics.api.gas.GasStack;

public class BoundElectroGasStackJS extends ElectroGasStackJS {
    private final GasStack stack;

    public BoundElectroGasStackJS(GasStack stack) {
        this.stack = stack;
    }

    public String getID() {
        return stack.getGas().toString();
    }

    @Override
    public ElectroGasStackJS kjs$copy(double amount) {
        BoundElectroGasStackJS stack = new BoundElectroGasStackJS(this.stack);
        stack.setAmount(amount);
        return stack;
    }

    @Override
    public void setAmount(double amount) {
        stack.setAmount(amount);
    }

    @Override
    public double getAmount() {
        return stack.getAmount();
    }

    @Override
    public void setPressure(int pressure) {
        stack.bringPressureTo(pressure);
    }

    @Override
    public int getPressure() {
        return stack.getPressure();
    }

    @Override
    public void setTemperature(double temperature) {
        stack.heat(temperature - getTemperature());
    }

    @Override
    public double getTemperature() {
        return stack.getTemperature();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
