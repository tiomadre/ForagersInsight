package com.doltandtio.naturesdelight.core.other;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class NDFoods {
    public static final FoodProperties ROSE_GRANITA = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.9f).build();

    public static final FoodProperties ROSE_WATER_CORDIAL = new FoodProperties.Builder()
            .nutrition(0).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1).build();
}
