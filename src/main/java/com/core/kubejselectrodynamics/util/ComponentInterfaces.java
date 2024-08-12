package com.core.kubejselectrodynamics.util;

import net.minecraft.core.Direction;

public class ComponentInterfaces {
    public interface IComponentElectrodynamics {
        void kjsElectro$clearInputDirections();
        void kjsElectro$clearOutputDirections();
        boolean kjsElectro$hasSidedInput(Direction side);
        boolean kjsElectro$hasSidedOutput(Direction side);
        boolean kjsElectro$hasSidedConnection(Direction side);
    }

    public interface IComponentContainerProvider {
        void kjsElectro$setName(String name);
    }
}
