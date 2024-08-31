package com.core.kubejselectrodynamics.client.guidebook.chapters;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.fluidpipe.BlockFluidPipeBuilder;
import com.core.kubejselectrodynamics.util.TextUtil;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class ChapterFluidPipe extends Chapter {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/chapter/fluidpipe/logo.png"));

    public ChapterFluidPipe(Module module) {
        super(module);
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(TextUtil.guidebookFluid("pipe.list")).setSeparateStart());

        for (BlockFluidPipeBuilder builder : BlockFluidPipeBuilder.BUILDERS) {
            blankLine();
            pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.fluids.pipecapacity", builder.getMaterialName(), builder.getMaxTransfer())).setSeparateStart().setIndentions(1));
        }
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebookFluid("pipe.title");
    }

    public static boolean valid() {
        return !BlockFluidPipeBuilder.BUILDERS.isEmpty();
    }
}
