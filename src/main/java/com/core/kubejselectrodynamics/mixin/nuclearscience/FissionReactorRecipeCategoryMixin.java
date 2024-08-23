package com.core.kubejselectrodynamics.mixin.nuclearscience;

import com.core.kubejselectrodynamics.util.RadiationUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import nuclearscience.compatibility.jei.recipecategories.item2item.FissionReactorRecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(FissionReactorRecipeCategory.class)
public class FissionReactorRecipeCategoryMixin {
    @WrapOperation(
            method = "getItemInputs",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 0
            ),
            remap = false
    )
    private boolean kjsElectro$getItemInputsPatch(List<ItemStack> instance, Object object, Operation<Boolean> original) {
        instance.addAll(RadiationUtil.fuelRods.keySet().stream().map(Item::getDefaultInstance).toList());
        return original.call(instance, object);
    }
}
