package com.doltandtio.naturesdelight.data.server.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.function.Consumer;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.ROSE_HIP;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ROSE_PETALS;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ROSE_CORDIAL;

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
    }
}
