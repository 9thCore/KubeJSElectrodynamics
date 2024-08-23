package com.core.kubejselectrodynamics.client.guidebook.chapters;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.RadiationUtil;
import com.core.kubejselectrodynamics.util.TextUtil;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class ChapterFuelRod extends Chapter {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/chapter/fuelrod/logo.png"));

    public ChapterFuelRod(Module module) {
        super(module);
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(TextUtil.guidebookFuelRod("list")).setSeparateStart());

        RadiationUtil.fuelRods.forEach((item, value) -> {
            pageData.add(new TextWrapperObject(item.getDescription()).setSeparateStart());
            pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.maxtemp", TileFissionReactorCore.MELTDOWN_TEMPERATURE_CALC / 8 * value + 15)).setIndentions(1).setSeparateStart());
            pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.fissionreactor.cycles", getCycles(item))).setIndentions(1).setSeparateStart());
        });
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebookFuelRod("title");
    }

    private static MutableComponent getCycles(Item item) {
        int damage = item.getDefaultInstance().getMaxDamage();
        return damage != 0 ? Component.literal(String.valueOf(damage)) : TextUtil.guidebookFuelRod("inf");
    }
}
