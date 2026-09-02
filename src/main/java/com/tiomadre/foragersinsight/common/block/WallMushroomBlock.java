package com.tiomadre.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Supplier;

public class WallMushroomBlock extends MushroomBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final int PLACING_LIGHT_LEVEL = 13;
    private static final VoxelShape NORTH_SHAPE = Block.box(4.0D, 3.0D, 8.0D, 12.0D, 13.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(4.0D, 3.0D, 0.0D, 12.0D, 13.0D, 8.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(8.0D, 3.0D, 4.0D, 16.0D, 13.0D, 12.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(0.0D, 3.0D, 4.0D, 8.0D, 13.0D, 12.0D);

    private final Supplier<Block> colonyBlock;

    public WallMushroomBlock(Properties props, Supplier<Block> colonyBlock) {
        super(null, props);
        this.colonyBlock = colonyBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isHorizontal()) {
            BlockState state = this.defaultBlockState().setValue(FACING, clickedFace);
            return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);

        if (canGrowColonyOn(supportState, facing)) {
            return true;
        }

        return level.getRawBrightness(pos, 0) < PLACING_LIGHT_LEVEL && supportState.canSustainPlant(level, supportPos, facing,  level.getBlockState(pos)).isTrue();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        growIntoColony(level, pos, state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(4) == 0 && canGrowColonyOn(getSupportState(level, pos, state), state.getValue(FACING))) {
            growIntoColony(level, pos, state);
        }
    }

    private void growIntoColony(ServerLevel level, BlockPos pos, BlockState state) {
        Block colony = this.colonyBlock.get();
        if (colony == null) {
            return;
        }

        BlockState colonyState = colony.defaultBlockState().setValue(WallMushroomColonyBlock.FACING, state.getValue(FACING));
        if (colonyState.canSurvive(level, pos)) {
            level.setBlock(pos, colonyState, Block.UPDATE_ALL);
        }
    }

    private static BlockState getSupportState(LevelReader level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        return level.getBlockState(pos.relative(facing.getOpposite()));
    }

    static boolean canGrowColonyOn(BlockState supportState, Direction face) {
        return supportState.is(BlockTags.MUSHROOM_GROW_BLOCK)
                || supportState.is(ModTags.Blocks.MUSHROOM_COLONY_GROWABLE_ON)
                || HollowLogBlock.MushroomPlacement(supportState, face);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
