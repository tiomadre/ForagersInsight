package com.doltandtio.naturesdelight.data.server.recipes;

import com.doltandtio.naturesdelight.core.registry.NDItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

import static net.minecraft.world.item.crafting.Ingredient.of;

public class NDCuttingRecipes {
    public static void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Fruit & Vegetable Cuts
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.APPLE), of(ForgeTags.TOOLS_KNIVES), NDItems.APPLE_SLICE.get(), 2).build(consumer);
        // Meat Cuts
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.RABBIT), of(ForgeTags.TOOLS_KNIVES), NDItems.RAW_RABBIT_LEG.get(), 2)
                .addResultWithChance(Items.RABBIT_FOOT,0.3f)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.COOKED_RABBIT), of(ForgeTags.TOOLS_KNIVES), NDItems.COOKED_RABBIT_LEG.get(), 2)
                .addResultWithChance(Items.RABBIT_FOOT,0.3f)
                .build(consumer);
    }


}
