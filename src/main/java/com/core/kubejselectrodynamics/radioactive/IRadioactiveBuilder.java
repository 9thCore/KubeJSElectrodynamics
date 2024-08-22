package com.core.kubejselectrodynamics.radioactive;

import dev.latvian.mods.rhino.util.HideFromJS;

public interface IRadioactiveBuilder {
    @HideFromJS
    void setRadiation(double radiation);
    @HideFromJS
    void setRadiationRadius(double radiationRadius);
    @HideFromJS
    double getRadiation();
    @HideFromJS
    double getRadiationRadius();
}
