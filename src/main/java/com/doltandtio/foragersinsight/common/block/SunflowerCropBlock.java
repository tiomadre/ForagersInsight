package com.doltandtio.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;

public class SunflowerCropBlock extends CropBlock implements BonemealableBlock {
    public static final int MAX_AGE = 4;
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.box(0, 0, 0, 16, 4, 16),
            Block.box(0, 0, 0, 16, 8, 16),
            Block.box(0, 0, 0, 16, 12, 16),
            Block.box(0, 0, 0, 16, 16, 16),
            Block.box(0, 0, 0, 16, 16, 16)
    };

    public static final EnumProperty<DoubleBlockHalf> HALF = EnumProperty.create("half", DoubleBlockHalf.class);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);

    private final int isDoubleAfterAge; // if set to 2, a plant will become a double plant at age 2.

    public SunflowerCropBlock(BlockBehaviour.Properties props, int isDoubleAfterAge) {
        super(props);
        this.isDoubleAfterAge = isDoubleAfterAge;

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(HALF, LOWER)
                .setValue(AGE, 0));
    }

    public boolean isDouble(BlockState state) {
        return this.getAge(state) >= isDoubleAfterAge;
    }

    public boolean isDouble(int age) {
        return age >= isDoubleAfterAge;
    }

    public DoubleBlockHalf getHalf(BlockState state) {
        return state.getValue(HALF);
    }

    // randomly ticks
    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return super.isRandomlyTicking(state) && getHalf(state) == LOWER;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        if (this.isMaxAge(state) || !level.isAreaLoaded(pos, 1) || level.getRawBrightness(pos, 0) < 9) {
            return;
        }

        float growthSpeed = getGrowthSpeed(this, level, pos);
        if (ForgeHooks.onCropsGrowPre(level, pos, state, rand.nextInt((int) (25 / growthSpeed)) == 0)) {
            this.mature(level, state, pos);
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    // Increases the current age of the plant
    private void mature(int stages, Level level, BlockState state, BlockPos pos) {
        if (this.isMaxAge(state)) {
            return;
        }

        if (!canGrow(level, pos) && isDouble(getAge(state) + stages)) {
            int index = stagesBeforeDouble(state);
            if (index != 0) {
                mature(index, level, state, pos);
            }
            return;
        }

        if (this.getHalf(state) == UPPER) {
            if (level.getBlockState(pos.below()).is(this)) {
                mature(stages, level, level.getBlockState(pos.below()), pos.below());
            }
            return;
        }

        int newAge = Math.min(this.getAge(state) + stages, MAX_AGE);
        if (this.isDouble(newAge)) {
            level.setBlock(pos, getStateForAge(newAge), UPDATE_CLIENTS);
            level.setBlock(pos.above(), getStateForAge(newAge).setValue(HALF, UPPER), UPDATE_CLIENTS);
        }
        else {
            level.setBlock(pos, getStateForAge(newAge), UPDATE_CLIENTS);
        }
    }

    private void mature(Level level, BlockState state, BlockPos pos) {
        mature(1, level, state, pos);
    }

    // Blockstates stuff
    @Override
    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDef) {
        stateDef.add(HALF, AGE);
    }

    public static boolean isIllegalState(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof SunflowerCropBlock crop) {
            return state.getValue(AGE) < crop.isDoubleAfterAge && state.getValue(HALF) == UPPER;
        }
        return false;
    }

// bone meal

    protected int getBonemealAgeIncrease(Level level, BlockPos pos, BlockState state) {
        if (canGrow(level, pos)) {
            return level.getRandom().nextInt(1, 3);
        }
        else {
            return Math.min(stagesBeforeDouble(state), level.getRandom().nextInt(1, 3));
        }
    }

    @Override
    public void growCrops(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        mature(getBonemealAgeIncrease(level, pos, state), level, state, pos);
    }

// shape

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, BlockGetter p_52298_, BlockPos p_52299_, CollisionContext p_52300_) {
        int age = this.getAge(state);
        if (this.isDouble(age) && state.getValue(HALF) == LOWER) {
            return Block.box(0,0 ,0, 16, 16, 16);
        }
        else {
            return AGE_TO_SHAPE[age];
        }
    }

    // double plant stuff
    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState updatedState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos updatePos) {
        if (!isDouble(state)) {
            return super.updateShape(state, direction, updatedState, level, pos, updatePos);
        }
        else {
            DoubleBlockHalf thisHalf = state.getValue(HALF);
            if (direction.getAxis() != Direction.Axis.Y ||
                    thisHalf == LOWER != (direction == Direction.UP) ||
                    updatedState.is(this) && updatedState.getValue(HALF) != thisHalf) {

                return thisHalf == LOWER &&
                        direction == Direction.DOWN &&
                        !state.canSurvive(level, pos) ?
                        Blocks.AIR.defaultBlockState() :
                        super.updateShape(state, direction, updatedState, level, pos, updatePos);
            }
            else {
                return Blocks.AIR.defaultBlockState();
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(HALF) != UPPER) {
            return super.canSurvive(state, level, pos);
        } else {
            BlockState blockstate = level.getBlockState(pos.below());
            if (state.getBlock() != this) {
                return super.canSurvive(state, level, pos);
            } else {
                return blockstate.is(this) && blockstate.getValue(HALF) == LOWER;
            }
        }
    }

    public boolean canGrow(Level level, BlockPos pos) {
        BlockState aboveState = level.getBlockState(pos.above());
        return aboveState.is(BlockTags.REPLACEABLE) || aboveState.is(this);
    }

    public int stagesBeforeDouble(BlockState state) {
        return this.isDoubleAfterAge - 1 - getAge(state);
    }
}