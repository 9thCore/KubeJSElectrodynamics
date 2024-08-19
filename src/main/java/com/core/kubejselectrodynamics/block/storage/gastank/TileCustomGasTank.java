package com.core.kubejselectrodynamics.block.storage.gastank;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.InsulationUtils;
import com.core.kubejselectrodynamics.util.mixinterfaces.ComponentInterfaces;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.common.tile.pipelines.tanks.gas.GenericTileGasTank;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileCustomGasTank extends GenericTileGasTank {
    private final BlockGasTankBuilder builder;

    public TileCustomGasTank(BlockPos pos, BlockState state) {
        this(TileRegister.TANK_TYPE.getBasicType(), pos, state, ((CustomBlockGasTank) state.getBlock()).kjs$getBlockBuilder());
    }

    public TileCustomGasTank(BlockEntityType<?> type, BlockPos pos, BlockState state, BlockGasTankBuilder builder) {
        super(type, pos, state, SubtypeMachine.tanksteel, builder.getCapacity(), builder.getPressure(), builder.getTemperature());
        this.builder = builder;

        // Hacks
        ((ComponentInterfaces.IComponentContainerProvider) getComponent(IComponentType.ContainerProvider)).kjsElectro$setName(builder.getBuilderTranslationKey());

        ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
        handler.setInputDirections(builder.getGasInput());
        handler.setOutputDirections(builder.getGasOutput());
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void tickServer(ComponentTickable tick) {
        ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
        GasUtilities.drainItem(this, handler.asArray());
        GasUtilities.fillItem(this, handler.asArray());
        GasUtilities.outputToPipe(this, handler.asArray(), handler.outputDirections);
        GasStack gasIn = handler.getGas();
        if (!gasIn.isEmpty() && gasIn.getTemperature() != 293.0) {
            double deltaT = Math.signum(293.0 - gasIn.getTemperature());
            double temperatureDecrease = builder.getHeatLoss() / this.insulationBonus.get() * deltaT;
            handler.heat(temperatureDecrease, GasAction.EXECUTE);
        }
    }

    /**
     * Method originally from Electrodynamics' source
     */
    @Override
    public void onInventoryChange(ComponentInventory inv, int slot) {
        super.onInventoryChange(inv, slot);
        if (slot > 5) {
            return;
        }

        double insulationBonus = 1.0;

        for (ItemStack item : inv.getInputContents()) {
            if (item.is(ElectrodynamicsItems.ITEM_FIBERGLASSSHEET.get()) || InsulationUtils.isInsulator(item)) {
                insulationBonus *= InsulationUtils.getInsulationEffectiveness(item);
            }
        }

        this.insulationBonus.set(insulationBonus);

    }
}
