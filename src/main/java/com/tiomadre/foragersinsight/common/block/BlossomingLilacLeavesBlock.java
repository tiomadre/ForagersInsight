package com.tiomadre.foragersinsight.common.block;

import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlossomingLilacLeavesBlock extends LeavesBlock implements BonemealableBlock {
    public BlossomingLilacLeavesBlock(Properties props) {
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
                level.setBlock(below, hangingState(), Block.UPDATE_CLIENTS);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        return belowState.isAir() && !belowState.is(FIBlocks.HANGING_LILAC_LEAVES.get());
    }



    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        BlockPos below = pos.below();
        level.setBlock(below, hangingState(), Block.UPDATE_CLIENTS);
    }

    private BlockState hangingState() {
        return FIBlocks.HANGING_LILAC_LEAVES.get().defaultBlockState();
    }
}