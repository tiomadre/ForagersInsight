package com.doltandtio.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RenderShape;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SunflowerCropBlock extends CropBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(2, 0, 2, 14, 10, 14),
            Block.box(2, 0, 2, 14, 14, 14),
            Block.box(2, 0, 2, 14, 16, 14),
            Block.box(2, 0, 2, 14, 16, 14),
            Block.box(2, 0, 2, 14, 16, 14)
    };

    public SunflowerCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(this.getAgeProperty(), 0)
                .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 4;
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.UPPER
                ? Block.box(2, 0, 2, 14, 16, 14)
                : SHAPE_BY_AGE[state.getValue(getAgeProperty())];
    }
    public static boolean isIllegalState(BlockState state) {
        return !state.hasProperty(AGE) || !state.hasProperty(HALF);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && !isMaxAge(state);
    }

    @Override
    public void randomTick(@NotNull BlockState state, ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getRawBrightness(pos.above(), 0) >= 9) {
            int age = this.getAge(state);
            if (age < this.getMaxAge() && random.nextInt(5) == 0) {
                growCrop(world, pos, state, age + 1);
            }
        }
    }

    protected void growCrop(ServerLevel world, BlockPos pos, BlockState state, int newAge) {
        if (newAge <= getMaxAge()) {
            BlockState newState = state.setValue(AGE, newAge);
            world.setBlock(pos, newState, 2);

            if (newAge >= 2) {
                BlockPos above = pos.above();
                if (world.getBlockState(above).isAir()) {
                    world.setBlock(above, newState.setValue(HALF, DoubleBlockHalf.UPPER), 2);
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState oldState, @NotNull Level world, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            DoubleBlockHalf half = oldState.getValue(HALF);
            if (half == DoubleBlockHalf.LOWER) {
                BlockPos above = pos.above();
                BlockState aboveState = world.getBlockState(above);
                if (aboveState.getBlock() == this && aboveState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    world.removeBlock(above, false);
                }
            } else {
                BlockPos below = pos.below();
                BlockState belowState = world.getBlockState(below);
                if (belowState.getBlock() == this && belowState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    world.removeBlock(below, false);
                }
            }
        }
        super.onRemove(oldState, world, pos, newState, isMoving);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (half == DoubleBlockHalf.UPPER) {
            BlockState below = world.getBlockState(pos.below());
            return below.getBlock() == this && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        } else {
            return super.canSurvive(state, world, pos);
        }
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction dir, @NotNull BlockState fromState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos fromPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (dir == Direction.UP && half == DoubleBlockHalf.LOWER) {
            if (fromState.getBlock() == this && fromState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                return state;
            }
        } else if (dir == Direction.DOWN && half == DoubleBlockHalf.UPPER) {
            if (fromState.getBlock() == this && fromState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                return state;
            }
        }
        return super.updateShape(state, dir, fromState, world, pos, fromPos);
    }
}
