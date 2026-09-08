package com.tiomadre.foragersinsight.common.effect;

import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class OdorousEffect extends MobEffect {
    private static final int EXTRA_DRAIN_INTERVAL = 20;

    private static final int FLOWER_EXTRA_DRAIN_TICKS = 20;
    private static final int WATER_EXTRA_DRAIN_TICKS = 40;
    private static final int BLOOM_EXTRA_DRAIN_TICKS = 60;

    public OdorousEffect() {
        super(MobEffectCategory.HARMFUL, 0x6b5a41);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide()) {
            return;
        }

        MobEffectInstance odorous = entity.getEffect(this);
        if (odorous == null) {
            return;
        }

        int extraDrainTicks = getCounterbalanceDrainTicks(entity);

        if (extraDrainTicks <= 0
                || entity.tickCount % EXTRA_DRAIN_INTERVAL != 0) {
            return;
        }

        int newDuration = odorous.getDuration() - extraDrainTicks;

        entity.removeEffect(this);

        if (newDuration > 0) {
            entity.addEffect(new MobEffectInstance(
                    this,
                    newDuration,
                    odorous.getAmplifier(),
                    odorous.isAmbient(),
                    odorous.isVisible(),
                    odorous.showIcon()
            ));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    private static int getCounterbalanceDrainTicks(LivingEntity entity) {
        if (entity.hasEffect(FIMobEffects.BLOOM)) {
            return BLOOM_EXTRA_DRAIN_TICKS;
        }

        if (entity.isInWaterOrBubble()) {
            return WATER_EXTRA_DRAIN_TICKS;
        }

        if (isNearFlowers(entity)) {
            return FLOWER_EXTRA_DRAIN_TICKS;
        }

        return 0;
    }

    private static boolean isNearFlowers(LivingEntity entity) {
        Level level = entity.level();

        if (isFlower(level, entity.blockPosition())
                || isFlower(level, entity.blockPosition().above())) {
            return true;
        }

        return BlockPos.betweenClosedStream(
                        entity.getBoundingBox().inflate(1.5D, 1.0D, 1.5D)
                )
                .anyMatch(pos -> isFlower(level, pos));
    }

    private static boolean isFlower(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(BlockTags.FLOWERS);
    }
}