package com.core.kubejselectrodynamics.block.motor.ac;

import com.core.kubejselectrodynamics.KubeJSElectrodynamics;
import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.capabilities.IBlockStateModifyChecker;
import com.core.kubejselectrodynamics.util.ComponentInterfaces;
import com.core.kubejselectrodynamics.util.TileUtils;
import dynamicelectricity.common.tile.generic.TileMotorAC;
import dynamicelectricity.compatability.industrialreborn.IndustrialRebornHandler;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.ModList;

import java.util.EnumSet;

public class TileCustomMotorAC extends TileMotorAC implements IBlockStateModifyChecker {
    private EnumSet<Direction> outputDirections = EnumSet.noneOf(Direction.class);
    private Direction lastFacing = null;
    private final CachedTileOutput[] outputs = new CachedTileOutput[6];
    public final BlockMotorACBuilder builder;

    public TileCustomMotorAC(BlockPos pos, BlockState state) {
        this(TileRegister.MOTOR_AC_TYPE.getBasicType(), pos, state, ((CustomBlockMotorAC)state.getBlock()).kjs$getBlockBuilder());
    }

    public TileCustomMotorAC(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, BlockMotorACBuilder builder) {
        super(tileEntityTypeIn, pos, state, 0, builder.getJoulesConsumption(), "");
        this.builder = builder;

        // Hacks
        feProduced.set((int)(builder.getJoulesConsumption() * builder.getConversionEfficiency()));
        ((ComponentInterfaces.IComponentContainerProvider) getComponent(IComponentType.ContainerProvider)).kjsElectro$setName(builder.getBuilderTranslationKey());

        ComponentElectrodynamic electrodynamic = getComponent(IComponentType.Electrodynamic);
        ComponentInterfaces.IComponentElectrodynamics mixinInterface = (ComponentInterfaces.IComponentElectrodynamics) electrodynamic;

        mixinInterface.kjsElectro$clearInputDirections();
        electrodynamic.setInputDirections(builder.getElectricInput());
        electrodynamic.voltage(builder.getVoltage());

        ComponentInventory inventory = getComponent(IComponentType.Inventory);
        for (Direction direction : Direction.values()) {
            inventory.relativeDirectionToSlotsMap[direction.ordinal()] = null;
        }
        inventory.setDirectionsBySlot(0, builder.getInventoryInput());

        ComponentFluidHandlerSimple fluidHandlerSimple = getComponent(IComponentType.FluidHandler);
        fluidHandlerSimple.setInputDirections(builder.getFluidInput());
    }

    @Override
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        outputDirections = TileUtils.getGlobalDirections(builder.getFEOutputSet(), newState.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    public Direction getLastKnownDirection() {
        return lastFacing;
    }

    @Override
    public void setLastKnownDirection(Direction direction) {
        lastFacing = direction;
    }

    public void tickServer(ComponentTickable tickable) {
        checkUpdate(getBlockState(), this::onBlockStateUpdate);
        if (this.hasRedstoneSignal.get()) {
            this.running.set(false);
        } else {
            ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
            boolean canRun = false;
            ComponentFluidHandlerSimple tank = getComponent(IComponentType.FluidHandler);
            if (electro.getJoulesStored() >= joulesConsumed.get()) {
                if (lubricantRemaining.get() > 0) {
                    lubricantRemaining.set(lubricantRemaining.get() - 1);
                    canRun = true;
                } else if (tank.getFluidAmount() > 0 && lubricantRemaining.get() == 0) {
                    tank.drain(1, IFluidHandler.FluidAction.EXECUTE);
                    lubricantRemaining.set(20000);
                }
            }

            FluidUtilities.drainItem(this, ((ComponentFluidHandlerSimple)getComponent(IComponentType.FluidHandler)).asArray());
            this.running.set(canRun);
            if (canRun) {
                electro.joules(electro.getJoulesStored() - joulesConsumed.get());
                feStored.set(feProduced.get());
                for (Direction direction : Direction.values()) {
                    int ordinal = direction.ordinal();
                    if (!outputDirections.contains(direction)) {
                        continue;
                    }

                    if (outputs[ordinal] == null) {
                        outputs[ordinal] = new CachedTileOutput(level, worldPosition.relative(direction));
                    }
                    outputs[ordinal].getSafe();

                    if (outputs[ordinal].valid()) {
                        handleFe(outputs[ordinal].getSafe(), direction.getOpposite());
                        handleIndustrialReborn(outputs[ordinal].getSafe(), direction.getOpposite());
                    }
                }
            }
        }
    }

    /**
     * Method originally from Dynamic Electricity's source
     */
    private void handleFe(BlockEntity tile, Direction side) {
        tile.getCapability(ForgeCapabilities.ENERGY, side).ifPresent(c -> {
            int amount = c.receiveEnergy(feStored.get(), true);
            if (amount > 0) {
                c.receiveEnergy(amount, false);
                feStored.set(feStored.get() - amount);
            }
        });
    }

    /**
     * Method originally from Dynamic Electricity's source
     */
    private void handleIndustrialReborn(BlockEntity tile, Direction motorFacing) {
        if (ModList.get().isLoaded("indreb")) {
            IndustrialRebornHandler.handleEnergyOutput(this, tile, motorFacing);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction face) {
        if (capability == ForgeCapabilities.ENERGY && outputDirections.contains(face)) {
            return LazyOptional.of(() -> (T)this);
        }
        return TileUtils.genericTileGetCapability(this, capability, face);
    }

    public static class Render extends TileCustomMotorAC {
        public Render(BlockPos pos, BlockState state) {
            super(TileRegister.MOTOR_AC_TYPE.getRenderedType(), pos, state, ((CustomBlockMotorAC)state.getBlock()).kjs$getBlockBuilder());
        }
    }
}
