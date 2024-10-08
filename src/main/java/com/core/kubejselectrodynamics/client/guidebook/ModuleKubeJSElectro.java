package com.core.kubejselectrodynamics.client.guidebook;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.client.guidebook.chapters.*;
import com.core.kubejselectrodynamics.plugin.KubePlugin;
import com.core.kubejselectrodynamics.util.TextUtil;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ModuleKubeJSElectro extends Module {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/logo.png"));

    @Override
    public void addChapters() {
        if (ChapterGas.valid()) {
            chapters.add(new ChapterGas(this));
        }

        if (ChapterGasPipe.valid()) {
            chapters.add(new ChapterGasPipe(this));
        }

        if (ChapterFluidPipe.valid()) {
            chapters.add(new ChapterFluidPipe(this));
        }

        if (ChapterGasInsulation.valid()) {
            chapters.add(new ChapterGasInsulation(this));
        }

        if (KubePlugin.ModHolder.NUCLEARSCIENCE.valid() && ChapterFuelRod.valid()) {
            chapters.add(new ChapterFuelRod(this));
        }

        if (KubePlugin.ModHolder.DYNAMICELECTRICITY.valid() && ChapterConductorBrush.valid()) {
            chapters.add(new ChapterConductorBrush(this));
        }
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebook("title");
    }
}
