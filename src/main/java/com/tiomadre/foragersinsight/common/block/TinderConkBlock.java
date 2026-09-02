package com.tiomadre.foragersinsight.common.block;

import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.Nullable;

public class TinderConkBlock extends Block implements BonemealableBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 4);
    public static final int MAX_AGE = 4;
    private static final VoxelShape[][] SHAPES_BY_FACING_AND_AGE = makeShapes();

    public TinderConkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction clickedFace = context.getClickedFace();
        if (clickedFace.getAxis().isHorizontal() && context.getItemInHand().is(FIItems.TINDER_CONK_SPORES.get())) {
            BlockState state = this.defaultBlockState()
                    .setValue(FACING, clickedFace)
                    .setValue(AGE, 0);
            return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos supportPos = pos.relative(facing.getOpposite());
        return canGrowOn(level.getBlockState(supportPos));
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(AGE) != MAX_AGE) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide) {
            popResource(level, pos, new ItemStack(FIItems.TINDER_CONK.get()));
            popResource(level, pos, new ItemStack(FIItems.TINDER_CONK_SPORES.get(), 2));
            level.destroyBlock(pos, false, player);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public static boolean canGrowOn(BlockState state) {
        return state.is(FIBlocks.SAPPY_BIRCH_LOG.get()) || state.is(FIBlocks.STRIPPED_SAPPY_BIRCH_LOG.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level,
                                  BlockPos currentPos, BlockPos neighborPos) {
        if (direction == state.getValue(FACING).getOpposite() && !state.canSurvive(level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_FACING_AND_AGE[state.getValue(FACING).get2DDataValue()][state.getValue(AGE)];
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) < MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(net.minecraft.world.level.Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        grow(level, pos, state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE) < MAX_AGE && random.nextInt(4) == 0 && CommonHooks.canCropGrow(level, pos, state, true)) {
            grow(level, pos, state);
            CommonHooks.fireCropGrowPost(level, pos, state);
        }
    }

    private static void grow(ServerLevel level, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(AGE, Math.min(MAX_AGE, state.getValue(AGE) + 1)), Block.UPDATE_ALL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, FACING);
    }

    private static VoxelShape[][] makeShapes() {
        VoxelShape[][] shapes = new VoxelShape[4][5];
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            int index = direction.get2DDataValue();
            for (int age = 0; age <= MAX_AGE; age++) {
                double halfWidth = 1.0D + age;
                double height = 1.0D + Math.min(age, 2);
                double depth = 2.0D + age;
                double minY = Math.max(5.0D, 6.0D - age * 0.25D);
                double maxY = minY + height;
                shapes[index][age] = switch (direction) {
                    case SOUTH -> Block.box(8.0D - halfWidth, minY, 0.0D, 8.0D + halfWidth, maxY, depth);
                    case WEST -> Block.box(16.0D - depth, minY, 8.0D - halfWidth, 16.0D, maxY, 8.0D + halfWidth);
                    case EAST -> Block.box(0.0D, minY, 8.0D - halfWidth, depth, maxY, 8.0D + halfWidth);
                    default -> Block.box(8.0D - halfWidth, minY, 16.0D - depth, 8.0D + halfWidth, maxY, 16.0D);
                };
            }
        }
        return shapes;
    }
}