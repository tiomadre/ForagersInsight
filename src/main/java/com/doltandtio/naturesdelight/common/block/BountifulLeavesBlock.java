package com.doltandtio.naturesdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class BountifulLeavesBlock extends LeavesBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);

    public BountifulLeavesBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(super.defaultBlockState().setValue(AGE, 0));
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return super.isRandomlyTicking(state) || canGrow(state);
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    public int getAge(@NotNull BlockState state) {
        return state.getValue(AGE);
    }

    public boolean canGrow(BlockState state) {
        if (getAge(state) >= MAX_AGE) {
            return false;
        }

        return state.getValue(LeavesBlock.DISTANCE) < DECAY_DISTANCE;
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(AGE);
    }
}
