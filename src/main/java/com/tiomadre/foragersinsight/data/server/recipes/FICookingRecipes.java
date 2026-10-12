package com.tiomadre.foragersinsight.data.server.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tiomadre.foragersinsight.core.registry.FIDataComponents;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import javax.annotation.Nullable;

import static com.tiomadre.foragersinsight.core.registry.FIItems.*;
import static net.neoforged.neoforge.common.Tags.Items.*;

public class FICookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;// 10 seconds
    public static final int MODERATE_COOKING = 300;// 15 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float MODERATE_EXP = 1.3F;
    public static final float LARGE_EXP = 2.0F;

    public static void buildRecipes(@NotNull RecipeOutput output) {
        //Comfort
        CookingPotRecipeBuilder.cookingPotRecipe(BLEWIT_BITES.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(BLEWIT_MUSHROOM.get())
                .addIngredient(FITags.ItemTag.FLOURS_WHEAT)
                .addIngredient(FITags.ItemTag.BLEWIT_STUFFING)
                .addIngredient(FITags.ItemTag.FLOURS_WHEAT)
                .addIngredient(BLEWIT_MUSHROOM.get())
                .unlockedByAnyIngredient(BLEWIT_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(CARROT_POPPY_CHOWDER.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(FITags.ItemTag.FOODS_POPPY_SEEDS)
                .addIngredient(FITags.ItemTag.FOODS_POPPY_SEEDS)
                .addIngredient(Items.CARROT)
                .addIngredient(SEED_BUTTER.get())
                .unlockedByAnyIngredient(POPPY_SEEDS.get(), Items.CARROT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(COD_AND_PUMPKIN_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(CommonTags.Items.FOODS_RAW_COD)
                .addIngredient(ModItems.PUMPKIN_SLICE.get())
                .addIngredient(SEED_BUTTER.get())
                .addIngredient(ModItems.TOMATO.get())
                .unlockedByAnyIngredient(ModItems.PUMPKIN_SLICE.get(), Items.COD)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_HIP_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ROSE_HIP.get())
                .addIngredient(ROSE_HIP.get())
                .addIngredient(DRINKS_MILK)
                .unlockedByAnyIngredient(ROSE_HIP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(STEAMY_KELP_RICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Items.DRIED_KELP)
                .addIngredient(Items.DRIED_KELP)
                .unlockedByAnyIngredient(Items.KELP, ModItems.RICE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(HEARTY_SPRUCE_PILAF.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(GREEN_SAUCE.get())
                .addIngredient(ModItems.RICE.get())
                .addIngredient(FITags.ItemTag.FOODS_ACORN)
                .addIngredient(FITags.ItemTag.FOODS_ACORN)
                .addIngredient(ModItems.PUMPKIN_SLICE.get())
                .addIngredient(SPRUCE_TIPS.get())
                .unlockedByAnyIngredient(SPRUCE_TIPS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(BLACK_FOREST_MUFFIN.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ACORN_DOUGH.get())
                .addIngredient(FITags.ItemTag.FOODS_COCOA)
                .addIngredient(FITags.ItemTag.FOODS_COCOA)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(BLACK_ACORN.get(), Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(RED_VELVET_CUPCAKE.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.WHEAT_DOUGH.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(FITags.ItemTag.FOODS_COCOA)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.BEETROOT, Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(POPPY_SEED_BAGEL.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(FOODS_DOUGH)
                .addIngredient(FITags.ItemTag.FOODS_POPPY_SEEDS)
                .addIngredient(FITags.ItemTag.FOODS_POPPY_SEEDS)
                .addIngredient(FITags.ItemTag.FOODS_POPPY_SEEDS)
                .unlockedByAnyIngredient(POPPY_SEEDS.get(), Items.WHEAT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(LILAC_TEACAKE.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.WHEAT_DOUGH.get())
                .addIngredient(FITags.ItemTag.FOODS_LILAC)
                .addIngredient(FITags.ItemTag.FOODS_LILAC)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.BEETROOT, Items.COCOA_BEANS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        //Nourishment
        CookingPotRecipeBuilder.cookingPotRecipe(ACORN_NOODLES.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ACORN_DOUGH.get())
                .addIngredient(FITags.ItemTag.FOODS_ACORN)
                .addIngredient(FITags.ItemTag.FOODS_ACORN)
                .addIngredient(DRINKS_MILK)
                .unlockedByAnyIngredient(BLACK_ACORN.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(GLAZED_PORKCHOP_AND_ACORN_GRITS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.of(BIRCH_SYRUP_BOTTLE.get(), BIRCH_SYRUP_BUCKET.get()))
                .addIngredient(Items.PORKCHOP)
                .addIngredient(FITags.ItemTag.FOODS_ACORN)
                .addIngredient(FITags.ItemTag.FOODS_ACORN)
                .addIngredient(DRINKS_MILK)
                .addIngredient(SPRUCE_TIPS.get())
                .unlockedByAnyIngredient(BIRCH_SYRUP_BOTTLE.get(), BIRCH_SYRUP_BUCKET.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_ROASTED_ROOTS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.RICE.get())
                .addIngredient(Ingredient.of(DANDELION_ROOT.get(), Items.BEETROOT))
                .addIngredient(FIItems.ROSE_HIP.get())
                .unlockedByAnyIngredient(FIItems.ROSE_HIP.get(), DANDELION_ROOT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(SEASIDE_SIZZLER.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.TROPICAL_FISH)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.KELP)
                .addIngredient(Items.BEETROOT)
                .addIngredient(ModItems.RICE.get())
                .unlockedByAnyIngredient(Items.KELP, Items.BEETROOT)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(SYRUP_TOAST_STACKS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.of(BIRCH_SYRUP_BOTTLE.get(), BIRCH_SYRUP_BUCKET.get()))
                .addIngredient(Items.BREAD)
                .addIngredient(EGGS)
              .addIngredient(DRINKS_MILK)
                .unlockedByAnyIngredient(BIRCH_SYRUP_BOTTLE.get(), BIRCH_SYRUP_BUCKET.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(WOODLAND_PASTA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(GREEN_SAUCE.get())
                .addIngredient(ModItems.RAW_PASTA.get())
                .addIngredient(Ingredient.of(FITags.ItemTag.FOODS_MUSHROOM))
                .addIngredient(Ingredient.of(FITags.ItemTag.FOODS_MUSHROOM))
                .addIngredient(Ingredient.of(FITags.ItemTag.FOODS_MUSHROOM))
                .unlockedByAnyIngredient(SPRUCE_TIPS.get(), BLEWIT_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(TART_WHEAT_PILAF.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.WHEAT_SEEDS)
                .addIngredient(ROSELLE_CALYX.get())
                .addIngredient(Items.WHEAT_SEEDS)
                .addIngredient(ROSELLE_CALYX.get())
                .addIngredient(Items.WHEAT_SEEDS)
                .unlockedByAnyIngredient(ROSELLE_CALYX.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(SAVORY_PASTA_ROLL.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ModItems.RAW_PASTA.get())
                .addIngredient(SEED_BUTTER.get())
                .addIngredient(ModItems.TOMATO_SAUCE.get())
              .addIngredient(DRINKS_MILK)
                .unlockedByAnyIngredient(SEED_BUTTER.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        //Drinks
        CookingPotRecipeBuilder.cookingPotRecipe(ROSE_CORDIAL.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ROSE_PETALS.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(FIItems.ROSE_HIP.get())
                .unlockedByAnyIngredient(ROSE_PETALS.get(), FIItems.ROSE_HIP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(GLOWING_CARROT_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.CARROT)
                .unlockedByAnyIngredient(Items.CARROT, Items.GLOW_BERRIES)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output);
        //Medicinal
        CookingPotRecipeBuilder.cookingPotRecipe(DANDELION_ROOT_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(DANDELION_ROOT.get())
              .addIngredient(DRINKS_MILK)
                .unlockedByAnyIngredient(DANDELION_ROOT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(FOREST_ELIXIR.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(Ingredient.of(BIRCH_SYRUP_BOTTLE.get(), BIRCH_SYRUP_BUCKET.get()))
                .unlockedByAnyIngredient(SPRUCE_TIPS.get(), BIRCH_SYRUP_BUCKET.get(), BIRCH_SYRUP_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(ROSELLE_JUICE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ROSELLE_CALYX.get())
                .addIngredient(ROSELLE_CALYX.get())
                .addIngredient(Items.HONEY_BOTTLE)
                .unlockedByAnyIngredient(ROSELLE_CALYX.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output);
        //Seed Milk
        CookingPotRecipeBuilder.cookingPotRecipe(SEED_MILK_BUCKET.get(), 1, MODERATE_COOKING, MODERATE_EXP, Items.BUCKET)
                .addIngredient(SEEDS)
                .addIngredient(SEEDS)
                .addIngredient(SEEDS)
                .addIngredient(SEEDS)
                .unlockedByAnyIngredient(Items.WHEAT_SEEDS)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output);
         //Other Non Food
        CookingPotRecipeBuilder.cookingPotRecipe(AMADOU.get(), 3, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(FIItems.TINDER_CONK.get())
                .addIngredient(FIItems.TINDER_CONK.get())
                .addIngredient(Items.BONE_MEAL)
                .unlockedByAnyIngredient(FIItems.TINDER_CONK.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);

        CookingPotRecipeBuilder.cookingPotRecipe(CANDIED_CALYCES.get(), 2, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.SUGAR)
                .addIngredient(ROSELLE_CALYX.get())
                .addIngredient(ROSELLE_CALYX.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(ROSELLE_CALYX.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(APPLE_DIPPERS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.SUGAR)
                .addIngredient(FITags.ItemTag.FOODS_APPLE)
                .addIngredient(Ingredient.of(Items.HONEY_BOTTLE, BIRCH_SYRUP_BOTTLE.get()))
                .unlockedByAnyIngredient(Items.APPLE)
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(DANDELION_FRIES.get(), 1, FAST_COOKING, SMALL_EXP)
                .addIngredient(DANDELION_ROOT.get())
                .addIngredient(DANDELION_ROOT.get())
                .unlockedByAnyIngredient(DANDELION_ROOT.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(GREEN_SAUCE.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .addIngredient(SPRUCE_TIPS.get())
                .unlockedByAnyIngredient(SPRUCE_TIPS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(SEED_BUTTER.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(SEEDS)
                .addIngredient(SEEDS)
                .addIngredient(SEEDS)
                .unlockedByAnyIngredient(Items.WHEAT_SEEDS)
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);
        CookingPotRecipeBuilder.cookingPotRecipe(Items.SLIME_BALL, 4, MODERATE_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.of(BIRCH_SAP_BOTTLE.get(), BIRCH_SAP_BUCKET.get()))
                .addIngredient(Items.KELP)
                .addIngredient(Items.KELP)
                .addIngredient(Ingredient.of(Items.BONE, Items.BONE_MEAL))
                .unlockedByAnyIngredient(BIRCH_SAP_BUCKET.get(), BIRCH_SAP_BOTTLE.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(output);

        // Override FD Recipes
        //Hot Cocoa
        CookingPotRecipeBuilder.cookingPotRecipe(ModItems.HOT_COCOA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(DRINKS_MILK)
                        .addIngredient(Items.SUGAR)
                        .addIngredient(FITags.ItemTag.FOODS_COCOA)
                        .addIngredient(FITags.ItemTag.FOODS_COCOA)
                        .unlockedByAnyIngredient(Items.COCOA_BEANS)
                        .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                        .save(output, ResourceLocation.fromNamespaceAndPath("farmersdelight", "hot_cocoa"));
        //Apple Cider
       CookingPotRecipeBuilder.cookingPotRecipe(ModItems.APPLE_CIDER.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                        .addIngredient(FITags.ItemTag.FOODS_APPLE)
                        .addIngredient(FITags.ItemTag.FOODS_APPLE)
                        .addIngredient(Items.SUGAR)
                        .unlockedByAnyIngredient(Items.APPLE)
                        .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                        .save(output, ResourceLocation.fromNamespaceAndPath("farmersdelight", "apple_cider"));


        //Other
        buildAuspiciousStewRecipe(output, ROSE_PETALS.get(), MobEffects.REGENERATION.value(), "auspicious_stew_from_rose_petals");
        buildAuspiciousStewRecipe(output, ROSELLE_PETALS.get(), MobEffects.DAMAGE_RESISTANCE.value(), "auspicious_stew_from_roselle_petals");
        buildAuspiciousStewRecipe(output, SPRUCE_TIPS.get(), MobEffects.HEALTH_BOOST.value(), "auspicious_stew_from_spruce_tips");
        buildAuspiciousStewRecipe(output, LILAC_BLOOM.get(), FIMobEffects.BLOOM.get(), "auspicious_stew_from_lilac_bloom");



    }




    private static void buildAuspiciousStewRecipe(RecipeOutput output, ItemLike flower, MobEffect effect, String recipeName) {


        CookingPotRecipeBuilder.cookingPotRecipe(AUSPICIOUS_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Tags.Items.DRINKS_MILK)
                .addIngredient(BLEWIT_MUSHROOM.get())
                .addIngredient(BLEWIT_MUSHROOM.get())
                .addIngredient(flower)
                .unlockedByAnyIngredient(BLEWIT_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(output, ResourceLocation.fromNamespaceAndPath("foragersinsight", recipeName));

    }

    private static ItemStack createAuspiciousStew(MobEffect effect) {
        ItemStack stack = new ItemStack(FIItems.AUSPICIOUS_STEW.get());

        if (effect != null) {
            stack.getOrDefault(FIDataComponents.AUSPICIOUS_EFFECT,effect);

        }
        return stack;
    }


}
