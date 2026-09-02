package com.tiomadre.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class WildMushroomBlock extends MushroomBlock {
    private final Supplier<Block> colonyBlock;

    public WildMushroomBlock(Properties props) {
        super(null, props);
        this.colonyBlock = () -> null;
    }

    public WildMushroomBlock(Properties props, Supplier<Block> colonyBlock) {
        super(null, props);
        this.colonyBlock = colonyBlock;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (canUseHollowLogSupport(level, pos)) {
            return true;
        }

        return super.canSurvive(state, level, pos);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        Block colony = this.colonyBlock.get();
        if (colony == null) {
            return;
        }

        BlockState colonyState = colony.defaultBlockState();
        if (colonyState.canSurvive(level, pos) || canUseHollowLogSupport(level, pos)) {
            level.setBlock(pos, colonyState, Block.UPDATE_ALL);
        }
    }

    private boolean canUseHollowLogSupport(LevelReader level, BlockPos pos) {
        return HollowLogBlock.canSupportMushroomAt(level, pos);
    }
}