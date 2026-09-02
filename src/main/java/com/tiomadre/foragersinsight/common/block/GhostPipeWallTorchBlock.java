package com.tiomadre.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GhostPipeWallTorchBlock extends WallTorchBlock {
    private final Supplier<SimpleParticleType> flameParticle;

    public GhostPipeWallTorchBlock(Properties properties, Supplier<SimpleParticleType> flameParticle) {
        super(ParticleTypes.FLAME, properties);
        this.flameParticle = flameParticle;
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = this.defaultBlockState();
        LevelReader level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                BlockState oppositeFacingState = blockState.setValue(FACING, direction);

                if (oppositeFacingState.canSurvive(level, pos)) {
                    return oppositeFacingState;
                }
            }
        }

        return null;
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        Direction opposite = direction.getOpposite();
        double x = (double) pos.getX() + 0.5D + 0.27D * (double) opposite.getStepX();
        double y = (double) pos.getY() + 0.85D;
        double z = (double) pos.getZ() + 0.5D + 0.27D * (double) opposite.getStepZ();

        level.addParticle(this.flameParticle.get(), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}