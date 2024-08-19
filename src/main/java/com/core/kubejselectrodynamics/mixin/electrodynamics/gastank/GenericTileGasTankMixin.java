package com.core.kubejselectrodynamics.mixin.electrodynamics.gastank;

import com.core.kubejselectrodynamics.util.InsulationUtils;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import electrodynamics.common.tile.pipelines.tanks.gas.GenericTileGasTank;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GenericTileGasTank.class)
public class GenericTileGasTankMixin {
    @Definition(id = "getItem", method = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;")
    @Definition(id = "ITEM_FIBERGLASSSHEET", field = "Lelectrodynamics/registers/ElectrodynamicsItems;ITEM_FIBERGLASSSHEET:Lnet/minecraftforge/registries/RegistryObject;")
    @Definition(id = "get", method = "Lnet/minecraftforge/registries/RegistryObject;get()Ljava/lang/Object;")
    @Expression("?.getItem() == ITEM_FIBERGLASSSHEET.get()")
    @ModifyExpressionValue(
            method = "onInventoryChange",
            at = @At("MIXINEXTRAS:EXPRESSION"),
            remap = false
    )
    private boolean kjsElectro$checkInsulator(boolean original, @Local ItemStack item) {
        return original || InsulationUtils.isInsulator(item);
    }

    @ModifyExpressionValue(
            method = "onInventoryChange",
            at = @At(
                    value = "CONSTANT",
                    args = "doubleValue=1.05D"
            ),
            remap = false
    )
    private double kjsElectro$insulationBonusMixin(double original, @Local ItemStack item) {
        return InsulationUtils.isInsulator(item) ? InsulationUtils.getInsulationEffectiveness(item) : original;
    }
}
