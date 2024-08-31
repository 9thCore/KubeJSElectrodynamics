package com.core.kubejselectrodynamics.util;

import dynamicelectricity.core.utils.UtilsText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MotorBrushRegister {
    public static final Map<Item, Integer> brushes = new HashMap<>();

    public static void register(Item item, int tooltipIndex) {
        brushes.put(item, tooltipIndex);
    }

    public static Component getTooltip(ItemStack stack) {
        if (stack.getMaxDamage() > 0) {
            return UtilsText.tooltip("condudctorbrushdurability", stack.getMaxDamage() - stack.getDamageValue(), stack.getMaxDamage()).withStyle(ChatFormatting.GRAY);
        }
        return TextUtil.tooltipMotorBrush("inf").withStyle(ChatFormatting.GRAY);
    }

    public static int getTooltipIndex(ItemStack stack) {
        return brushes.getOrDefault(stack.getItem(), 0);
    }
}
