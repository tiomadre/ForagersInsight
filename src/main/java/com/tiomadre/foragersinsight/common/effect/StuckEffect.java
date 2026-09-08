package com.tiomadre.foragersinsight.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.NotNull;

public class StuckEffect extends MobEffect {
    private static final String STUCK_TAG = "ForagersInsightStuckMovement";

    public StuckEffect() {
        super(MobEffectCategory.HARMFUL, 0xb87830);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        stopMovementActions(entity);
    }

 
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void stopMovementActions(@NotNull LivingEntity entity) {
        entity.setDeltaMovement(0.0D, Math.min(entity.getDeltaMovement().y, 0.0D), 0.0D);
        entity.hurtMarked = true;

        if (entity instanceof Mob mob) {
            mob.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            mob.getNavigation().stop();
            mob.getPersistentData().putBoolean(STUCK_TAG, true);
        }
    }

    public static void restoreMovementActions(@NotNull LivingEntity entity) {
        if (!(entity instanceof Mob mob)) return;
        if (!mob.getPersistentData().getBoolean(STUCK_TAG)) return;

        mob.goalSelector.enableControlFlag(Goal.Flag.MOVE);
        mob.getPersistentData().remove(STUCK_TAG);
    }
}