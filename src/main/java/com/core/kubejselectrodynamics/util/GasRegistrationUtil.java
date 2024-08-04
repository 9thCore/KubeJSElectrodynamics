package com.core.kubejselectrodynamics.util;

import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class GasRegistrationUtil {
    /**
     * KubeJS registered gasses only
     */
    public static List<RegistryObject<Gas>> KJS_REGISTERED_GASSES = new ArrayList<>();
    /**
     * KubeJS condensable gasses only
     */
    public static List<RegistryObject<Gas>> KJS_CONDENSABLE_GASSES = null;

    public static List<RegistryObject<Gas>> getCondensableGasses() {
        if (KJS_CONDENSABLE_GASSES == null) {
            KJS_CONDENSABLE_GASSES = new ArrayList<>();
            KJS_REGISTERED_GASSES.forEach(gas -> {
                if (gas.get().getCondensedFluid() != Fluids.EMPTY) {
                    KJS_CONDENSABLE_GASSES.add(gas);
                }
            });
        }
        return KJS_CONDENSABLE_GASSES;
    }
}
