package com.doltandtio.naturesdelight.common.effect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class MedicinalEffect extends MobEffect {
    public MedicinalEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x0a5f38);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // On application, perform the healing one time.
        if (!entity.getPersistentData().getBoolean("naturesdelight:medicinal_healed")) {
            int removalCount = 0;
            // Checks for Poison and Wither.
            if (entity.hasEffect(MobEffects.POISON)) {
                removalCount++;
            }
            if (entity.hasEffect(MobEffects.WITHER)) {
                removalCount++;
            }
            // Heal heart per effect purged, 2 hearts max.
            int heartsToHeal = Math.min(removalCount, 2);
            if (heartsToHeal > 0) {
                entity.heal(heartsToHeal * 2.0F);
            }
            // Mark so healing from effect only occurs once.
            entity.getPersistentData().putBoolean("naturesdelight:medicinal_healed", true);
        }
        // Always remove any Poison or Wither effects.
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
