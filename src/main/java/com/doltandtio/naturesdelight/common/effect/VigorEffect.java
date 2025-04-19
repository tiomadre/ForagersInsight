package com.doltandtio.naturesdelight.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class VigorEffect extends MobEffect {

    public VigorEffect() {
        //Grants temporary health naturally regenerating.
        //Adds 1 heart , plus 0.5 hearts (1 health point) per rank.
        super(MobEffectCategory.BENEFICIAL, 0x086e3b); // Orange color for the effect
        this.addAttributeModifier(Attributes.MAX_HEALTH, "91AEAA56-376B-4498-935B-2F7F68070635", 2.0D, AttributeModifier.Operation.ADDITION);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        float maxHealth = (float) (entity.getMaxHealth());
        if (entity.getHealth() > maxHealth) {
            entity.setHealth(maxHealth);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}