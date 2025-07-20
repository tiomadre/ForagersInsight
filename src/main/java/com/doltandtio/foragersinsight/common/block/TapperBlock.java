package com.doltandtio.foragersinsight.common.block;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.core.registry.FIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class TapperBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HAS_TAPPER = BooleanProperty.create("has_tapper");
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);

    // hit box
    private static final VoxelShape NORTH_SHAPE = Stream.of(
            Block.box(7.5, 12,  0,   8.5, 15,  6),
            Block.box(7.5, 13,  6,   8.5, 15, 11),
            Block.box(3.5,  2,   1,  12.5, 11, 10),
            Block.box(11.5,11,   5,  11.5,15,  6),
            Block.box(4.5, 11,   5,   4.5,15,  6),
            Block.box(4.5, 15,   5,  11.5,15,  6)
    ).reduce(Shapes.empty(), Shapes::or);
    private static final VoxelShape EAST_SHAPE = Stream.of(
            Block.box(10, 12,  7.5, 16, 15,  8.5),
            Block.box( 5, 13,  7.5, 10, 15,  8.5),
            Block.box( 6,  2,  3.5, 15, 11, 12.5),
            Block.box(10, 11, 11.5, 11, 15, 11.5),
            Block.box(10, 11,  4.5, 11, 15,  4.5),
            Block.box(10, 15,  4.5, 11, 15, 11.5)
    ).reduce(Shapes.empty(), Shapes::or);
    private static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.box( 7.5, 12, 10,  8.5, 15, 16),
            Block.box( 7.5, 13,  5,  8.5, 15, 10),
            Block.box( 3.5,  2,   6, 12.5, 11, 15),
            Block.box( 4.5, 11, 10,  4.5, 15, 11),
            Block.box(11.5, 11, 10, 11.5, 15, 11),
            Block.box( 4.5, 15, 10, 11.5, 15, 11)
    ).reduce(Shapes.empty(), Shapes::or);
    private static final VoxelShape WEST_SHAPE = Stream.of(
            Block.box( 0, 12,  7.5,  6, 15,  8.5),
            Block.box( 6, 13,  7.5, 11, 15,  8.5),
            Block.box( 1,  2,  3.5, 10, 11, 12.5),
            Block.box( 5, 11,  4.5,  6, 15,  4.5),
            Block.box( 5, 11, 11.5,  6, 15, 11.5),
            Block.box( 5, 15,  4.5,  6, 15, 11.5)
    ).reduce(Shapes.empty(), Shapes::or);

    public TapperBlock(Properties props) {
        super(props);
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HAS_TAPPER, false)
                .setValue(FILL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FILL, HAS_TAPPER);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world,
                                        @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return switch (state.getValue(FACING)) {
            case EAST  -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST  -> WEST_SHAPE;
            default    -> NORTH_SHAPE;
        };
    }
    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        if (face.getAxis().isVertical()) return null;
        Level level = context.getLevel();
        BlockPos logPos = context.getClickedPos().relative(face);
        BlockState logState = level.getBlockState(logPos);

        // place on any valid Sappy Birch Log to being harvest
        if (!logState.is(FIBlocks.SAPPY_BIRCH_LOG.get()) ||
                logState.getValue(RotatedPillarBlock.AXIS) != Direction.Axis.Y) {
            return null;
        }
        return defaultBlockState()
                .setValue(FACING, face.getOpposite())
                .setValue(HAS_TAPPER, true)
                .setValue(FILL, 0);
    }
    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Direction attachDir = state.getValue(FACING).getOpposite();
        BlockState logState = world.getBlockState(pos.relative(attachDir));
        return logState.is(FIBlocks.SAPPY_BIRCH_LOG.get()) &&
                logState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y;
    }
    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return state.getValue(HAS_TAPPER) && state.getValue(FILL) < 4;
    }
    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level,
                           @NotNull BlockPos pos, @NotNull RandomSource random) {
        level.setBlock(pos, state.setValue(FILL, state.getValue(FILL) + 1), Block.UPDATE_CLIENTS);
    }
    //drips
    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                            @NotNull RandomSource random) {
        if (state.getValue(FILL) == 4 && state.getValue(HAS_TAPPER)) {
            if (random.nextInt(5) == 0) {
                Direction facing = state.getValue(FACING);
                double x = pos.getX() + 0.5D;
                double y = pos.getY() + 0.8D;
                double z = pos.getZ() + 0.5D;

                switch (facing) {
                    case NORTH -> z -= 0.35D;
                    case SOUTH -> z += 0.35D;
                    case WEST  -> x -= 0.35D;
                    case EAST  -> x += 0.35D;
                }
                level.addParticle(FIParticleTypes.DRIPPING_SAP.get(), x, y, z, 0.0D, -0.05D, 0.0D);
                level.playLocalSound(x, y, z, SoundEvents.BEEHIVE_DRIP, SoundSource.BLOCKS,
                        0.5F, 1.0F, false);
            }
        }
    }
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
    @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {ItemStack held = player.getItemInHand(hand);
        // harvest only when full w/ bucket
        if (state.getValue(HAS_TAPPER) && state.getValue(FILL) == 4 && held.is(Items.BUCKET)) {
            if (!level.isClientSide) {
                ItemStack sap = new ItemStack(FIItems.BIRCH_SAP_BUCKET.get());
                if (!player.addItem(sap)) player.drop(sap, false);
                // reset stage
                level.setBlock(pos,
                        state.setValue(FILL, 0).setValue(HAS_TAPPER, true),
                        Block.UPDATE_ALL);
                // sounds
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 0.9F, 0.9F);
                level.playSound(null, pos, SoundEvents.BEEHIVE_DRIP, SoundSource.BLOCKS, 1.1F, 0.5F);
                if (!player.getAbilities().instabuild) held.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState oldState, @NotNull Level level, @NotNull BlockPos pos,
                         BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock() && oldState.getValue(HAS_TAPPER)) {
            popResource(level, pos, new ItemStack(FIItems.TAPPER.get()));
        }
        super.onRemove(oldState, level, pos, newState, isMoving);
    }
}
