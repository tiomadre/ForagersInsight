package com.doltandtio.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SandyFlowerBlock extends FlowerBlock {
    public SandyFlowerBlock(Supplier<MobEffect> effect, int effectDuration, Properties properties) {
        super(effect, effectDuration, properties);
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(BlockTags.SAND) || super.mayPlaceOn(state, level, pos);
    }
}