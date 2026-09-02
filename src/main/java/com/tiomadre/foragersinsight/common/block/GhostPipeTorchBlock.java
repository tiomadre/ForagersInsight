package com.tiomadre.foragersinsight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class GhostPipeTorchBlock extends TorchBlock {
    private final Supplier<SimpleParticleType> flameParticle;

    public GhostPipeTorchBlock(Properties properties, Supplier<SimpleParticleType> flameParticle) {
        super(ParticleTypes.FLAME, properties);
        this.flameParticle = flameParticle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY() + 0.7D;
        double z = (double) pos.getZ() + 0.5D;

        level.addParticle(this.flameParticle.get(), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}