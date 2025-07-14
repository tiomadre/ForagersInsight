package com.doltandtio.foragersinsight.common.block;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIItems;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TapperBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty HAS_TAPPER = BooleanProperty.create("has_tapper");
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4); // Now supports 5 stages

    public TapperBlock(Properties props) {
        super(props);
        this.registerDefaultState(defaultBlockState()
                .setValue(HAS_TAPPER, false)
                .setValue(FILL, 0)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FILL, HAS_TAPPER);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        if (face.getAxis().isVertical()) {
            return null; // prevent placement on top or bottom of log
        }

        Direction opposite = face.getOpposite();
        BlockPos attachedPos = context.getClickedPos().relative(opposite);
        BlockState attached = context.getLevel().getBlockState(attachedPos);

        if (attached.is(FIBlocks.SAPPY_BIRCH_LOG.get())) {
            return defaultBlockState()
                    .setValue(FACING, face)
                    .setValue(HAS_TAPPER, false)
                    .setValue(FILL, 0);
        }

        return null;
    }

    public boolean canSurvive(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        BlockState attachedState = level.getBlockState(attachedPos);
        return attachedState.is(FIBlocks.SAPPY_BIRCH_LOG.get());
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return state.getValue(HAS_TAPPER) && state.getValue(FILL) < 4;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(HAS_TAPPER)) {
            int currentFill = state.getValue(FILL);
            if (currentFill < 4) {
                level.setBlock(pos, state.setValue(FILL, currentFill + 1), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);

        // Place tapper to start collecting sap
        if (!state.getValue(HAS_TAPPER) && held.is(FIItems.TAPPER.get())) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(HAS_TAPPER, true).setValue(FILL, 0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1F, 1F);
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Harvest with a bucket
        if (state.getValue(HAS_TAPPER) && state.getValue(FILL) == 4 && held.is(Items.BUCKET)) {
            if (!level.isClientSide) {
                ItemStack sapBucket = new ItemStack(FIItems.BIRCH_SAP_BUCKET.get());
                if (!player.addItem(sapBucket)) {
                    player.drop(sapBucket, false);
                }

                // Reset stage
                level.setBlock(pos, state.setValue(HAS_TAPPER, false).setValue(FILL, 0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 1F);

                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState oldState, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock() && oldState.getValue(HAS_TAPPER)) {
            // Drop tapper item on break
            Block.popResource(level, pos, new ItemStack(FIItems.TAPPER.get()));
        }
        super.onRemove(oldState, level, pos, newState, isMoving);
    }
}
