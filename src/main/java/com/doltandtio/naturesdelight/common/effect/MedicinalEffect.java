package com.doltandtio.naturesdelight.common.effect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class MedicinalEffect extends MobEffect {
    //This effect removes any Poison and Wither effects on the affected unit and heals per effect removed.
    //Healing only occurs on the initial trigger. For the remainder of the duration, Poison and Wither cannot be reapplied.
    public MedicinalEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0a5f38);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.getPersistentData().getBoolean("naturesdelight:medicinal_healed")) {
            int removalCount = 0;
            if (entity.hasEffect(MobEffects.POISON)) {
                removalCount++;
            }
            if (entity.hasEffect(MobEffects.WITHER)) {
                removalCount++;
            }
            int heartsToHeal = Math.min(removalCount, 2);
            if (heartsToHeal > 0) {
                entity.heal(heartsToHeal * (2.0F + (amplifier * 1.0F)));//Healing increased by 1/2 Heart per rank.
            }
            entity.getPersistentData().putBoolean("naturesdelight:medicinal_healed", true);
        }
        entity.removeEffect(MobEffects.POISON);
        entity.removeEffect(MobEffects.WITHER);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    // When effects end, remove mark.
    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
        entity.getPersistentData().remove("naturesdelight:medicinal_healed");
    }
}