package com.core.kubejselectrodynamics.block;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.batterybox.TileCustomBatteryBox;
import com.core.kubejselectrodynamics.block.fluidpipe.TileCustomFluidPipe;
import com.core.kubejselectrodynamics.block.gaspipe.TileCustomGasPipe;
import com.core.kubejselectrodynamics.block.motor.ac.TileCustomMotorAC;
import com.core.kubejselectrodynamics.block.motor.dc.TileCustomMotorDC;
import com.core.kubejselectrodynamics.block.radioactive.TileCustomRadioactive;
import com.core.kubejselectrodynamics.block.storage.gastank.TileCustomGasTank;
import com.core.kubejselectrodynamics.block.storage.tank.TileCustomTank;
import com.core.kubejselectrodynamics.block.wire.TileCustomLogisticalWire;
import com.core.kubejselectrodynamics.block.wire.TileCustomWire;
import com.core.kubejselectrodynamics.client.tile.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileRegister {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, KubeJSElectrodynamics.MODID);

    public static final BlockEntityOptionalRenderer<TileCustomBatteryBox, TileCustomBatteryBox.Render> BATTERY_BOX_TYPE = new BlockEntityOptionalRenderer<>(
            "electrodynamics",
            "battery_box_type",
            () -> TileCustomBatteryBox::new,
            () -> TileCustomBatteryBox.Render::new,
            () -> (BlockEntityRendererProvider<TileCustomBatteryBox.Render>) RenderCustomBatteryBox::new
    );

    public static final BlockEntityRegister<TileCustomGasTank> GAS_TANK_TYPE = new BlockEntityRegister<>(
            "electrodynamics",
            "gas_tank_type",
            () -> TileCustomGasTank::new
    );

    public static final BlockEntityRegister<TileCustomWire> WIRE_TYPE = new BlockEntityRegister<>(
            "electrodynamics",
            "wire_type",
            () -> TileCustomWire::new
    );

    public static final BlockEntityRegister<TileCustomLogisticalWire> LOGISTICAL_WIRE_TYPE = new BlockEntityRegister<>(
            "electrodynamics",
            "logistical_wire_type",
            () -> TileCustomLogisticalWire::new
    );

    public static final BlockEntityRegister<TileCustomFluidPipe> FLUID_PIPE_TYPE = new BlockEntityRegister<>(
            "electrodynamics",
            "fluid_pipe_type",
            () -> TileCustomFluidPipe::new
    );

    public static final BlockEntityRegister<TileCustomGasPipe> GAS_PIPE_TYPE = new BlockEntityRegister<>(
            "electrodynamics",
            "gas_pipe_type",
            () -> TileCustomGasPipe::new
    );

    public static BlockEntityOptionalRenderer<TileCustomMotorDC, TileCustomMotorDC.Render> MOTOR_DC_TYPE = new BlockEntityOptionalRenderer<>(
            "dynamicelectricity",
            "motor_dc_type",
            () -> TileCustomMotorDC::new,
            () -> TileCustomMotorDC.Render::new,
            () -> (BlockEntityRendererProvider<TileCustomMotorDC.Render>) RenderCustomMotorDC::new
    );

    public static BlockEntityOptionalRenderer<TileCustomTank, TileCustomTank.Render> TANK_TYPE = new BlockEntityOptionalRenderer<>(
            "electrodynamics",
            "tank_type",
            () -> TileCustomTank::new,
            () -> TileCustomTank.Render::new,
            () -> (BlockEntityRendererProvider<TileCustomTank.Render>) RenderCustomTank::new
    );

    public static BlockEntityOptionalRenderer<TileCustomMotorAC, TileCustomMotorAC.Render> MOTOR_AC_TYPE = new BlockEntityOptionalRenderer<>(
            "dynamicelectricity",
            "motor_ac_type",
            () -> TileCustomMotorAC::new,
            () -> TileCustomMotorAC.Render::new,
            () -> (BlockEntityRendererProvider<TileCustomMotorAC.Render>) RenderCustomMotorAC::new
    );

    public static final BlockEntityRegister<TileCustomRadioactive> RADIOACTIVE_TYPE = new BlockEntityRegister<>(
            "nuclearscience",
            "radioactive_type",
            () -> TileCustomRadioactive::new
    );
}
