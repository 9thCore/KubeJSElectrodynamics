package com.core.kubejselectrodynamics.client;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.BlockEntityOptionalRenderer;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.wire.BlockItemWireBuilder;
import com.core.kubejselectrodynamics.block.wire.BlockWireBuilder;
import com.core.kubejselectrodynamics.client.guidebook.ModuleKubeJSElectro;
import com.core.kubejselectrodynamics.client.tile.RenderCustomLogisticalWire;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.connect.BlockLogisticalWire;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                KubeJSElectrodynamics.LOGGER.warn("Could not register BER {}: Renderer provided not an instance of BlockEntityRendererProvider!", optional.name);
            }
        }
        event.registerBlockEntityRenderer(TileRegister.LOGISTICAL_WIRE_TYPE.getType(), RenderCustomLogisticalWire::new);
    }

    @SubscribeEvent
    public static void registerColoredBlocks(RegisterColorHandlersEvent.Block event) {
        BlockWireBuilder.builders.forEach(blockBuilder -> {
            if (blockBuilder.getSubtype().wireClass.conductsRedstone) {
                event.register((state, level, pos, tintIndex) -> {
                    if (tintIndex == 0) {
                        return blockBuilder.getWireColor().color.color();
                    }
                    if (tintIndex != 1) {
                        return Color.WHITE.color();
                    }
                    if (state.getValue(BlockMachine.ON)) {
                        return BlockLogisticalWire.REDSTONE_ON.color();
                    }
                    return BlockLogisticalWire.REDSTONE_OFF.color();
                }, blockBuilder.get());
            } else {
                event.register((state, level, pos, tintIndex) -> {
                    if (tintIndex == 0) {
                        return blockBuilder.getWireColor().color.color();
                    }
                    return Color.WHITE.color();
                }, blockBuilder.get());
            }
        });
    }

    @SubscribeEvent
    public static void registerColoredItems(RegisterColorHandlersEvent.Item event) {
        BlockItemWireBuilder.builders.forEach(itemBuilder -> event.register((stack, index) -> {
            if (index == 0 && itemBuilder.blockBuilder.hasBaseTexture()) {
                return itemBuilder.blockBuilder.getWireTint().color();
            } else if (index == 1) {
                return itemBuilder.blockBuilder.getWireColor().color.color();
            }
            return Color.WHITE.color();
        }, itemBuilder.get()));
    }
}
