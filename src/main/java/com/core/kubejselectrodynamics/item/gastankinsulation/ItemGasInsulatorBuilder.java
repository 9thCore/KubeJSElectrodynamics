package com.core.kubejselectrodynamics.item.gastankinsulation;

import com.core.kubejselectrodynamics.util.InsulationUtils;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import electrodynamics.common.tile.pipelines.tanks.gas.GenericTileGasTank;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ItemGasInsulatorBuilder extends ItemBuilder {
    private double insulationEffectiveness = GenericTileGasTank.INSULATION_EFFECTIVENESS;

    public ItemGasInsulatorBuilder(ResourceLocation location) {
        super(location);
    }

    @Info("""
        Sets the insulator's effectiveness.
        Total insulation factor in the tank scales multiplicatively.
        Cannot go below 0.
        """)
    public ItemGasInsulatorBuilder insulationEffectiveness(double insulationEffectiveness) {
        this.insulationEffectiveness = Math.max(0.0D, insulationEffectiveness);
        return this;
    }

    @Override
    public Item createObject() {
        Item item = new Item(createItemProperties());
        InsulationUtils.registerInsulator(item, insulationEffectiveness);
        return item;
    }
}
