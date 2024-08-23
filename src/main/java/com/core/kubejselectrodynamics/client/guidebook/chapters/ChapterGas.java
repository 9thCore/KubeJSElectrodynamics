package com.core.kubejselectrodynamics.client.guidebook.chapters;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.GasRegistrationUtil;
import com.core.kubejselectrodynamics.util.TextUtil;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasStack;
import electrodynamics.client.guidebook.ScreenGuidebook;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.OnKeyPress;
import electrodynamics.client.guidebook.utils.pagedata.graphics.AbstractGraphicWrapper;
import electrodynamics.client.guidebook.utils.pagedata.graphics.GasWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.pagedata.text.TextWrapperObject;
import electrodynamics.compatibility.jei.JeiBuffer;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ChapterGas extends Chapter {
    private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(KubeJSElectrodynamics.MODID, "textures/guidebook/chapter/gas/logo.png"));

    public ChapterGas(Module module) {
        super(module);
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(TextUtil.guidebookGas("list.all")).setSeparateStart());

        // KubeJS gas list
        for (RegistryObject<Gas> gas : GasRegistrationUtil.KJS_REGISTERED_GASSES) {
            if (gas.get().isEmpty()) {
                continue;
            }
            pageData.add(new GasWrapperObject(0, 0, 32, 32, 36, gas.get(), new AbstractGraphicWrapper.GraphicTextDescriptor(36, 11, gas.get().getDescription()))
                .onTooltip((graphics, xAxis, yAxis, screen) -> {
                    if (JeiBuffer.isJeiInstalled()) {
                        List<FormattedCharSequence> tooltips = new ArrayList<>();
                        tooltips.add(ElectroTextUtils.tooltip("guidebookjeirecipe").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                        tooltips.add(ElectroTextUtils.tooltip("guidebookjeiuse").withStyle(ChatFormatting.GRAY).getVisualOrderText());
                        graphics.renderTooltip(screen.getFontRenderer(), tooltips, xAxis, yAxis);
                    }
                }).onKeyPress(new OnKeyPress() {
                    @Override
                    public void onKeyPress(int keyCode, int scanCode, int modifiers, int x, int y, int xAxis, int yAxis, ScreenGuidebook screen) { }

                    @Override
                    public Object getJeiLookup() {
                        return new GasStack(gas.get(), 1, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL);
                    }
                })
            );
        }

        // KubeJS condensable gas list
        blankLine();
        pageData.add(new TextWrapperObject(TextUtil.guidebookGas("list.condensable")).setSeparateStart());

        for (RegistryObject<Gas> gas : GasRegistrationUtil.getCondensableGasses()) {
            if (gas.get().isEmpty() || gas.get().getCondensedFluid() == null) {
                continue;
            }
            blankLine();
            pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gases.gas", gas.get().getDescription()).withStyle(ChatFormatting.ITALIC)).setSeparateStart());
            pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gases.condensedfluid", gas.get().getCondensedFluid().getFluidType().getDescription())).setSeparateStart());
            pageData.add(new TextWrapperObject(ElectroTextUtils.guidebook("chapter.gases.condtemp", ChatFormatter.getChatDisplayShort(gas.get().getCondensationTemp(), DisplayUnit.TEMPERATURE_KELVIN))).setSeparateStart());
        }
    }

    @Override
    public AbstractGraphicWrapper<?> getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return TextUtil.guidebookGas("title");
    }

    public static boolean valid() {
        return !GasRegistrationUtil.KJS_REGISTERED_GASSES.isEmpty();
    }
}
