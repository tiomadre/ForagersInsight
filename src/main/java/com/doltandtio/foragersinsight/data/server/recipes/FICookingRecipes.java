package com.doltandtio.foragersinsight.data.server.recipes;

import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import java.util.function.Consumer;
import static com.doltandtio.foragersinsight.core.registry.FIItems.*;

public class FICookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        //Comfort
        CookingPotRecipeBuilder.cookingPotRecipe(COD_AND_PUMPKIN_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ForgeTags.RAW_FISHES_COD)
                .addIngredient(ModItems.PUMPKIN_SLICE.get())
                .addIngredient(ForgeTags.MILK)
                .addIngredient(ModItems.TOMATO.get())
                .unlockedByAnyIngredient(ModItems.PUMPKIN_SLICE.get(), Items.COD)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(STEAMY_KELP_RICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Items.DRIED_KELP)
                .addIngredient(Items.DRIED_KELP)
                .unlockedByAnyIngredient(Items.KELP, ModItems.RICE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(BLACK_FOREST_MUFFIN.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ACORN_DOUGH.get())
                .addIngredient(FITags.ItemTag.COCOA)
                .addIngredient(FITags.ItemTag.COCOA)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(BLACK_ACORN.get(), Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(RED_VELVET_CUPCAKE.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.WHEAT_DOUGH.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(FITags.ItemTag.COCOA)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.BEETROOT, Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(POPPY_SEED_BAGEL.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.WHEAT_DOUGH.get())
                .addIngredient(FITags.ItemTag.POPPY_SEEDS)
                .addIngredient(FITags.ItemTag.POPPY_SEEDS)
                .addIngredient(FITags.ItemTag.POPPY_SEEDS)
                .unlockedByAnyIngredient(POPPY_SEEDS.get(), Items.WHEAT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        //Nourishment
        CookingPotRecipeBuilder.cookingPotRecipe(ACORN_NOODLES.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ACORN_DOUGH.get())
                .addIngredient(BLACK_ACORN.get())
                .addIngredient(BLACK_ACORN.get())
                .addIngredient(ForgeTags.MILK)
                .unlockedByAnyIngredient(BLACK_ACORN.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_ROASTED_ROOTS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Ingredient.of(DANDELION_ROOT.get(), Items.BEETROOT))
                .addIngredient(FIItems.ROSE_HIP.get())
                .unlockedByAnyIngredient(FIItems.ROSE_HIP.get(), DANDELION_ROOT.get())
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

        //Drinks
        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_CORDIAL.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ROSE_PETALS.get())
                .addIngredient(ROSE_PETALS.get())
                .addIngredient(FIItems.ROSE_HIP.get())
                .unlockedByAnyIngredient(ROSE_PETALS.get(), FIItems.ROSE_HIP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(GLOWING_CARROT_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.CARROT)
                .unlockedByAnyIngredient(Items.CARROT, Items.GLOW_BERRIES)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
            //Medicinal
        CookingPotRecipeBuilder.cookingPotRecipe(DANDELION_ROOT_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(ForgeTags.MILK_BOTTLE)
                .unlockedByAnyIngredient(DANDELION_ROOT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(FOREST_ELIXIR.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(Items.HONEY_BOTTLE) //Replace with Syrup Bottle when Tree Tapping is added
                .unlockedByAnyIngredient(SPRUCE_TIPS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
            //Seed Milk
        CookingPotRecipeBuilder.cookingPotRecipe(SEED_MILK_BUCKET.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.of(SUNFLOWER_KERNELS.get(), ModItems.RICE.get()))
                .addIngredient(ForgeTags.SEEDS)
                .addIngredient(ForgeTags.SEEDS)
                .addIngredient(ForgeTags.SEEDS)
                .addIngredient(ForgeTags.SEEDS)
                .unlockedByAnyIngredient(SUNFLOWER_KERNELS.get(), ModItems.RICE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
        //Other
        CookingPotRecipeBuilder.cookingPotRecipe(DANDELION_FRIES.get(), 1, FAST_COOKING, SMALL_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .unlockedByAnyIngredient(DANDELION_ROOT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(GREEN_SAUCE.get(), 1, NORMAL_COOKING,MEDIUM_EXP)
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .unlockedByAnyIngredient(SPRUCE_TIPS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);
        CookingPotRecipeBuilder.cookingPotRecipe(SUNFLOWER_BUTTER.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(SUNFLOWER_KERNELS.get())
                .addIngredient(SUNFLOWER_KERNELS.get())
                .addIngredient(SUNFLOWER_KERNELS.get())
                .unlockedByAnyIngredient(SUNFLOWER_KERNELS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .build(consumer);

    }
}
