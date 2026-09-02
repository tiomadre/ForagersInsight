package com.tiomadre.foragersinsight.common.block;

import com.mojang.serialization.MapCodec;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import com.tiomadre.foragersinsight.core.registry.FIParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SkunkCabbageBlock extends BushBlock {
    private static final int ODOROUS_DURATION_TICKS = 250;
    public static final MapCodec<SkunkCabbageBlock> CODEC = simpleCodec(SkunkCabbageBlock::new);

    public SkunkCabbageBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (!(entity instanceof LivingEntity livingEntity) || level.isClientSide()) {
            return;
        }

        livingEntity.addEffect(new MobEffectInstance(FIMobEffects.ODOROUS, ODOROUS_DURATION_TICKS, 0, false, true, true));

        if (level instanceof ServerLevel serverLevel) {
            RandomSource random = serverLevel.random;
            double x = pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.4D;
            double y = pos.getY() + 0.2D + random.nextDouble() * 0.5D;
            double z = pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.4D;
            serverLevel.sendParticles(FIParticleTypes.FOUL_SCENT.get(), x, y, z, 1, 0.03D, 0.04D, 0.03D, 0.005D);
        }
    }
}