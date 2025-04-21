package com.doltandtio.foragersinsight.data.server.recipes;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

import static net.minecraft.world.item.crafting.Ingredient.of;

public class FICuttingRecipes {
    public static void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        // Fruit & Vegetable Cuts
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.APPLE), of(ForgeTags.TOOLS_KNIVES), FIItems.APPLE_SLICE.get(), 2).build(consumer);
        // Meat Cuts
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.RABBIT), of(ForgeTags.TOOLS_KNIVES), FIItems.RAW_RABBIT_LEG.get(), 2)
                .addResultWithChance(Items.RABBIT_FOOT,0.3f)
                .build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.COOKED_RABBIT), of(ForgeTags.TOOLS_KNIVES), FIItems.COOKED_RABBIT_LEG.get(), 2)
                .addResultWithChance(Items.RABBIT_FOOT,0.3f)
                .build(consumer);
        //Cake Slices
        CuttingBoardRecipeBuilder.cuttingRecipe(of(FIItems.ACORN_CARROT_CAKE_ITEM.get()), of(ForgeTags.TOOLS_KNIVES), FIItems.SLICE_OF_ACORN_CARROT_CAKE.get(), 7).build(consumer);

    }


}
