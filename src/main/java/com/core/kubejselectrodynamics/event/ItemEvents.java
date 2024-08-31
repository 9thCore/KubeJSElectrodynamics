package com.core.kubejselectrodynamics.event;

import com.core.kubejselectrodynamics.util.MotorBrushRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemEvents {
    public static void tooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (!MotorBrushRegister.brushes.containsKey(item)) {
            return;
        }
        event.getToolTip().add(MotorBrushRegister.getTooltipIndex(stack), MotorBrushRegister.getTooltip(stack));
    }
}
