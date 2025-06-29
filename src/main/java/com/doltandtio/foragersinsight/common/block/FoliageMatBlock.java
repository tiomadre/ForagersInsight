package com.doltandtio.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class FoliageMatBlock extends CarpetBlock {
    public FoliageMatBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.1F).sound(SoundType.PINK_PETALS).noOcclusion().mapColor(MapColor.PLANT)
                .isValidSpawn((state, level, pos, entityType) -> false));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return belowState.isFaceSturdy(level, pos.below(), Direction.UP);
    }
}
