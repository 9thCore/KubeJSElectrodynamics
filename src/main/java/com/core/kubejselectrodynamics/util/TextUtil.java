package com.core.kubejselectrodynamics.util;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextUtil {
    public static MutableComponent guidebook(String key, Object... additional) {
        return getText("guidebook", key, additional);
    }

    public static MutableComponent guidebookMotor(String key, Object... additional) {
        return guidebook("chapter.motor." + key, additional);
    }

    public static MutableComponent guidebookMotorBrush(String key, Object... additional) {
        return guidebookMotor("brush." + key, additional);
    }

    public static MutableComponent guidebookFluid(String key, Object... additional) {
        return guidebook("chapter.fluid." + key, additional);
    }

    public static MutableComponent guidebookGas(String key, Object... additional) {
        return guidebook("chapter.gas." + key, additional);
    }

    public static MutableComponent guidebookGasInsulation(String key, Object... additional) {
        return guidebookGas("insulation." + key, additional);
    }

    public static MutableComponent guidebookFuelRod(String key, Object... additional) {
        return guidebook("chapter.fuelrod." + key, additional);
    }

    public static MutableComponent tooltip(String key, Object... additional) {
        return getText("tooltip", key, additional);
    }

    public static MutableComponent tooltipMotorBrush(String key, Object... additional) {
        return tooltip("motor.brush." + key, additional);
    }

    public static MutableComponent getText(String prefix, String postfix, Object... additional) {
        return Component.translatable(prefix + "." + KubeJSElectrodynamics.MODID + "." + postfix, additional);
    }
}
