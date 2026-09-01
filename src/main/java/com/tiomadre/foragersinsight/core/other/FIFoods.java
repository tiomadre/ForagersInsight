package com.tiomadre.foragersinsight.core.other;

import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.function.Supplier;

public class FIFoods {
    private static FoodProperties basicFood(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build();
    }

    private static FoodProperties foodWithEffect(int nutrition, float saturation, Supplier<MobEffectInstance> effect, float chance) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).effect(effect, chance).build();
    }

    private static FoodProperties comfortFood(int nutrition, float saturation, int duration) {
        return comfortFood(nutrition, saturation, duration, 0);
    }

    private static FoodProperties comfortFood(int nutrition, float saturation, int duration, int amplifier) {
        return foodWithEffect(nutrition, saturation, () -> new MobEffectInstance(ModEffects.COMFORT, duration, amplifier), 1);
    }

    private static FoodProperties nourishmentFood(int nutrition, float saturation, int duration) {
        return foodWithEffect(nutrition, saturation, () -> new MobEffectInstance(ModEffects.NOURISHMENT, duration), 1);
    }

    public static final FoodProperties ACORN_DOUGH = foodWithEffect(2, 0.3f, () -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F);
    public static final FoodProperties SAUCE_BOWLS = basicFood(2, 0.3f);
    public static final FoodProperties SEED_MILK_BOTTLE = new FoodProperties.Builder().alwaysEdible().build();
    public static final FoodProperties SEED_MILK_BUCKET = new FoodProperties.Builder().build();
    public static final FoodProperties BIRCH_SYRUP_BOTTLE = new FoodProperties.Builder().nutrition(5).saturationModifier(1.0F).alwaysEdible().build();

    //Cuts
    public static final FoodProperties RAW_RABBIT_LEG = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.4f).build();
    public static final FoodProperties COOKED_RABBIT_LEG = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.6f).build();
    //Crops
    //This is for Apple Slices/Black Acorns
    public static final FoodProperties MID_SAT_MORSELS = new FoodProperties.Builder().fast()
            .nutrition(2).saturationModifier(0.3f).build();
    //This is for Dandelion Root/Roselle Calyx
    public static final FoodProperties LOW_SAT_MORSELS = new FoodProperties.Builder().fast()
            .nutrition(1).saturationModifier(0.1f).build();
    //This is for Poppy Seeds/Rose Hips/Spruce Tips/Lilac Bloom
    public static final FoodProperties NO_SAT_MORSELS= new FoodProperties.Builder().fast()
            .nutrition( 1).build();
    //This is for Blewits
    public static final FoodProperties BLEWIT = foodWithEffect(2, 0.45f,
            () -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.30f);
    //DISHES
    //Comfort
    public static final FoodProperties BLEWIT_BITES = comfortFood(3, 0.3f, 700);
    public static final FoodProperties CARROT_POPPY_CHOWDER = comfortFood(8, 0.7f, 2200);
    public static final FoodProperties COD_AND_PUMPKIN_STEW = comfortFood(10, 0.9f, 2500);
    public static final FoodProperties CREAMY_SALMON_BAGEL = comfortFood(4, 0.35f, 800);
    public static final FoodProperties FORAGERS_GRANOLA = comfortFood(6, 0.6f, 1500);
    public static final FoodProperties JAMMY_BREAKFAST_SANDWICH = comfortFood(8, 0.7f, 1700);
    public static final FoodProperties HEARTY_SPRUCE_PILAF = comfortFood(11, 1f, 2800);
    public static final FoodProperties ROSE_HIP_SOUP = comfortFood(6, 0.6f, 1400);
    public static final FoodProperties STEAMY_KELP_RICE = comfortFood(6, 0.5f, 1200);

    //Nourishment
    public static final FoodProperties ACORN_NOODLES = nourishmentFood(7, 0.6f, 1000);
    public static final FoodProperties ROSE_ROASTED_ROOTS = nourishmentFood(8, 0.75f, 1800);
    public static final FoodProperties SEASIDE_SIZZLER = nourishmentFood(9, 0.85f, 2050);
    public static final FoodProperties SYRUP_TOAST_STACKS = nourishmentFood(11, 1f, 2800);
    public static final FoodProperties WOODLAND_PASTA = nourishmentFood(11, 1f, 2800);
    public static final FoodProperties GLAZED_PORKCHOP_AND_ACORN_GRITS = nourishmentFood(12, 1.1f, 3000);
    public static final FoodProperties TART_WHEAT_PILAF = nourishmentFood(6, 0.6f, 1000);
    public static final FoodProperties SAVORY_PASTA_ROLL = nourishmentFood(10, 0.9f, 2500);
        //Feast Servings
        public static final FoodProperties RAINBOW_SANDWICH_SLICE = nourishmentFood(4, 0.45f, 800);

    //Other
    public static final FoodProperties AUSPICIOUS_STEW = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 500), 1).build();
    public static final FoodProperties BAKED_GOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT, 500), 1).build();
    public static final FoodProperties CAKE_SLICE = (new FoodProperties.Builder().fast())
            .nutrition(2).saturationModifier(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0, false, false), 1.0F).build();
    public static final FoodProperties KELP_WRAP = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).build();
    public static final FoodProperties SALAD = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.4f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100), 1).build();
    public static final FoodProperties DANDELION_FRIES = new FoodProperties.Builder().fast()
            .nutrition(3).saturationModifier(0.3f).build();
    public static final FoodProperties SEED_BUTTER_JAMWICH = (new FoodProperties.Builder())
            .nutrition(8).saturationModifier(0.7F).build();
    public static final FoodProperties SWEET_ROASTED_RABBIT_LEG = (new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6f)).build();
    public static final FoodProperties CANDIED_CALYCES = new FoodProperties.Builder().fast()
            .nutrition(2).saturationModifier(0.2f).build();
    public static final FoodProperties APPLE_DIPPERS = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).build();
    //DRINKS
    //Tonics (Medicinal)
    public static final FoodProperties ROSE_CORDIAL = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400), 1).build();
    public static final FoodProperties GLOWING_CARROT_JUICE = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 750), 1).build();
    public static final FoodProperties DANDELION_ROOT_TEA = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1), 1).build();
    public static final FoodProperties FOREST_ELIXIR = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance( MobEffects.HEALTH_BOOST, 1200, 0), 1.0F).build();
    public static final FoodProperties ROSELLE_JUICE = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance( FIMobEffects.BLOOM, 1200, 0), 1.0F).build();

}
