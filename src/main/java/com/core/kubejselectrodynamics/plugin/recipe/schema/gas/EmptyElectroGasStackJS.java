package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

public final class EmptyElectroGasStackJS extends ElectroGasStackJS {
    public static ElectroGasStackJS INSTANCE = new EmptyElectroGasStackJS();

    @Override
    public ElectroGasStackJS kjs$copy(double amount) {
        return this;
    }

    @Override
    public String getID() {
        return "electrodynamics:empty";
    }

    @Override
    public void setAmount(double amount) {}

    @Override
    public double getAmount() {
        return 1;
    }

    @Override
    public void setPressure(int pressure) {}

    @Override
    public int getPressure() {
        return 1;
    }

    @Override
    public void setTemperature(double temperature) {}

    @Override
    public double getTemperature() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
