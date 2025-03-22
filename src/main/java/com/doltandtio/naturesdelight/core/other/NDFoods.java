package com.doltandtio.naturesdelight.core.other;

import com.doltandtio.naturesdelight.core.registry.NDMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class NDFoods {

    //Crops
    public static final FoodProperties APPLE_SLICE = new FoodProperties.Builder().fast()
            .nutrition(2).saturationMod(0.3f).build();
    public static final FoodProperties BLACK_ACORN = new FoodProperties.Builder().fast()
            .nutrition(2).saturationMod(0.2f).build();
    public static final FoodProperties SUNFLOWER_KERNELS = new FoodProperties.Builder().fast()
            .nutrition(1).saturationMod(0.1f).build();
            //This is for 1 Hunger no Saturation Crops: Poppy Seeds/Rose Hips/Spruce Tips
    public static final FoodProperties NO_SATURATION_PSRHST= new FoodProperties.Builder().fast()
            .nutrition( 1).build();

    //DISHES
        //Comfort
    public static final FoodProperties COD_AND_PUMPKIN_STEW = new FoodProperties.Builder()
            .nutrition(9).saturationMod(0.8f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(),2000),1).build();
    public static final FoodProperties STEAMY_KELP_RICE = new FoodProperties.Builder()
            .nutrition(7).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1250), 1).build();
        //Chilled
        public static final FoodProperties ROSE_GRANITA = new FoodProperties.Builder()
                .nutrition(6).saturationMod(0.9f)
                .effect(() -> new MobEffectInstance(NDMobEffects.CHILLED.get(), 300), 1).build();
        //Medicinal

        //Nourishment
        public static final FoodProperties ROSE_ROASTED_ROOTS = new FoodProperties.Builder()
                .nutrition(12).saturationMod(1f)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600), 1).build();
        public static final FoodProperties SEASIDE_SIZZLER = new FoodProperties.Builder()
            .nutrition(9).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3000), 1).build();

        //Salads
        public static final FoodProperties KELP_AND_BEET_SALAD = new FoodProperties.Builder()
                .nutrition(6).saturationMod(0.4f)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1).build();
        public static final FoodProperties MEADOW_MEDLEY = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1).build();

        //Sandwiches and Finger Foods
    public static final FoodProperties KELP_WRAP = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4f).build();

    //DRINKS
    public static final FoodProperties ROSE_CORDIAL = new FoodProperties.Builder()
            .nutrition(0).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200), 1).build();
        //Chilled

    //Meat
    public static final FoodProperties RAW_RABBIT_LEG = new FoodProperties.Builder().meat()
            .nutrition(2).saturationMod(0.4f).build();
    public static final FoodProperties COOKED_RABBIT_LEG = new FoodProperties.Builder().meat()
            .nutrition(3).saturationMod(0.6f).build();

    //Seed Milk
    public static final FoodProperties SEED_MILK_BOTTLE = new FoodProperties.Builder().build();
    public static final FoodProperties SEED_MILK_BUCKET = new FoodProperties.Builder().build();

    //Sweets
    public static final FoodProperties RED_VELVET_CUPCAKE = new FoodProperties.Builder()
            .nutrition(4).saturationMod(0.5f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 500), 1).build();

}
