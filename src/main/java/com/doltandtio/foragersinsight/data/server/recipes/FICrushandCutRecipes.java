package com.doltandtio.foragersinsight.data.server.recipes;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.function.Consumer;

import static net.minecraft.world.item.crafting.Ingredient.of;

public class FICrushandCutRecipes {
    public static void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        //Cutting Recipes
            //Crop Cuts
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.APPLE), of(ForgeTags.TOOLS_KNIVES), FIItems.APPLE_SLICE.get(), 2).addResultWithChance(FIItems.APPLE_SLICE.get(),0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.ROSE_BUSH), of(ForgeTags.TOOLS_KNIVES), FIItems.ROSE_HIP.get(), 1).addResultWithChance(FIItems.ROSE_HIP.get(),0.1f).addResultWithChance(FIItems.ROSE_PETALS.get(), 1f, 2).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(FIItems.ROSELLE_BUSH.get()), of(ForgeTags.TOOLS_KNIVES), FIItems.ROSELLE_CALYX.get(), 1).addResultWithChance(FIItems.ROSELLE_CALYX.get(),0.1f).addResultWithChance(FIItems.ROSELLE_PETALS.get(), 1f, 2).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.DANDELION), of(ForgeTags.TOOLS_KNIVES), FIItems.DANDELION_ROOT.get(), 1).addResultWithChance(FIItems.DANDELION_ROOT.get(),0.1f).addResultWithChance(Items.YELLOW_DYE,1f, 2).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.POPPY), of(ForgeTags.TOOLS_KNIVES), FIItems.POPPY_SEEDS.get(), 1).addResultWithChance(FIItems.POPPY_SEEDS.get(),0.1f).addResultWithChance(Items.RED_DYE,1f, 2).build(consumer);
             //Meat Cuts
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.RABBIT), of(ForgeTags.TOOLS_KNIVES), FIItems.RAW_RABBIT_LEG.get(), 2).addResultWithChance(Items.RABBIT_FOOT,0.3f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.COOKED_RABBIT), of(ForgeTags.TOOLS_KNIVES), FIItems.COOKED_RABBIT_LEG.get(), 2).addResultWithChance(Items.RABBIT_FOOT,0.3f).build(consumer);
            //Cake Slices
        CuttingBoardRecipeBuilder.cuttingRecipe(of(FIItems.ACORN_CARROT_CAKE_ITEM.get()), of(ForgeTags.TOOLS_KNIVES), FIItems.SLICE_OF_ACORN_CARROT_CAKE.get(), 7).build(consumer);
        //Crushing Recipes
            //Items
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.ICE), of(FITags.ItemTag.MALLETS), FIItems.CRUSHED_ICE.get(), 4).addResultWithChance(FIItems.CRUSHED_ICE.get(),0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.PACKED_ICE), of(FITags.ItemTag.MALLETS), Items.ICE, 9).addResultWithChance(Items.ICE,0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.BLUE_ICE), of(FITags.ItemTag.MALLETS), Items.PACKED_ICE, 9).addResultWithChance(Items.PACKED_ICE,0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.COBBLESTONE), of(FITags.ItemTag.MALLETS), Items.GRAVEL, 2).addResultWithChance(Items.GRAVEL,0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.GRAVEL), of(FITags.ItemTag.MALLETS), Items.FLINT, 2).addResultWithChance(Items.FLINT,0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.SANDSTONE), of(FITags.ItemTag.MALLETS), Items.SAND, 4).addResultWithChance(Items.SAND,0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.RED_SANDSTONE), of(FITags.ItemTag.MALLETS), Items.RED_SAND, 4).addResultWithChance(Items.RED_SAND,0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.GLASS), of(FITags.ItemTag.MALLETS), Items.SAND, 1).addResultWithChance(Items.SAND,0.1f).build(consumer);
            //Crushed Crops
        CuttingBoardRecipeBuilder.cuttingRecipe(of(FIItems.POPPY_SEEDS.get()), of(FITags.ItemTag.MALLETS), FIItems.POPPY_SEED_PASTE.get(), 2).addResultWithChance(FIItems.POPPY_SEED_PASTE.get(),0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(FIItems.BLACK_ACORN.get()), of(FITags.ItemTag.MALLETS), FIItems.ACORN_MEAL.get(), 2).addResultWithChance(FIItems.ACORN_MEAL.get(),0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.COCOA_BEANS), of(FITags.ItemTag.MALLETS), FIItems.COCOA_POWDER.get(), 2).addResultWithChance(FIItems.COCOA_POWDER.get(),0.1f).build(consumer);
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.WHEAT), of(FITags.ItemTag.MALLETS), FIItems.WHEAT_FLOUR.get(), 2).addResultWithChance(FIItems.WHEAT_FLOUR.get(),0.1f).build(consumer);
            //Other
        CuttingBoardRecipeBuilder.cuttingRecipe(of(Items.BONE), of(FITags.ItemTag.MALLETS), Items.BONE_MEAL, 4).addResultWithChance(Items.BONE_MEAL,0.1f).build(consumer);

    }


}
