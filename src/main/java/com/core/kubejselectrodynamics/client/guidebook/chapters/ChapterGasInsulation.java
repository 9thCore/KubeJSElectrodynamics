package com.core.kubejselectrodynamics.client.guidebook.chapters;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.InsulationUtils;
import com.core.kubejselectrodynamics.util.TextUtil;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Map;

public class ChapterGasInsulation extends Chapter {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/chapter/gasinsulation/logo.png"));

    public ChapterGasInsulation(Module module) {
        super(module);
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(TextUtil.guidebookGasInsulation("list")).setSeparateStart());

        for (Map.Entry<Item, Double> entry : InsulationUtils.insulationEffectiveness.entrySet()) {
            double percentage = (entry.getValue() - 1) * 100;
            MutableComponent value = ChatFormatter.formatDecimals(percentage, 1);
            pageData.add(new TextWrapperObject(entry.getKey().getDescription()).setSeparateStart());
            pageData.add(new TextWrapperObject(TextUtil.guidebookGasInsulation("format", value.append("%"))).setSeparateStart().setIndentions(1));
        }
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebookGasInsulation("title");
    }

    public static boolean valid() {
        return !InsulationUtils.insulationEffectiveness.isEmpty();
    }
}
