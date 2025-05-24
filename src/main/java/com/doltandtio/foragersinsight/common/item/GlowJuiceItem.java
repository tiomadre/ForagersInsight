package com.doltandtio.foragersinsight.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class GlowJuiceItem extends DrinkableItem {

    public GlowJuiceItem(Properties properties) {
        super(properties, false, true);
    }
    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0)); // 600 ticks = 30 seconds
        consumer.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0));
        if (consumer.hasEffect(MobEffects.BLINDNESS)) {
            consumer.removeEffect(MobEffects.BLINDNESS);
        }
    }
}