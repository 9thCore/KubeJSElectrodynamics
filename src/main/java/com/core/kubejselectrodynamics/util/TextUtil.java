package com.core.kubejselectrodynamics.util;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextUtil {
    public static MutableComponent guidebook(String key, Object... additional) {
        return getText("guidebook", key, additional);
    }

    public static MutableComponent guidebookGas(String key, Object... additional) {
        return guidebook("chapter.gas." + key, additional);
    }

    public static MutableComponent getText(String prefix, String postfix, Object... additional) {
        return Component.translatable(prefix + "." + KubeJSElectrodynamics.MODID + "." + postfix, additional);
    }
}
