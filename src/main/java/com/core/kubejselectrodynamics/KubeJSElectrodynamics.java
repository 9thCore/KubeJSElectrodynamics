package com.core.kubejselectrodynamics;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.client.ClientRegister;
import com.core.kubejselectrodynamics.item.CustomItemTab;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(KubeJSElectrodynamics.MODID)
@Mod.EventBusSubscriber(modid = KubeJSElectrodynamics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KubeJSElectrodynamics
{
    public static final String MODID = "kubejselectrodynamics";
    private static final Logger LOGGER = LogUtils.getLogger();

    public KubeJSElectrodynamics() {
        CustomItemTab.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileRegister.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientRegister::setup);
    }

    public static void LogInfo(Object message) {
        LOGGER.info(message.toString());
    }
}
