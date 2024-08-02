package com.core.kubejselectrodynamics.plugin.recipe.schema.gas;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.plugin.recipe.schema.Components;
import com.google.gson.JsonObject;
import dev.latvian.mods.rhino.NativeObject;
import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

public class UnboundElectroGasJS extends ElectroGasStackJS {
    double amount = 1000.0D;
    int pressure = 1;
    double temperature = 273.0D;
    final TagKey<Gas> tag;

    public UnboundElectroGasJS(@NotNull TagKey<Gas> tag) {
        this.tag = tag;
    }

    public static UnboundElectroGasJS of(Object from) {
        if (from instanceof String string) {
            TagKey<Gas> tag = TagKey.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, new ResourceLocation(string));
            return new UnboundElectroGasJS(tag);
        } else if (from instanceof NativeObject nativeObject) {
            UnboundElectroGasJS stack = UnboundElectroGasJS.of(nativeObject.get("gas"));
            if (nativeObject.containsKey("amount")) {
                stack.amount = (double)nativeObject.get("amount");
            } else if (nativeObject.containsKey("count")) {
                stack.amount = (double)nativeObject.get("count");
            }
            if (nativeObject.containsKey("chance")) {
                stack.chance = (double)nativeObject.get("chance");
            }
            if (nativeObject.containsKey("pressure")) {
                stack.pressure = Components.CastToInt(nativeObject, "pressure");
            }
            if (nativeObject.containsKey("temperature")) {
                stack.temperature = (double)nativeObject.get("temperature");
            }
            return stack;
        } else if (from instanceof JsonObject object) {
            UnboundElectroGasJS stack = UnboundElectroGasJS.of(object.get("gas").getAsString());
            if (object.has("amount")) {
                stack.amount = object.get("amount").getAsDouble();
            } else if (object.has("count")) {
                stack.amount = object.get("count").getAsDouble();
            }
            if (object.has("chance")) {
                stack.chance = object.get("chance").getAsDouble();
            }
            if (object.has("pressure")) {
                stack.pressure = object.get("pressure").getAsInt();
            }
            if (object.has("temperature")) {
                stack.temperature = object.get("temperature").getAsDouble();
            }
            return stack;
        } else {
            throw new IllegalArgumentException("Invalid gas object!");
        }
    }

    @Override
    public String getID() {
        return tag.location().toString();
    }

    @Override
    public boolean isEmpty() {
        return amount <= 0L;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    @Override
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getPressure() {
        return pressure;
    }

    @Override
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public ElectroGasStackJS kjs$copy(double amount) {
        UnboundElectroGasJS gas = new UnboundElectroGasJS(this.tag);
        gas.chance = this.chance;
        gas.amount = amount;
        gas.temperature = this.temperature;
        gas.pressure = this.pressure;
        return gas;
    }
}
