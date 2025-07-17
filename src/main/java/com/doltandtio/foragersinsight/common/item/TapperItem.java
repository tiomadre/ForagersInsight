package com.doltandtio.foragersinsight.common.item;

import com.doltandtio.foragersinsight.common.block.TapperBlock;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TapperItem extends Item {
    public TapperItem(Properties props) {
        super(props);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos clicked = ctx.getClickedPos();
        Direction face = ctx.getClickedFace();

        BlockPos logPos;
        BlockState clickedState = level.getBlockState(clicked);
        if (clickedState.getBlock() instanceof TapperBlock) {
            Direction out = clickedState.getValue(TapperBlock.FACING);
            logPos = clicked.relative(out.getOpposite());
        } else {
            logPos = clicked;
        }

        // tapper place on: sappy birch logs
        if (!level.getBlockState(logPos).is(FIBlocks.SAPPY_BIRCH_LOG.get())
                || face.getAxis().isVertical()) {
            return InteractionResult.PASS;
        }

        BlockPos targetPos = logPos.relative(face);
        if (!level.isEmptyBlock(targetPos)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            BlockState tapperState = FIBlocks.TAPPER.get().defaultBlockState()
                    .setValue(TapperBlock.FACING, face.getOpposite())
                    .setValue(TapperBlock.HAS_TAPPER, true)
                    .setValue(TapperBlock.FILL, 0);

            level.setBlock(targetPos, tapperState, 3);

            // sounds
            SoundType type = level.getBlockState(logPos).getSoundType();
            level.playSound(null, targetPos, type.getPlaceSound(), SoundSource.BLOCKS, 1F, 1F);

            // consoom item on use
            Player player = ctx.getPlayer();
            if (player != null && !player.isCreative()) {
                ctx.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}