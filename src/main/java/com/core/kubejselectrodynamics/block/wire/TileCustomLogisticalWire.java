package com.core.kubejselectrodynamics.block.wire;

import com.core.kubejselectrodynamics.block.TileRegister;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileCustomLogisticalWire extends TileCustomWire {
    public boolean isPowered = false;

    public TileCustomLogisticalWire(BlockPos pos, BlockState state) {
        this(((CustomBlockWire) state.getBlock()).kjs$getBlockBuilder(), pos, state);
    }

    public TileCustomLogisticalWire(BlockWireBuilder builder, BlockPos pos, BlockState state) {
        super(builder, pos, state);
        this.forceComponent((new ComponentTickable(this)).tickServer(this::tickServer));
    }

    @Override
    public BlockEntityType<?> getType() {
        return TileRegister.LOGISTICAL_WIRE_TYPE.getType();
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    protected void tickServer(ComponentTickable component) {
        if (component.getTicks() % 10L == 0L) {
            boolean shouldPower = this.getNetwork().getActiveTransmitted() > 0.0;
            if (shouldPower != this.isPowered) {
                this.isPowered = shouldPower;
                this.level.updateNeighborsAt(this.worldPosition, this.getBlockState().getBlock());
                BlockEntityUtils.updateLit(this, this.isPowered);
            }
        }
    }
}
