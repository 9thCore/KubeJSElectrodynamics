package com.core.kubejselectrodynamics.client.guidebook.chapters;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.MotorBrushRegister;
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

public class ChapterConductorBrush extends Chapter {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/chapter/conductorbrush/logo.png"));

    public ChapterConductorBrush(Module module) {
        super(module);
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(TextUtil.guidebookMotorBrush("list")).setSeparateStart());

        for (Item item : MotorBrushRegister.brushes) {
            pageData.add(new TextWrapperObject(item.getDescription()).setSeparateStart());
            pageData.add(new TextWrapperObject(TextUtil.guidebookMotorBrush("format", getCycles(item))).setIndentions(1).setSeparateStart());
        }
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebookMotorBrush("title");
    }

    private static MutableComponent getCycles(Item item) {
        int damage = item.getDefaultInstance().getMaxDamage();
        return damage != 0 ? Component.literal(String.valueOf(damage)) : TextUtil.guidebookMotorBrush("inf");
    }

    public static boolean valid() {
        return !MotorBrushRegister.brushes.isEmpty();
    }
}
