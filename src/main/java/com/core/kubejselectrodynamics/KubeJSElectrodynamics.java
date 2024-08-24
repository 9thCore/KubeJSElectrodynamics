package com.core.kubejselectrodynamics;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.client.ClientRegister;
import com.core.kubejselectrodynamics.item.CustomItemTab;
import com.core.kubejselectrodynamics.plugin.KubePlugin;
import com.core.kubejselectrodynamics.plugin.event.NuclearScienceEvents;
import com.core.kubejselectrodynamics.plugin.event.server.FuelRodEventJS;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;
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
    public static final Logger LOGGER = LogUtils.getLogger();

    public KubeJSElectrodynamics() {
        CustomItemTab.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileRegister.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.addListener(this::onDatapackSync);
    }

    public void onDatapackSync(OnDatapackSyncEvent event) {
        if (KubePlugin.ModHolder.NUCLEARSCIENCE.valid()) {
            NuclearScienceEvents.FUEL_ROD.post(new FuelRodEventJS());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientRegister::setup);
    }
}
