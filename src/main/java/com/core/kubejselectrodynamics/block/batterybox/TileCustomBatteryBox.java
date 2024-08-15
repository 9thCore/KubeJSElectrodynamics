package com.core.kubejselectrodynamics.block.batterybox;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.mixinterfaces.ComponentInterfaces;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.tile.electricitygrid.batteries.TileBatteryBox;
import electrodynamics.prefab.tile.components.IComponent;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class TileCustomBatteryBox extends TileBatteryBox {
    private final CachedTileOutput[] outputs = new CachedTileOutput[6];

    public TileCustomBatteryBox(BlockPos worldPosition, BlockState blockState) {
        this(TileRegister.BATTERY_BOX_TYPE.get(), worldPosition, blockState, ((CustomBlockBatteryBox)blockState.getBlock()).kjs$getBlockBuilder());
    }

    public TileCustomBatteryBox(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState, BlockBatteryBoxBuilder builder) {
        super(type, SubtypeMachine.batterybox, builder.getVoltage(), builder.getOutput(), builder.getCapacity(), worldPosition, blockState);

        // Hacks: set correct values
        ((ComponentInterfaces.IComponentContainerProvider) getComponent(IComponentType.ContainerProvider)).kjsElectro$setName(builder.getBuilderTranslationKey());

        IComponent component = getComponent(IComponentType.Electrodynamic);
        ComponentElectrodynamic electrodynamic = (ComponentElectrodynamic) component;
        ComponentInterfaces.IComponentElectrodynamics mixinInterface = (ComponentInterfaces.IComponentElectrodynamics) component;

        mixinInterface.kjsElectro$clearInputDirections();
        electrodynamic.setInputDirections(builder.getElectricInput());
        mixinInterface.kjsElectro$clearOutputDirections();
        electrodynamic.setOutputDirections(builder.getElectricOutput());
    }

    /**
     * Method originally from Electrodynamics' source code, adapted to fit implementation
     */
    @Override
    protected void tickServer(ComponentTickable tickable) {
        ComponentElectrodynamic electro = this.getComponent(IComponentType.Electrodynamic);

        for (Direction direction : Direction.values()) {
            int ordinal = direction.ordinal();
            if (!((ComponentInterfaces.IComponentElectrodynamics) electro).kjsElectro$hasSidedOutput(direction)) {
                continue;
            }

            if (outputs[ordinal] == null) {
                outputs[ordinal] = new CachedTileOutput(level, worldPosition.relative(direction));
            }
            CachedTileOutput output = outputs[ordinal];
            output.getSafe();

            if (electro.getJoulesStored() > 0.0 && output.valid()) {
                electro.joules(electro.getJoulesStored() - ElectricityUtils.receivePower(output.getSafe(), direction.getOpposite(), TransferPack.joulesVoltage(Math.min(electro.getJoulesStored(), powerOutput.get() * currentCapacityMultiplier.get()), electro.getVoltage()), false).getJoules());
            }
        }

        if (electro.getJoulesStored() > electro.getMaxJoulesStored()) {
            electro.joules(electro.getMaxJoulesStored());
        }

        electro.drainElectricItem(0);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, Direction face) {
        ComponentElectrodynamic component = getComponent(IComponentType.Electrodynamic);
        if (capability == ForgeCapabilities.ENERGY) {
            if (((ComponentInterfaces.IComponentElectrodynamics) component).kjsElectro$hasSidedConnection(face)) {
                return LazyOptional.of(() -> (T)this);
            }
            return LazyOptional.empty();
        }
        return component.getCapability(capability, face, null);
    }
}
