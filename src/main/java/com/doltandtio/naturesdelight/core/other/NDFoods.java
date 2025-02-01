package com.doltandtio.naturesdelight.core.other;

import com.doltandtio.naturesdelight.core.registry.NDMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class NDFoods {
    public static final FoodProperties ROSE_GRANITA = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(NDMobEffects.CHILLED.get(), 300), 1).build();
    public static final FoodProperties ROSE_CORDIAL = new FoodProperties.Builder()
            .nutrition(0).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1).build();
    public static final FoodProperties ROSE_ROASTED_ROOTS = new FoodProperties.Builder()
            .nutrition(12).saturationMod(1f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600), 1).build();
    public static final FoodProperties APPLE_SLICE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.3f).alwaysEat().build();
}
