package com.doltandtio.naturesdelight.core.other;

import com.doltandtio.naturesdelight.core.registry.NDMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class NDFoods {
//Dishes

    public static final FoodProperties MEADOW_MEDLEY = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1).build();
    public static final FoodProperties ROSE_GRANITA = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(NDMobEffects.CHILLED.get(), 300), 1).build();
    public static final FoodProperties ROSE_CORDIAL = new FoodProperties.Builder()
            .nutrition(0).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1).build();
    public static final FoodProperties ROSE_ROASTED_ROOTS = new FoodProperties.Builder()
            .nutrition(12).saturationMod(1f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600), 1).build();
//Crops
    public static final FoodProperties APPLE_SLICE = new FoodProperties.Builder().fast()
            .nutrition(2).saturationMod(0.3f).build();
    public static final FoodProperties ROSE_HIP = new FoodProperties.Builder().fast()
            .nutrition( 1).saturationMod(0.1f).build();
//Meat
    public static final FoodProperties RAW_RABBIT_LEG = new FoodProperties.Builder().meat()
        .nutrition(2).saturationMod(0.4f).build();
    public static final FoodProperties COOKED_RABBIT_LEG = new FoodProperties.Builder().meat()
            .nutrition(3).saturationMod(0.6f).build();
}
