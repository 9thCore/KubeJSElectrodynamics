package com.core.kubejselectrodynamics.mixin;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.util.ComponentInterfaces;
import electrodynamics.api.capability.types.electrodynamic.ICapabilityElectrodynamic;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

public class ComponentMixins {
    @Mixin(ComponentContainerProvider.class)
    public static class ComponentContainerProviderMixin implements ComponentInterfaces.ComponentContainerProviderInterface {
        @Shadow protected String name;

        @Override
        public void kjsElectro$setName(String name) {
            this.name = name;
        }
    }

    @Mixin(ComponentElectrodynamic.class)
    public static abstract class ComponentElectrodynamicMixin implements ComponentInterfaces.ComponentElectrodynamicInterface {
        @Shadow protected HashSet<Direction> relativeInputDirections;

        @Shadow protected HashSet<Direction> relativeOutputDirections;

        @Shadow private LazyOptional<ICapabilityElectrodynamic>[] sidedOptionals;

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
            if (relativeInputDirections.size() == 0) {
                return true;
            }
            if (sidedOptionals[side.ordinal()].isPresent()) {
                return sidedOptionals[side.ordinal()].resolve().get().isEnergyReceiver();
            }
            return false;
        }

        @Override
        public boolean kjsElectro$hasSidedOutput(Direction side) {
            if (relativeOutputDirections.size() == 0) {
                return true;
            }
            if (sidedOptionals[side.ordinal()].isPresent()) {
                return sidedOptionals[side.ordinal()].resolve().get().isEnergyProducer();
            }
            return false;
        }

        @Override
        public boolean kjsElectro$hasSidedConnection(Direction side) {
            return kjsElectro$hasSidedInput(side) || kjsElectro$hasSidedOutput(side);
        }
    }
}
