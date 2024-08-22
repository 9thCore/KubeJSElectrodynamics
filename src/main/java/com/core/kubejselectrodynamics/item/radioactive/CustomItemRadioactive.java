package com.core.kubejselectrodynamics.item.radioactive;

import com.core.kubejselectrodynamics.item.CustomItemTab;
import com.core.kubejselectrodynamics.util.RadiationUtil;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.item.ItemRadioactive;

public class CustomItemRadioactive extends ItemRadioactive {
    private final IRadioactiveItemBuilder<?> builder;

    public CustomItemRadioactive(IRadioactiveItemBuilder<?> builder, Properties properties) {
        super(properties, CustomItemTab.TAB);
        this.builder = builder;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level world = entity.getCommandSenderWorld();
        if (world.getLevelData().getGameTime() % 10 == 0) {
            double totstrength = RadiationUtil.getRadiation(stack);
            RadiationSystem.emitRadiationFromLocation(world, new Location(entity), builder.getRadiationRadius(), totstrength);
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
        if (world.getLevelData().getGameTime() % 10 == 0) {
            double totstrength = RadiationUtil.getRadiation(stack);
            RadiationSystem.emitRadiationFromLocation(world, new Location(entity), builder.getRadiationRadius(), totstrength);
        }
    }
}
