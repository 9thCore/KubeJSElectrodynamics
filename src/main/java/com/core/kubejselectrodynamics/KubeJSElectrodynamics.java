package com.core.kubejselectrodynamics;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(KubeJSElectrodynamics.MODID)
public class KubeJSElectrodynamics
{
    public static final String MODID = "kubejselectrodynamics";
    private static final Logger LOGGER = LogUtils.getLogger();

    public KubeJSElectrodynamics() {}

    public static void LogInfo(Object message) {
        LOGGER.info(message.toString());
    }
}
