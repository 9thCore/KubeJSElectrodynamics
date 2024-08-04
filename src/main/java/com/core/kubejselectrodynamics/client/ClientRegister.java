package com.core.kubejselectrodynamics.client;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.client.guidebook.ModuleKubeJSElectro;
import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = KubeJSElectrodynamics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClientRegister {
    public static void setup() {
        ScreenGuidebook.addGuidebookModule(new ModuleKubeJSElectro());
    }
}
