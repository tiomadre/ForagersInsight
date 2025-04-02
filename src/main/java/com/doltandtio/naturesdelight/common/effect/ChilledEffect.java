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
    //This effect freezes the duration of any Beneficial effects on the affected target for the length of Chilled.
    //Chilled is tagged as Neutral to avoid extending itself.
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        ArrayList<MobEffectInstance> activeEffects = new ArrayList<>(entity.getActiveEffects());

        for (MobEffectInstance effectInstance : activeEffects) {
            if (effectInstance.getEffect().getCategory() == MobEffectCategory.BENEFICIAL
                    && effectInstance.getEffect() != this) {
                int currentDuration = effectInstance.getDuration();
                int newDuration = currentDuration + 1;
                int effectAmplifier = effectInstance.getAmplifier();
                boolean ambient = effectInstance.isAmbient();
                boolean visible = effectInstance.isVisible();
                boolean showIcon = effectInstance.showIcon();
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