package com.doltandtio.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FoliageMatBlock extends CarpetBlock {
    public FoliageMatBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.1F)
                .sound(SoundType.PINK_PETALS)
                .noOcclusion()
                .mapColor(MapColor.PLANT)
                .isValidSpawn((state, level, pos, entityType) -> false));
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        // placeable on lower slabs
        if (belowState.getBlock() instanceof SlabBlock && belowState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) {
            return true;
        }

        // placeable on water
        return belowState.isFaceSturdy(level, belowPos, Direction.UP)
                || Block.isFaceFull(belowState.getCollisionShape(level, belowPos), Direction.UP)
                || belowState.is(Blocks.WATER);
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction,
                                           @NotNull BlockState neighborState, @NotNull net.minecraft.world.level.LevelAccessor level,
                                           @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        // drop itself if block beneath is broken
        if (direction == Direction.DOWN && !canSurvive(state, level, currentPos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }
        //sits on top of bottom slabs
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (belowState.getBlock() instanceof SlabBlock && belowState.getValue(SlabBlock.TYPE) == SlabType.BOTTOM) {
            return Block.box(0, -8, 0, 16, -7, 16);
        }
        return Block.box(0, 0, 0, 16, 1, 16);
    }
}
