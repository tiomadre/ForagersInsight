package com.doltandtio.foragersinsight.common.block;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TapperBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty HAS_BUCKET = BooleanProperty.create("bucket");
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 3);

    public TapperBlock(Properties props) {
        super(props);
        this.registerDefaultState(defaultBlockState()
                .setValue(HAS_BUCKET, false).setValue(FILL, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FILL, HAS_BUCKET);
    }

    private boolean canPlace(BlockPlaceContext context, BlockState state) {
        // only allow placing on birch logs
        return state.is(FITags.BlockTag.TAPPABLE) && context.canPlace();
    }

    public boolean placeTapper(Level level, BlockPlaceContext context, Player player, BlockState state) {
        if (!canPlace(context, state)) {
            return false;
        }

        BlockPos pos = context.getClickedPos();
        BlockState placeState = getStateForPlacement(context);
        ItemStack placeStack = context.getItemInHand();

        if (placeState == null) {
            return false;
        }

        if (!level.setBlock(pos, placeState, Block.UPDATE_ALL_IMMEDIATE)) return false;

        if (placeState.is(state.getBlock())) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, pos, placeStack);
            }
        }

        // play sounds and do the goddamn sculk sensors
        level.playSound(player, pos, soundType.getPlaceSound(),
                SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, state));

        if (player == null || !player.getAbilities().instabuild) {
            placeStack.shrink(1);
        }

        return true;
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return state.getValue(HAS_BUCKET) && state.getValue(FILL) < 3;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.getValue(HAS_BUCKET)) return;
        int fill = state.getValue(FILL);
        if (fill < 3) {
            level.setBlock(pos, state.setValue(FILL, fill + 1), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull net.minecraft.world.phys.BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);

        if (!state.getValue(HAS_BUCKET) && held.is(Items.BUCKET)) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(HAS_BUCKET, true).setValue(FILL, 0), Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 1F);
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (state.getValue(HAS_BUCKET) && state.getValue(FILL) == 3) {
            if (!level.isClientSide) {
                ItemStack result = new ItemStack(FIItems.BIRCH_SAP_BUCKET.get());
                if (!player.addItem(result)) {
                    player.drop(result, false);
                }
                BlockState newState = state.setValue(HAS_BUCKET, false).setValue(FILL, 0);
                level.setBlock(pos, newState, Block.UPDATE_ALL);
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 1F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

}