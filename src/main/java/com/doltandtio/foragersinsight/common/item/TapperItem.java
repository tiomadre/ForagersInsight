package com.doltandtio.foragersinsight.common.item;

import com.doltandtio.foragersinsight.common.block.TapperBlock;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TapperItem extends Item {
    public TapperItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction face = context.getClickedFace();
        BlockPos placePos = clickedPos.relative(face);
        BlockState clickedState = level.getBlockState(clickedPos);

        // only usable on Sappy Birch Logs
        if (!clickedState.is(FIBlocks.SAPPY_BIRCH_LOG.get())) return InteractionResult.PASS;
        if (!level.getBlockState(placePos).canBeReplaced()) return InteractionResult.FAIL;

        BlockState tapperState = FIBlocks.TAPPER.get().defaultBlockState()
                .setValue(TapperBlock.HAS_TAPPER, false)
                .setValue(TapperBlock.FILL, 0);

        if (!level.isClientSide) {
            level.setBlock(placePos, tapperState, 3);
            level.playSound(null, placePos, clickedState.getSoundType().getPlaceSound(),
                    SoundSource.BLOCKS, 1.0F, 1.0F);

            if (!Objects.requireNonNull(context.getPlayer()).isCreative()) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
