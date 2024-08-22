package com.core.kubejselectrodynamics.block.radioactive;

import com.core.kubejselectrodynamics.block.TileRegister;
import com.core.kubejselectrodynamics.util.RadiationUtil;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.registers.NuclearScienceBlocks;

public class TileCustomRadioactive extends GenericTile {
    public final BlockRadioactiveBuilder builder;

    public TileCustomRadioactive(BlockPos pos, BlockState state) {
        this(TileRegister.RADIOACTIVE_TYPE.getType(), pos, state, ((CustomBlockRadioactive) state.getBlock()).kjs$getBlockBuilder());
    }

    public TileCustomRadioactive(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState, BlockRadioactiveBuilder builder) {
        super(tileEntityTypeIn, worldPos, blockState);
        this.builder = builder;
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
    }

    /**
     * Adapted from TileMeltedReactor
     */
    protected void tickServer(ComponentTickable tickable) {
        if (builder.getRadiation() <= 0.0D) {
            return;
        }

        double radius = builder.getRadiationRadius();
        double x2 = worldPosition.getX() + 0.5 + (level.random.nextDouble() - 0.5) * radius / 2;
        double y2 = worldPosition.getY() + 0.5 + (level.random.nextDouble() - 0.5) * radius / 2;
        double z2 = worldPosition.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * radius / 2;
        double d3 = worldPosition.getX() - x2;
        double d4 = worldPosition.getY() - y2;
        double d5 = worldPosition.getZ() - z2;
        double distanceSq = d3 * d3 + d4 * d4 + d5 * d5;
        if (distanceSq < radius * radius && level.random.nextDouble() > distanceSq / (radius * radius)) {
            BlockPos p = new BlockPos((int) Math.floor(x2), (int) Math.floor(y2), (int) Math.floor(z2));
            BlockState st = level.getBlockState(p);
            Block block = st.getBlock();
            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {
                level.setBlockAndUpdate(p, NuclearScienceBlocks.blockRadioactiveSoil.defaultBlockState());
            }
        }

        if (level.getLevelData().getGameTime() % 10 == 0) {
            RadiationSystem.emitRadiationFromLocation(level, new Location(worldPosition), builder.getRadiationRadius(), builder.getRadiation());
        }
    }
}
