package com.doltandtio.foragersinsight.core.other;

import com.doltandtio.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class FIFoods {
    public static final FoodProperties ACORN_DOUGH = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();
    public static final FoodProperties SAUCE_BOWLS = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.3f).build();
    public static final FoodProperties SEED_MILK_BOTTLE = new FoodProperties.Builder().build();
    public static final FoodProperties SEED_MILK_BUCKET = new FoodProperties.Builder().build();
    public static final FoodProperties BIRCH_SYRUP_BOTTLE = new FoodProperties.Builder()
            .nutrition(5).saturationMod(1.0F).alwaysEat().build();


    //Cuts
    public static final FoodProperties RAW_RABBIT_LEG = new FoodProperties.Builder().meat()
            .nutrition(2).saturationMod(0.4f).build();
    public static final FoodProperties COOKED_RABBIT_LEG = new FoodProperties.Builder().meat()
            .nutrition(3).saturationMod(0.6f).build();
    //Crops
            //This is for Apple Slices/Black Acorns
    public static final FoodProperties MID_SAT_MORSELS = new FoodProperties.Builder().fast()
            .nutrition(2).saturationMod(0.3f).build();
            //This is for Dandelion Root/Roselle Calyx
    public static final FoodProperties LOW_SAT_MORSELS = new FoodProperties.Builder().fast()
            .nutrition(1).saturationMod(0.1f).build();
            //This is for Poppy Seeds/Rose Hips/Spruce Tips
    public static final FoodProperties NO_SAT_MORSELS= new FoodProperties.Builder().fast()
            .nutrition( 1).build();
    //DISHES
        //Comfort
    public static final FoodProperties CARROT_POPPY_CHOWDER = new FoodProperties.Builder()
            .nutrition(8).saturationMod(0.7f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 2200, 1), 1).build();
    public static final FoodProperties COD_AND_PUMPKIN_STEW = new FoodProperties.Builder()
            .nutrition(10).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(),2500),1).build();
    public static final FoodProperties CREAMY_SALMON_BAGEL = new FoodProperties.Builder().fast()
            .nutrition(3).saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 800), 1).build();
    public static final FoodProperties FORAGERS_GRANOLA = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1500), 1).build();
    public static final FoodProperties JAMMY_BREAKFAST_SANDWICH = new FoodProperties.Builder()
            .nutrition(8).saturationMod(0.7f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1700), 1).build();
    public static final FoodProperties HEARTY_SPRUCE_PILAF = new FoodProperties.Builder()
            .nutrition(11).saturationMod(1f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 2800), 1).build();
    public static final FoodProperties ROSE_HIP_SOUP = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1400), 1).build();
    public static final FoodProperties STEAMY_KELP_RICE = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.5f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1200), 1).build();

        //Chilled

        //Medicinal

        //Nourishment
        public static final FoodProperties ACORN_NOODLES = new FoodProperties.Builder()
                .nutrition(7).saturationMod(0.6f)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1000), 1).build();
        public static final FoodProperties ROSE_ROASTED_ROOTS = new FoodProperties.Builder()
                .nutrition(8).saturationMod(0.75f)
                .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1800), 1).build();
        public static final FoodProperties SEASIDE_SIZZLER = new FoodProperties.Builder()
            .nutrition(9).saturationMod(0.85f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 2050), 1).build();
        public static final FoodProperties SYRUP_TOAST_STACKS = new FoodProperties.Builder()
            .nutrition(11).saturationMod(1f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 2800), 1).build();
        public static final FoodProperties WOODLAND_PASTA = new FoodProperties.Builder()
            .nutrition(11).saturationMod(1f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 2800), 1).build();
        public static final FoodProperties GLAZED_PORKCHOP_AND_ACORN_GRITS = new FoodProperties.Builder()
            .nutrition(12).saturationMod(1.1f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3000), 1).build();
        public static final FoodProperties TART_WHEAT_PILAF = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 1000), 1).build();

        //Other
        public static final FoodProperties BAKED_GOOD = new FoodProperties.Builder()
                .nutrition(4).saturationMod(0.3f)
                .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 500), 1).build();
        public static final FoodProperties CAKE_SLICE = (new FoodProperties.Builder().fast())
            .nutrition(2).saturationMod(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0, false, false), 1.0F).build();
        public static final FoodProperties KELP_WRAP = new FoodProperties.Builder()
            .nutrition(8).saturationMod(0.7f).build();
        public static final FoodProperties SALAD = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1).build();
        public static final FoodProperties DANDELION_FRIES = new FoodProperties.Builder().fast()
            .nutrition(3).saturationMod(0.3f).build();
        public static final FoodProperties SEED_BUTTER_JAMWICH = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.7F).build();
        public static final FoodProperties SWEET_ROASTED_RABBIT_LEG = (new FoodProperties.Builder()
            .nutrition(5).saturationMod(0.6f)).build();
        public static final FoodProperties CANDIED_CALYCES = new FoodProperties.Builder().fast()
                .nutrition(2).saturationMod(0.2f).build();
    //DRINKS
    public static final FoodProperties ROSE_CORDIAL = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 250), 1).build();
    public static final FoodProperties GLOWING_CARROT_JUICE = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600), 1).build();

        //Chilled

        //Medicinal
        public static final FoodProperties DANDELION_ROOT_TEA = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(FIMobEffects.MEDICINAL.get(), 1200), 1).build();
        public static final FoodProperties FOREST_ELIXIR = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance( MobEffects.HEALTH_BOOST, 1200, 0), 1.0F).build();
    //FEASTS

}
