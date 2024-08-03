package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.WrappedJS;
import dev.latvian.mods.rhino.NativeObject;
import electrodynamics.api.gas.GasStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * JS object handling Electrodynamics gas.
 */
public abstract class ElectroGasStackJS implements WrappedJS, InputGas, OutputGas {
    double chance = 1.0D;
    public static ElectroGasStackJS of(@Nullable Object from) {
        if (from == null) {
            return EmptyElectroGasStackJS.INSTANCE;
        } else if (from instanceof ElectroGasStackJS stack) {
            return stack;
        } else if (from instanceof GasStack stack) {
            return new BoundElectroGasStackJS(stack);
        } else if (from instanceof ResourceLocation location) {
            return UnboundElectroGasJS.of(location.toString());
        } else if (from instanceof String string) {
            return UnboundElectroGasJS.of(string);
        } else if (from instanceof NativeObject nativeObject) {
            return UnboundElectroGasJS.of(nativeObject);
        } else if (from instanceof JsonObject object) {
            return UnboundElectroGasJS.of(object);
        } else {
            throw new IllegalArgumentException("Cannot read gas!");
        }
    }

    @Info("Return obtainment chance of gas. Not all recipes support this.")
    public double getChance() {
        return chance;
    }

    @Info("Create a copy of the current instance with the given chance.")
    public ElectroGasStackJS withChance(double chance) {
        ElectroGasStackJS gas = copy();
        gas.chance = chance;
        return gas;
    }

    @Info("Create a copy of the current instance.")
    public ElectroGasStackJS copy() {
        return kjs$copy(kjs$getAmount());
    }

    @Info("Returns the gas amount as a double.")
    @Override
    public double kjs$getAmount() {
        return getAmount();
    }

    @Info("Returns whether the gas is empty.")
    @Override
    public boolean kjs$isEmpty() {
        return isEmpty();
    }

    @Info("Create a copy of the current instance with the given amount (double).")
    public ElectroGasStackJS withAmount(double amount) {
        ElectroGasStackJS gas = copy();
        gas.setAmount(amount);
        return gas;
    }

    @Info("Create a copy of the current instance with the given pressure (integer).")
    public ElectroGasStackJS withPressure(int pressure) {
        ElectroGasStackJS gas = copy();
        gas.setPressure(pressure);
        return gas;
    }

    @Info("Create a copy of the current instance with the given temperature (double).")
    public ElectroGasStackJS withTemperature(double temperature) {
        ElectroGasStackJS gas = copy();
        gas.setTemperature(temperature);
        return gas;
    }

    @Info("Write gas to JSON.")
    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("amount", getAmount());
        object.addProperty("chance", getChance());
        object.addProperty("gas", getID());
        object.addProperty("pressure", getPressure());
        object.addProperty("temp", getTemperature());
        return object;
    }

    @Info("Create a copy of the current instance with the given amount.")
    @Override
    public abstract ElectroGasStackJS kjs$copy(double amount);
    @Info("Return the ID of the current gas. May either be a tag or gas key.")
    public abstract String getID();
    @Info("Set the amount of the current instance as a double.")
    public abstract void setAmount(double amount);
    @Info("Get the amount of the current instance as a double.")
    public abstract double getAmount();
    @Info("Set the pressure of the current instance as an integer.")
    public abstract void setPressure(int pressure);
    @Info("Get the pressure of the current instance as an integer.")
    public abstract int getPressure();
    @Info("Set the temperature of the current instance as a double.")
    public abstract void setTemperature(double temperature);
    @Info("Get the temperature of the current instance as a double.")
    public abstract double getTemperature();
    @Info("Returns whether the gas is empty.")
    public abstract boolean isEmpty();
}
