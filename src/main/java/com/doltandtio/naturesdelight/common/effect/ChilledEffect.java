package com.doltandtio.naturesdelight.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;

public class ChilledEffect extends MobEffect {

    public ChilledEffect() {
        super(MobEffectCategory.NEUTRAL, 0xc2ecff);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        ArrayList<MobEffectInstance> activeEffects = new ArrayList<>(entity.getActiveEffects());

        for (MobEffectInstance effectInstance : activeEffects) {
            // Affects only beneficial effects
            if (effectInstance.getEffect().getCategory() == MobEffectCategory.BENEFICIAL
                    && effectInstance.getEffect() != this) {
                int currentDuration = effectInstance.getDuration();
                int newDuration = currentDuration + 1; // +1 tick to counter the natural decay
                int effectAmplifier = effectInstance.getAmplifier();
                boolean ambient = effectInstance.isAmbient();
                boolean visible = effectInstance.isVisible();
                boolean showIcon = effectInstance.showIcon();

                // effects are removed and reapplied w/ increased duration
                entity.removeEffect(effectInstance.getEffect());
                MobEffectInstance newEffect = new MobEffectInstance(
                        effectInstance.getEffect(),
                        newDuration,
                        effectAmplifier,
                        ambient,
                        visible,
                        showIcon
                );
                entity.addEffect(newEffect);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}