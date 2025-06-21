package com.doltandtio.foragersinsight.common.block;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BountifulSpruceLeavesBlock extends LeavesBlock {
    public BountifulSpruceLeavesBlock(Properties props) {
        super(props);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return true;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        super.randomTick(state, level, pos, rand);
        if (rand.nextInt(8) == 0) {
            BlockPos below = pos.below();
            if (level.isEmptyBlock(below)) {
                level.setBlock(below, FIBlocks.BOUNTIFUL_SPRUCE_TIPS.get().defaultBlockState(), Block.UPDATE_CLIENTS);
            }
        }
    }
}