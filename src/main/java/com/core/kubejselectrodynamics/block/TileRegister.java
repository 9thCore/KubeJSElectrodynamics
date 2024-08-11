package com.core.kubejselectrodynamics.block;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.batterybox.TileCustomBatteryBox;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class TileRegister {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KubeJSElectrodynamics.MODID);
    public static final List<Block> VALID_BATTERY_BLOCKS = new ArrayList<>();
    public static final RegistryObject<BlockEntityType<TileCustomBatteryBox>> BATTERY_BOX_TYPE = REGISTER.register(
            "battery_box_type",
            () -> BlockEntityType.Builder.of(TileCustomBatteryBox::new, VALID_BATTERY_BLOCKS.toArray(new Block[0])).build(null)
    );
}
