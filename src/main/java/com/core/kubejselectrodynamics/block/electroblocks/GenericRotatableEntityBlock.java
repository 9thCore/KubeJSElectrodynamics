package com.core.kubejselectrodynamics.block.electroblocks;

import com.core.kubejselectrodynamics.block.capabilities.IElectrodynamicsCustomRenderer;
import dev.latvian.mods.kubejs.block.custom.HorizontalDirectionalBlockBuilder;
import electrodynamics.common.block.BlockMachine;
import electrodynamics.common.block.voxelshapes.VoxelShapes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class GenericRotatableEntityBlock extends HorizontalDirectionalBlockBuilder.WithEntity {
    private final BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier;
    public final @Nonnull HorizontalDirectionalBlockBuilder blockBuilder;

    public GenericRotatableEntityBlock(BlockEntityType.BlockEntitySupplier<? extends BlockEntity> supplier, @Nonnull HorizontalDirectionalBlockBuilder blockBuilder) {
        super(blockBuilder);
        this.blockBuilder = blockBuilder;
        this.supplier = supplier;
        BlockState state = getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BlockMachine.ON, false);
        if (blockBuilder.canBeWaterlogged()) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, false);
        }
        registerDefaultState(state);
    }

    @Override
    public @Nonnull HorizontalDirectionalBlockBuilder kjs$getBlockBuilder() {
        return blockBuilder;
    }

    @Override
    public ResourceLocation kjs$getIdLocation() {
        return blockBuilder.id;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return VoxelShapes.getShape(state.getBlock(), state.getValue(FACING));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockMachine.ON);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return supplier.create(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (l, pos, s, tile) -> {
            if (tile instanceof GenericTile generic) {
                if (generic.getComponent(IComponentType.Tickable) instanceof ComponentTickable tickable) {
                    tickable.performTick(l);
                }
            }
        };
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        if (blockBuilder instanceof IElectrodynamicsCustomRenderer renderer) {
            if (renderer.usesCustomRenderer()) {
                return renderer.getRenderShape(state);
            }
        }
        return super.getRenderShape(state);
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        super.onBlockStateChange(level, pos, oldState, newState);
        BlockEntity entity = level.getBlockEntity(pos);
        if (entity instanceof GenericTile tile) {
            tile.onBlockStateUpdate(oldState, newState);
        }
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        BlockState state = super.rotate(blockState, rotation);
        if (state == blockState) {
            return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
        }
        return state;
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        BlockState state = super.mirror(blockState, mirror);
        if (state == blockState) {
            return blockState.setValue(FACING, mirror.mirror(blockState.getValue(FACING)));
        }
        return state;
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (super.use(state, level, pos, player, hand, hit) == InteractionResult.SUCCESS) {
            return InteractionResult.SUCCESS;
        }
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            return generic.use(player, hand, hit);
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            generic.onNeightborChanged(neighbor, false);
        }
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(newState, level, pos, oldState, isMoving);
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            generic.onPlace(oldState, isMoving);
        }
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, neighbor, isMoving);
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            generic.onNeightborChanged(neighbor, true);
        }

    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            return generic.getComparatorSignal();
        }
        return super.getAnalogOutputSignal(state, level, pos);
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            return generic.getDirectSignal(direction);
        }
        return super.getDirectSignal(state, level, pos, direction);
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (level.getBlockEntity(pos) instanceof GenericTile generic) {
            return generic.getSignal(direction);
        }
        return super.getSignal(state, level, pos, direction);
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level.getBlockEntity(pos) instanceof GenericTile tile) {
            tile.onEntityInside(state, level, pos, entity);
        }
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        BlockState state = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        if (blockBuilder.canBeWaterlogged()) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
        }
        return state;
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (blockBuilder.canBeWaterlogged() && stateIn.getValue(BlockStateProperties.WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    /**
     * Method originally from Electrodynamics' source code
     */
    @Override
    public FluidState getFluidState(BlockState state) {
        if (blockBuilder.canBeWaterlogged()) {
            return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
        }
        return super.getFluidState(state);
    }
}
