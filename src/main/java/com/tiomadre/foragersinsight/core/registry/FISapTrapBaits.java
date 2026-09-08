package com.tiomadre.foragersinsight.core.registry;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class FISapTrapBaits {
    public static final int SEARCH_RANGE = 10;
    public static final int MAX_SEARCH_RANGE = 16;

    private static final List<Bait> BAITS = List.of(
            //Rabbit
                //Favorite Food
            bait(Ingredient.of(Items.DANDELION), Rabbit.class, 1.75F, MAX_SEARCH_RANGE),
                //Liked Foods
            bait(Ingredient.of(Items.CARROT, FIItems.DANDELION_ROOT.get()), Rabbit.class, 1.35F, SEARCH_RANGE),
            //Chicken
                //Favorite Food
            bait(Ingredient.of(Items.MELON_SLICE), Chicken.class, 1.75F, MAX_SEARCH_RANGE),
                //Liked Foods
            bait(Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS), Chicken.class, 1.35F, SEARCH_RANGE),
            //Ocelot
                //Favorite Food
            bait(Ingredient.of(Items.SALMON), Ocelot.class, 1.75F, MAX_SEARCH_RANGE),
                //Liked Foods
            bait(Ingredient.of(Items.COD, Items.CHICKEN, ModItems.CHICKEN_CUTS.get()), Ocelot.class, 1.35F, SEARCH_RANGE),
            //Fox
                //Favorite Food
            bait(Ingredient.of(Items.SWEET_BERRIES), Fox.class, 1.75F, MAX_SEARCH_RANGE),
                //Liked Foods
            bait(Ingredient.of(FIItems.ROSE_HIP.get(), Items.RABBIT, FIItems.RAW_RABBIT_LEG.get()), Fox.class, 1.35F, SEARCH_RANGE),
            //Pig
                //Favorite Food
            bait(Ingredient.of(FIItems.BLACK_ACORN.get(),FIItems.ACORN_MEAL.get()), Pig.class, 1.75F, MAX_SEARCH_RANGE),
                //Liked Foods
            bait(Ingredient.of(ModItems.PUMPKIN_SLICE.get(), Items.POTATO, Items.BEETROOT), Pig.class, 1.35F, SEARCH_RANGE)
    );

    private FISapTrapBaits() {
    }

    public static boolean isBait(ItemStack stack) {
        return !stack.isEmpty() && BAITS.stream().anyMatch(bait -> bait.ingredient().test(stack));
    }

    public static boolean attracts(ItemStack stack, LivingEntity entity) {
        return find(stack, entity).isPresent();
    }

    public static float stuckDurationMultiplier(ItemStack stack, LivingEntity entity) {
        return find(stack, entity).map(Bait::stuckDurationMultiplier).orElse(1.0F);
    }

    public static int searchRange(ItemStack stack, LivingEntity entity) {
        return find(stack, entity).map(Bait::searchRange).orElse(0);
    }

    private static Optional<Bait> find(ItemStack stack, LivingEntity entity) {
        return BAITS.stream()
                .filter(bait -> bait.ingredient().test(stack) && bait.target().test(entity))
                .findFirst();
    }

    private static Bait bait(Ingredient ingredient, Class<? extends LivingEntity> target, float durationMultiplier, int searchRange) {
        return new Bait(ingredient, target::isInstance, durationMultiplier, searchRange);
    }

    public record Bait(Ingredient ingredient, Predicate<LivingEntity> target, float stuckDurationMultiplier,
                        int searchRange) {
    }
}