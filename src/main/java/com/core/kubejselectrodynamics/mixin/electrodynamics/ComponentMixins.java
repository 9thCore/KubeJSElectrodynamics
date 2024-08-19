package com.core.kubejselectrodynamics.mixin.electrodynamics;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.mixinterfaces.ComponentInterfaces;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.HashSet;

public class ComponentMixins {
    @Mixin(ComponentContainerProvider.class)
    public static class ComponentContainerProviderMixin implements ComponentInterfaces.IComponentContainerProvider {
        @Shadow(remap = false) protected String name;

        @Override
        public void kjsElectro$setName(String name) {
            this.name = name;
        }
    }

    @Mixin(ComponentElectrodynamic.class)
    public static class ComponentElectrodynamicMixin implements ComponentInterfaces.IComponentElectrodynamics {
        @Shadow(remap = false) protected HashSet<Direction> relativeInputDirections;

        @Shadow(remap = false) protected HashSet<Direction> relativeOutputDirections;

        @Shadow(remap = false) private LazyOptional<ICapabilityElectrodynamic>[] sidedOptionals;

        @Shadow(remap = false) private boolean isSided;

        @Override
        public void kjsElectro$clearInputDirections() {
            relativeInputDirections.clear();
        }

        @Override
        public void kjsElectro$clearOutputDirections() {
            relativeOutputDirections.clear();
        }

        @Override
        public boolean kjsElectro$hasSidedInput(Direction side) {
            if (!isSided) {
                return true;
            }
            if (side != null && sidedOptionals[side.ordinal()] != null && sidedOptionals[side.ordinal()].isPresent()) {
                return sidedOptionals[side.ordinal()].resolve().get().isEnergyReceiver();
            }
            return false;
        }

        @Override
        public boolean kjsElectro$hasSidedOutput(Direction side) {
            if (!isSided) {
                return true;
            }
            if (side != null && sidedOptionals[side.ordinal()] != null && sidedOptionals[side.ordinal()].isPresent()) {
                return sidedOptionals[side.ordinal()].resolve().get().isEnergyProducer();
            }
            return false;
        }

        @Override
        public boolean kjsElectro$hasSidedConnection(Direction side) {
            return kjsElectro$hasSidedInput(side) || kjsElectro$hasSidedOutput(side);
        }
    }

    @Mixin(ComponentFluidHandlerSimple.class)
    public static class ComponentFluidHandlerSimpleMixin implements ComponentInterfaces.IComponentFluidHandlerSimple {
        @Shadow(remap = false) private boolean isSided;

        @Shadow(remap = false) private LazyOptional<IFluidHandler>[] sidedOptionals;

        @Shadow(remap = false) private LazyOptional<IFluidHandler> inputOptional;

        @Shadow(remap = false) private LazyOptional<IFluidHandler> outputOptional;

        @Override
        public boolean kjsElectro$hasSidedInput(Direction side) {
            if (!isSided) {
                return true;
            }
            if (side != null && sidedOptionals[side.ordinal()] != null) {
                return sidedOptionals[side.ordinal()].equals(inputOptional);
            }
            return false;
        }

        @Override
        public boolean kjsElectro$hasSidedOutput(Direction side) {
            if (!isSided) {
                return true;
            }
            if (side != null && sidedOptionals[side.ordinal()] != null) {
                return sidedOptionals[side.ordinal()].equals(outputOptional);
            }
            return false;
        }

        @Override
        public boolean kjsElectro$hasSidedConnection(Direction side) {
            return kjsElectro$hasSidedInput(side) || kjsElectro$hasSidedOutput(side);
        }
    }
}
