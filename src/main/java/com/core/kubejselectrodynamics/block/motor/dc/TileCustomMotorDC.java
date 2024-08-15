package com.core.kubejselectrodynamics.block.motor.dc;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.block.capabilities.IBlockStateModifyChecker;
import com.core.kubejselectrodynamics.util.mixinterfaces.ComponentInterfaces;
import com.core.kubejselectrodynamics.util.TileUtils;
import dynamicelectricity.common.tile.generic.TileMotorDC;
import electrodynamics.common.network.utils.FluidUtilities;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.EnumSet;

public class TileCustomMotorDC extends TileMotorDC implements IBlockStateModifyChecker {
    private EnumSet<Direction> inputDirections = EnumSet.noneOf(Direction.class);
    private Direction lastFacing = null;
    private final CachedTileOutput[] outputs = new CachedTileOutput[6];
    public final BlockMotorDCBuilder builder;

    public TileCustomMotorDC(BlockPos pos, BlockState state) {
        this(TileRegister.MOTOR_DC_TYPE.getBasicType(), pos, state, ((CustomBlockMotorDC)state.getBlock()).kjs$getBlockBuilder());
    }

    public TileCustomMotorDC(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, BlockMotorDCBuilder builder) {
        super(tileEntityTypeIn, pos, state, builder.getFeConsumption(), 0, "");
        this.builder = builder;

        // Hacks
        joulesProduced.set(builder.getFeConsumption() * builder.getConversionEfficiency());
        ((ComponentInterfaces.IComponentContainerProvider) getComponent(IComponentType.ContainerProvider)).kjsElectro$setName(builder.getBuilderTranslationKey());

        ComponentElectrodynamic electrodynamic = getComponent(IComponentType.Electrodynamic);
        ComponentInterfaces.IComponentElectrodynamics mixinInterface = (ComponentInterfaces.IComponentElectrodynamics) electrodynamic;

        mixinInterface.kjsElectro$clearOutputDirections();
        electrodynamic.setOutputDirections(builder.getElectricOutput());
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
        inputDirections = TileUtils.getGlobalDirections(builder.getFEInputSet(), newState.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    public Direction getLastKnownDirection() {
        return lastFacing;
    }

    @Override
    public void setLastKnownDirection(Direction direction) {
        lastFacing = direction;
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void tickServer(ComponentTickable tickable) {
        checkUpdate(getBlockState(), this::onBlockStateUpdate);
        if (this.hasRedstoneSignal.get()) {
            this.running.set(false);
        } else {
            ComponentInventory inventory = this.getComponent(IComponentType.Inventory);
            ComponentElectrodynamic electro = this.getComponent(IComponentType.Electrodynamic);

            boolean canRun = false;
            ItemStack brush = inventory.getItem(0);
            ComponentFluidHandlerSimple tank = this.getComponent(IComponentType.FluidHandler);
            if (!brush.isEmpty() && feStored.get() >= maxFeConsumed.get()) {
                brush.setDamageValue(brush.getDamageValue() + 1);
                if (lubricantRemaining.get() > 0) {
                    lubricantRemaining.set(lubricantRemaining.get() - 1);
                    canRun = true;
                } else if (lubricantRemaining.get() == 0 && tank.getFluidAmount() > 0) {
                    tank.drain(1, IFluidHandler.FluidAction.EXECUTE);
                    lubricantRemaining.set(20000);
                }
            }

            if (brush.getDamageValue() >= brush.getMaxDamage() && canRun) {
                brush.shrink(1);
                inventory.setItem(0, brush);
            }

            FluidUtilities.drainItem(this, tank.asArray());
            running.set(canRun);
            if (canRun) {
                feStored.set(feStored.get() - maxFeConsumed.get());
                for (Direction direction : Direction.values()) {
                    int ordinal = direction.ordinal();
                    if (!((ComponentInterfaces.IComponentElectrodynamics) electro).kjsElectro$hasSidedOutput(direction)) {
                        continue;
                    }

                    if (outputs[ordinal] == null) {
                        outputs[ordinal] = new CachedTileOutput(level, worldPosition.relative(direction));
                    }
                    outputs[ordinal].getSafe();
                    if (outputs[ordinal].valid()) {
                        TransferPack pack = TransferPack.joulesVoltage(joulesProduced.get(), electro.getVoltage());
                        ElectricityUtils.receivePower(outputs[ordinal].getSafe(), direction.getOpposite(), pack, false);
                    }
                }
            }
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction face) {
        if (capability == ForgeCapabilities.ENERGY && inputDirections.contains(face)) {
            return LazyOptional.of(() -> (T)this);
        }
        return TileUtils.genericTileGetCapability(this, capability, face);
    }

    public static class Render extends TileCustomMotorDC {
        public Render(BlockPos pos, BlockState state) {
            super(TileRegister.MOTOR_DC_TYPE.getRenderedType(), pos, state, ((CustomBlockMotorDC)state.getBlock()).kjs$getBlockBuilder());
        }
    }
}
