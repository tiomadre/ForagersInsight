package com.doltandtio.naturesdelight.common.block;

import com.doltandtio.naturesdelight.data.server.tags.NDTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    private boolean canPlace(BlockPlaceContext context, BlockState state, Player player) {
        // only allow placing on birch logs if the player is sneaking
        return state.is(NDTags.BlockTag.TAPPABLE) && player.isCrouching() && context.canPlace();
    }

    public boolean placeTapper(Level level, BlockPlaceContext context, Player player, BlockState state) {
        if (!canPlace(context, state, player)) {
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

}
