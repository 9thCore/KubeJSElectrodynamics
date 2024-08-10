package com.core.kubejselectrodynamics.item.canister;

import com.core.kubejselectrodynamics.item.CustomItemExtension;
import electrodynamics.api.capability.types.fluid.RestrictedFluidHandlerItemStack;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.prefab.utilities.CapabilityUtils;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class CustomItemCanister extends ItemCanister implements CustomItemExtension {
    private final ItemCanisterBuilder builder;

    public CustomItemCanister(Properties properties, Supplier<CreativeModeTab> creativeTab, ItemCanisterBuilder builder) {
        super(properties, creativeTab);
        this.builder = builder;
    }

    /**
     * Method originally from Electrodynamics' source code (addCreativeModeItems), adapted to fit custom implementation
     */
    @Override
    public Collection<ItemStack> getEntries() {
        Collection<ItemStack> entries = new ArrayList<>();
        if (!CapabilityUtils.isFluidItemNull()) {
            for (Fluid liq : ForgeRegistries.FLUIDS.getValues()) {
                if (liq.isSame(Fluids.EMPTY)) {
                    continue;
                }
                ItemStack temp = new ItemStack(this);
                temp.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> ((RestrictedFluidHandlerItemStack) h).setFluid(new FluidStack(liq, builder.getCapacity())));
                entries.add(temp);
            }
        }
        return entries;
    }

    /**
     * Method originally from Electrodynamics' source code, adapted to fit custom implementation
     */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new RestrictedFluidHandlerItemStack.SwapEmpty(stack, stack, builder.getCapacity());
    }

    /**
     * Method originally from Electrodynamics' source code, adapted to fit custom implementation
     */
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (!CapabilityUtils.isFluidItemNull()) {
            stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(h -> {
                if (!((RestrictedFluidHandlerItemStack) h).getFluid().isEmpty()) {
                    RestrictedFluidHandlerItemStack cap = (RestrictedFluidHandlerItemStack) h;
                    tooltip.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(cap.getFluidInTank(0).getAmount()), ChatFormatter.formatFluidMilibuckets(builder.getCapacity())).withStyle(ChatFormatting.GRAY));
                    tooltip.add(cap.getFluid().getDisplayName().copy().withStyle(ChatFormatting.DARK_GRAY));
                }
            });
        }
    }
}
