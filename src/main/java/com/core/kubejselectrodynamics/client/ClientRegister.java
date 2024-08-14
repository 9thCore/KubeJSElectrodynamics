package com.core.kubejselectrodynamics.client;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.BlockEntityOptionalRenderer;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.motor.dc.TileCustomMotorDC;
import com.core.kubejselectrodynamics.client.guidebook.ModuleKubeJSElectro;
import com.core.kubejselectrodynamics.client.tile.RenderBase;
import com.core.kubejselectrodynamics.client.tile.RenderCustomMotorDC;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = KubeJSElectrodynamics.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegister {
    public static void setup() {
        ScreenGuidebook.addGuidebookModule(new ModuleKubeJSElectro());
    }

    @SubscribeEvent
    public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
        for (BlockEntityOptionalRenderer<?, ?> optional : BlockEntityOptionalRenderer.blockEntityList) {
            if (optional.getRenderer().get() instanceof BlockEntityRendererProvider provider) {
                event.registerBlockEntityRenderer(optional.getRenderedType(), provider);
            } else {
                KubeJSElectrodynamics.LOGGER.warn("Could not register BER " + optional.name + ": Renderer provided not an instance of BlockEntityRendererProvider!");
            }
        }
    }
}
