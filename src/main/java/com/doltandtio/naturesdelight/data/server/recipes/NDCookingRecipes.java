package com.doltandtio.naturesdelight.data.server.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
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
                .build(consumer);
    }
}
