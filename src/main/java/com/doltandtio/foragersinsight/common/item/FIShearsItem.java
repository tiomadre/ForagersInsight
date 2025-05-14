package com.doltandtio.foragersinsight.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.MushroomColonyBlock;

public class FIShearsItem extends ShearsItem {
    public FIShearsItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level     = ctx.getLevel();
        BlockPos pos    = ctx.getClickedPos();
        BlockState state= level.getBlockState(pos);

        if (state.getBlock() instanceof MushroomColonyBlock colony) {
            int age = state.getValue(colony.getAgeProperty());
            if (age > 0) {
                Block.popResource(level, pos, colony.getCloneItemStack(level, pos, state));
                level.playSound((Player)null, pos, SoundEvents.MOOSHROOM_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(colony.getAgeProperty(), age - 1), 2);
                if (!level.isClientSide()) {
                    ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), (p) -> p.broadcastBreakEvent(ctx.getHand()));
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.useOn(ctx);
    }
}
