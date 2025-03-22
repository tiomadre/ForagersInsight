package com.doltandtio.naturesdelight.data.server.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.function.Consumer;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.DANDELION_BUSH;
import static com.doltandtio.naturesdelight.core.registry.NDBlocks.ROSE_HIP;
import static com.doltandtio.naturesdelight.core.registry.NDItems.*;

public class NDCookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(COD_AND_PUMPKIN_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ForgeTags.RAW_FISHES_COD)
                .addIngredient(ModItems.PUMPKIN_SLICE.get())
                .addIngredient(ForgeTags.MILK)
                .addIngredient(ModItems.TOMATO.get())
                .unlockedByAnyIngredient(ModItems.PUMPKIN_SLICE.get(), Items.COD)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(RED_VELVET_CUPCAKE.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.WHEAT_DOUGH.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.COCOA_BEANS)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.BEETROOT, Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_CORDIAL.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ROSE_PETALS.get())
                .addIngredient(ROSE_PETALS.get())
                .addIngredient(ROSE_HIP.get())
                .unlockedByAnyIngredient(ROSE_PETALS.get(), ROSE_HIP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_ROASTED_ROOTS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Ingredient.of(DANDELION_ROOT.get(), Items.BEETROOT))
                .addIngredient(ROSE_HIP.get())
                .unlockedByAnyIngredient(ROSE_HIP.get(), DANDELION_ROOT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(SEASIDE_SIZZLER.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.TROPICAL_FISH)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.KELP)
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.RICE.get())
                .unlockedByAnyIngredient(Items.KELP, Items.BEETROOT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(STEAMY_KELP_RICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Items.DRIED_KELP)
                .addIngredient(Items.DRIED_KELP)
                .unlockedByAnyIngredient(Items.KELP, ModItems.RICE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);

        CookingPotRecipeBuilder.cookingPotRecipe(SEED_MILK_BUCKET.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(SUNFLOWER_KERNELS.get())
                .addIngredient(ForgeTags.SEEDS)
                .addIngredient(ForgeTags.SEEDS)
                .addIngredient(ForgeTags.SEEDS)
                .unlockedByAnyIngredient(SUNFLOWER_KERNELS.get(), ModItems.RICE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
    }
}
