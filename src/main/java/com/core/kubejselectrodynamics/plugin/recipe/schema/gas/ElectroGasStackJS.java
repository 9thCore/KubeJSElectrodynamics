package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.util.WrappedJS;
import dev.latvian.mods.rhino.NativeObject;
import electrodynamics.api.gas.GasStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

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

    public double getChance() {
        return chance;
    }

    public ElectroGasStackJS withChance(double chance) {
        ElectroGasStackJS gas = copy();
        gas.chance = chance;
        return gas;
    }

    public ElectroGasStackJS copy() {
        return kjs$copy(kjs$getAmount());
    }

    @Override
    public double kjs$getAmount() {
        return getAmount();
    }

    @Override
    public boolean kjs$isEmpty() {
        return isEmpty();
    }

    public ElectroGasStackJS withAmount(double amount) {
        ElectroGasStackJS gas = copy();
        gas.setAmount(amount);
        return gas;
    }

    public ElectroGasStackJS withPressure(int pressure) {
        ElectroGasStackJS gas = copy();
        gas.setPressure(pressure);
        return gas;
    }

    public ElectroGasStackJS withTemperature(double temperature) {
        ElectroGasStackJS gas = copy();
        gas.setTemperature(temperature);
        return gas;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("amount", getAmount());
        object.addProperty("chance", getChance());
        object.addProperty("gas", getID());
        object.addProperty("pressure", getPressure());
        object.addProperty("temp", getTemperature());
        return object;
    }

    @Override
    public abstract ElectroGasStackJS kjs$copy(double amount);
    public abstract String getID();
    public abstract void setAmount(double amount);
    public abstract double getAmount();
    public abstract void setPressure(int pressure);
    public abstract int getPressure();
    public abstract void setTemperature(double temperature);
    public abstract double getTemperature();
    public abstract boolean isEmpty();
}
