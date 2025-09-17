package com.doltandtio.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TallSandyFlowerBlock extends TallFlowerBlock {
    public TallSandyFlowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(BlockTags.SAND) || super.mayPlaceOn(state, level, pos);
    }
}