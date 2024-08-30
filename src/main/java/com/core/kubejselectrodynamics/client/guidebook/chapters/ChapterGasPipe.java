package com.core.kubejselectrodynamics.client.guidebook.chapters;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.gaspipe.BlockGasPipeBuilder;
import com.core.kubejselectrodynamics.util.GasRegistrationUtil;
import com.core.kubejselectrodynamics.util.TextUtil;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.common.block.subtype.SubtypeGasPipe;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChapterGasPipe extends Chapter {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/logo.png"));

    public ChapterGasPipe(Module module) {
        super(module);
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(TextUtil.guidebookGas("pipe.list")).setSeparateStart());

        for (BlockGasPipeBuilder builder : BlockGasPipeBuilder.BUILDERS) {
            blankLine();
            pageData.add(new TextWrapperObject(Component.translatable(builder.getBuilderTranslationKey()).withStyle(ChatFormatting.ITALIC)).setSeparateStart());
            if (builder.isCorrodedByAcid()) {
                pageData.add(new TextWrapperObject(TextUtil.guidebookGas("pipe.corrodible")).setSeparateStart());
            }
            pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gases.pipecapacity", ChatFormatter.formatFluidMilibuckets(builder.getMaxTransfer()))).setSeparateStart());
            pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gases.pipepressure", ChatFormatter.getChatDisplayShort(builder.getMaxPressure(), DisplayUnit.PRESSURE_ATM))).setSeparateStart());
        }
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebookGas("pipe.title");
    }

    public static boolean valid() {
        return !BlockGasPipeBuilder.BUILDERS.isEmpty();
    }
}
