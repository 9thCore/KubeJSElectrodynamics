package com.core.kubejselectrodynamics.mixin.electrodynamics;

import electrodynamics.api.item.IItemElectric;
import electrodynamics.prefab.item.ElectricItemProperties;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IItemElectric.class)
public interface IItemElectricMixin {
    @Shadow(remap = false) ElectricItemProperties getElectricProperties();

    /**
     * @author 9thCore
     * @reason Patch method to add battery switching supporting custom battery types - Cannot use @Inject as it is not supported for Interfaces
     */
    @Overwrite(remap = false)
    default void swapBatteryPackFirstItem(ItemStack tool, Player player) {
        IItemElectric electricItem = (IItemElectric) tool.getItem();

        if (electricItem.isEnergyStorageOnly() || electricItem.cannotHaveBatterySwapped()) {
            return;
        }

        Inventory inv = player.getInventory();

        for (int i = 0; i < inv.items.size(); i++) {
            ItemStack playerItem = inv.getItem(i).copy();

            if (!playerItem.isEmpty() && playerItem.getItem() instanceof IItemElectric electric && electric.isEnergyStorageOnly() && electric.getJoulesStored(playerItem) > 0 && electric.getElectricProperties().extract.getVoltage() == electricItem.getElectricProperties().extract.getVoltage()) {
                /* PATCH: CAPACITY CHECK */
                if (electric.getElectricProperties().capacity > electricItem.getElectricProperties().capacity) {
                    // Assume electrical items are rated for some maximum power value, their default battery
                    return;
                }
                /* PATCH: RECEIVE CHECK */
                if (electric.getElectricProperties().receive.getVoltage() != electricItem.getElectricProperties().receive.getVoltage()) {
                    // Assume electrical items are rated for a specific receiving voltage, as well as extract
                    return;
                }
                ItemStack currBattery = electricItem.getCurrentBattery(tool);
                if (currBattery.isEmpty()) {
                    return;
                }
                double joulesStored = electricItem.getJoulesStored(tool);
                /* PATCH: BATTERY SWITCH */
                electricItem.setCurrentBattery(tool, playerItem);
                IItemElectric.setEnergyStored(tool, electric.getJoulesStored(playerItem));
                IItemElectric.setEnergyStored(currBattery, joulesStored);
                inv.setItem(i, ItemStack.EMPTY);
                for (int j = 0; j < inv.items.size(); j++) {
                    ItemStack item = inv.getItem(j);
                    if (item.isEmpty()) {
                        inv.setItem(j, currBattery);
                        player.level().playSound(null, player.getOnPos(), ElectrodynamicsSounds.SOUND_BATTERY_SWAP.get(), SoundSource.PLAYERS, 0.25F, 1.0F);
                        return;
                    }
                }
                return;
            }

        }

    }

    /**
     * @author 9thCore
     * @reason Patch method to get capacity of battery object instead of current item
     */
    @Overwrite(remap = false)
    default void setCurrentBattery(ItemStack tool, ItemStack battery) {
        CompoundTag tag = tool.getOrCreateTag();
        tag.remove("currentbattery");
        if (battery.getItem() instanceof IItemElectric electric) {
            // If the battery is an electric item, use its capacity
            IItemElectric.setMaximumCapacity(tool, electric.getMaximumCapacity(battery));
        } else {
            // Failsafe, use the capacity of the current item instead
            IItemElectric.setMaximumCapacity(tool, this.getElectricProperties().capacity);
        }
        tag.put("currentbattery", battery.save(new CompoundTag()));
    }

    /**
     * @author 9thCore
     * @reason Patch method to check capacity and extract voltage
     */
    @Overwrite(remap = false)
    static boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (action == null || action == ClickAction.PRIMARY) {
            return false;
        }

        if (((IItemElectric) stack.getItem()).cannotHaveBatterySwapped()) {
            return false;
        }

        if (!(other.getItem() instanceof IItemElectric) || (other.getItem() instanceof IItemElectric electric && !electric.isEnergyStorageOnly())) {
            return false;
        }

        IItemElectric thisElectric = (IItemElectric) stack.getItem();
        IItemElectric otherElectric = (IItemElectric) other.getItem();

        if (otherElectric.getJoulesStored(other) == 0 || otherElectric.getElectricProperties().receive.getVoltage() != thisElectric.getElectricProperties().receive.getVoltage()) {
            return false;
        }

        /* PATCH: EXTRA CHECKS */
        if (thisElectric.getElectricProperties().extract.getVoltage() != otherElectric.getElectricProperties().extract.getVoltage()) {
            return false;
        }
        if (thisElectric.getElectricProperties().capacity < otherElectric.getElectricProperties().capacity) {
            return false;
        }

        ItemStack currBattery = thisElectric.getCurrentBattery(stack);

        double joulesStored = thisElectric.getJoulesStored(stack);

        IItemElectric.setEnergyStored(currBattery, joulesStored);

        access.set(currBattery);

        IItemElectric.setEnergyStored(stack, otherElectric.getJoulesStored(other));

        thisElectric.setCurrentBattery(stack, other);

        player.level().playLocalSound(player.getX(), player.getY(), player.getZ(), ElectrodynamicsSounds.SOUND_BATTERY_SWAP.get(), SoundSource.PLAYERS, 0.25F, 1.0F, false);

        return true;
    }
}
