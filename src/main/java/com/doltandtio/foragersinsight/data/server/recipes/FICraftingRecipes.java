package com.doltandtio.foragersinsight.data.server.recipes;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import static com.doltandtio.foragersinsight.core.registry.FIBlocks.*;
import static com.doltandtio.foragersinsight.core.registry.FIItems.*;
import static com.doltandtio.foragersinsight.core.registry.FIItems.ACORN_CARROT_CAKE_ITEM;

import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import static net.minecraft.world.item.Items.*;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FICraftingRecipes extends BlueprintRecipeProvider {
    public FICraftingRecipes(GatherDataEvent e) {
        super(ForagersInsight.MOD_ID, e.getGenerator().getPackOutput());
    }
    //ITEMS
    //Cookies
    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        addVanillaOverrides(consumer);
        addFarmersDelightOverrides(consumer);
        cookie(ROSE_COOKIE, FIItems.ROSE_HIP, consumer);
        cookie(ACORN_COOKIE, BLACK_ACORN, consumer);
        //Dough
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_DOUGH.get(), 3)
                .requires(FITags.ItemTag.ACORN)
                .requires(FITags.ItemTag.ACORN)
                .requires(FITags.ItemTag.ACORN)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(ForgeTags.EGGS),
                        new Ingredient.ItemValue(new net.minecraft.world.item.ItemStack(Items.WATER_BUCKET))
                )))
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get()))
                .save(consumer);
        //DISHES
        //Comfort
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FORAGERS_GRANOLA.get())
                .requires(FIItems.ROSE_HIP.get()).requires(FITags.ItemTag.APPLE).requires(FIItems.ROSE_HIP.get())
                .requires(FITags.ItemTag.ACORN).requires(BOWL)
                .unlockedBy("has_rose_hip", has(FIItems.ROSE_HIP.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CREAMY_SALMON_BAGEL.get(), 2)
                .requires(ForgeTags.MILK).requires(POPPY_SEED_BAGEL.get()).requires(ForgeTags.COOKED_FISHES_SALMON)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, JAMMY_BREAKFAST_SANDWICH.get())
                .requires(SWEET_BERRIES).requires(POPPY_SEED_BAGEL.get()).requires(ModItems.BACON.get())
                .requires(ModItems.FRIED_EGG.get()).requires(SWEET_BERRIES)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RABBIT_STEW)
                .requires(BAKED_POTATO).requires(COOKED_RABBIT_LEG.get()).requires(FITags.ItemTag.ROOTS)
                .requires(FITags.ItemTag.MUSHROOM).requires(BOWL)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get()))
                .save(consumer, new ResourceLocation(ForagersInsight.MOD_ID, "stew_from_rabbit_leg"));
        //Nourishment

        //Salads
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_AND_BEET_SALAD.get())
                .requires(KELP).requires(INK_SAC).requires(ForgeTags.CROPS_TOMATO)
                .requires(ForgeTags.CROPS_ONION).requires(ForgeTags.COOKED_FISHES_COD).requires(KELP)
                .unlockedBy("has_kelp", has(KELP)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MEADOW_MEDLEY.get())
                .requires(FITags.ItemTag.APPLE).requires(FITags.ItemTag.POPPY_SEEDS).requires(FITags.ItemTag.POPPY_SEEDS)
                .requires(DANDELION).requires(DANDELION)
                .requires(BOWL)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(consumer);
        //Sandwiches + Finger Foods
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_WRAP.get())
                .requires(KELP).requires(INK_SAC).requires(ForgeTags.CROPS_TOMATO)
                .requires(ForgeTags.CROPS_ONION).requires(ForgeTags.COOKED_FISHES_COD).requires(KELP)
                .unlockedBy("has_kelp", has(KELP)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_BUTTER_JAMWICH.get())
                .requires(BREAD).requires(SUNFLOWER_BUTTER.get()).requires(SWEET_BERRIES)
                .requires(SWEET_BERRIES)
                .unlockedBy("has_sunflower_kernels", has(FIItems.SUNFLOWER_KERNELS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SWEET_ROASTED_RABBIT_LEG.get(), 2)
                .requires(COOKED_RABBIT_LEG.get()).requires(COOKED_RABBIT_LEG.get()).requires(FITags.ItemTag.POPPY_SEEDS)
                .requires(FITags.ItemTag.POPPY_SEEDS).requires(HONEY_BOTTLE)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get())).save(consumer);

        //OTHER
        //Seed Milk
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_MILK_BOTTLE.get())
                .requires(SEED_MILK_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_seed_milk_bucket", has(SEED_MILK_BUCKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_MILK_BUCKET.get())
                .requires(BUCKET).requires(SEED_MILK_BOTTLE.get()).requires(SEED_MILK_BOTTLE.get())
                .requires(SEED_MILK_BOTTLE.get()).requires(SEED_MILK_BOTTLE.get())
                .unlockedBy("has_seed_milk_bottle", has(SEED_MILK_BOTTLE.get())).save(consumer);
        //Sap and Syrup
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BIRCH_SYRUP_BOTTLE.get())
                .requires(BIRCH_SYRUP_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_birch_syrup_bucket", has(BIRCH_SYRUP_BUCKET.get()))
                .save(consumer, ForagersInsight.rl("birch_syrup_bottle_from_bucket"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BIRCH_SYRUP_BUCKET.get())
                .requires(BUCKET).requires(BIRCH_SYRUP_BOTTLE.get()).requires(BIRCH_SYRUP_BOTTLE.get())
                .requires(BIRCH_SYRUP_BOTTLE.get()).requires(BIRCH_SYRUP_BOTTLE.get())
                .unlockedBy("has_birch_syrup_bottle", has(BIRCH_SYRUP_BOTTLE.get()))
                .save(consumer, ForagersInsight.rl("birch_syrup_bucket_from_bottles"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BIRCH_SAP_BOTTLE.get())
                .requires(BIRCH_SAP_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_birch_sap_bucket", has(BIRCH_SAP_BUCKET.get()))
                .save(consumer, ForagersInsight.rl("birch_sap_bottle_from_bucket"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BIRCH_SAP_BUCKET.get())
                .requires(BUCKET).requires(BIRCH_SAP_BOTTLE.get()).requires(BIRCH_SAP_BOTTLE.get())
                .requires(BIRCH_SAP_BOTTLE.get()).requires(BIRCH_SAP_BOTTLE.get())
                .unlockedBy("has_birch_sap_bottle", has(BIRCH_SAP_BOTTLE.get()))
                .save(consumer, ForagersInsight.rl("birch_sap_bucket_from_bottles"));
        // Sap to Syrup Smeltin'
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BIRCH_SAP_BUCKET.get()), RecipeCategory.FOOD, BIRCH_SYRUP_BUCKET.get(), 1.0F, 200)
                .unlockedBy("has_birch_sap_bucket", has(BIRCH_SAP_BUCKET.get()))
                .save(consumer, ForagersInsight.rl("birch_syrup_bucket_from_smelting"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BIRCH_SAP_BOTTLE.get()), RecipeCategory.FOOD, BIRCH_SYRUP_BOTTLE.get(), 1.0F, 200)
                .unlockedBy("has_birch_sap_bucket", has(BIRCH_SAP_BUCKET.get()))
                .save(consumer, ForagersInsight.rl("birch_syrup_bottle_from_smelting"));

        //Tools
        //Flint Shears
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, FLINT_SHEARS.get())
                .requires(FLINT).requires(FLINT)
                .unlockedBy("has_flint", has(FLINT))
                .save(consumer);
        //Mallets
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FLINT_MALLET.get())
                .pattern("fff")
                .pattern(" s ")
                .define('f', FLINT)
                .define('s', STICK)
                .unlockedBy("has_flint", has(FLINT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IRON_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', IRON_INGOT)
                .define('s', STICK)
                .unlockedBy("has_iron", has(IRON_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GOLD_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', GOLD_INGOT)
                .define('s', STICK)
                .unlockedBy("has_gold", has(GOLD_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DIAMOND_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', DIAMOND)
                .define('s', STICK)
                .unlockedBy("has_diamond", has(DIAMOND))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, NETHERITE_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', NETHERITE_INGOT)
                .define('s', STICK)
                .unlockedBy("has_netherite",has(NETHERITE_INGOT))
                .save(consumer);
        //Handbasket
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HANDBASKET.get())
                .pattern(" ) ")
                .pattern(": :")
                .pattern("):)")
                .define(':', ModItems.STRAW.get())
                .define(')', STICK)
                .unlockedBy("has_straw", has(ModItems.STRAW.get()))
                .save(consumer);
        //Tapper
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, FIItems.TAPPER.get())
                .requires(ModItems.FLINT_KNIFE.get())
                .requires(BUCKET)
                .unlockedBy("has_flint_knife", has(ModItems.FLINT_KNIFE.get()))
                .unlockedBy("has_bucket", has(BUCKET))
                .save(consumer);
        //BLOCKS
        //Decorative
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_ROSE_PETAL_MAT.get())
                .requires(FIItems.ROSE_PETALS.get(), 4)
                .unlockedBy("has_rose_petals", has(FIItems.ROSE_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_SPRUCE_TIP_MAT.get())
                .requires(FIItems.SPRUCE_TIPS.get(), 4)
                .unlockedBy("has_spruce_tips", has(FIItems.SPRUCE_TIPS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_ROSE_PETAL_MAT.get())
                .requires(FIBlocks.SCATTERED_ROSE_PETAL_MAT.get(), 2)
                .unlockedBy("has_scattered_rose_petals", has(FIBlocks.SCATTERED_ROSE_PETAL_MAT.get()))
                .save(consumer, ForagersInsight.rl("dense_rose_petal_mat"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_SPRUCE_TIP_MAT.get())
                .requires(FIBlocks.SCATTERED_SPRUCE_TIP_MAT.get(), 2)
                .unlockedBy("has_scattered_spruce_tips", has(FIBlocks.SCATTERED_SPRUCE_TIP_MAT.get()))
                .save(consumer, ForagersInsight.rl("dense_spruce_tip_mat"));
        //Feasts and Cakes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_CARROT_CAKE_ITEM.get())
                .requires(ACORN_DOUGH.get()).requires(CARROT).requires(CARROT)
                .requires(SUGAR).requires(ForgeTags.EGGS).requires(SUGAR)
                .requires(ForgeTags.MILK).requires(ForgeTags.MILK).requires(ForgeTags.MILK)
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get())).save(consumer);
        //Storage
        this.storageRecipes(consumer, RecipeCategory.FOOD, FIItems.ROSE_HIP.get(), RecipeCategory.DECORATIONS, ROSE_HIP_SACK.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, POPPY_SEEDS.get(), RecipeCategory.DECORATIONS, POPPY_SEEDS_SACK.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, DANDELION_ROOT.get(), RecipeCategory.DECORATIONS, DANDELION_ROOTS_CRATE.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, BLACK_ACORN.get(), RecipeCategory.DECORATIONS, BLACK_ACORN_SACK.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, SPRUCE_TIPS.get(), RecipeCategory.DECORATIONS, SPRUCE_TIPS_SACK.get());

        FICookingRecipes.buildRecipes(consumer);
        FICrushandCutRecipes.buildRecipes(consumer);
    }
    private void addVanillaOverrides(Consumer<FinishedRecipe> consumer) {
        //Bread
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.BREAD)
                .pattern("WWW")
                .define('W', FITags.ItemTag.WHEAT)
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(consumer, new ResourceLocation("minecraft", "bread"));

        //Cookie
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.COOKIE, 8)
                .requires(FITags.ItemTag.WHEAT)
                .requires(FITags.ItemTag.COCOA)
                .requires(FITags.ItemTag.WHEAT)
                .unlockedBy("has_cocoa", has(COCOA_BEANS))
                .save(consumer, new ResourceLocation("minecraft", "cookie"));

        //Cake
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.CAKE)
                .pattern("MMM")
                .pattern("SES")
                .pattern("WWW")
                .define('M', ForgeTags.MILK)
                .define('S', Items.SUGAR)
                .define('E', ForgeTags.EGGS)
                .define('W', FITags.ItemTag.WHEAT)
                .unlockedBy("has_egg", has(Items.EGG))
                .save(consumer, new ResourceLocation("minecraft", "cake"));
    }
    private void addFarmersDelightOverrides(Consumer<FinishedRecipe> consumer) {
    //Wheat Dough
        net.minecraftforge.common.crafting.ConditionalRecipe.builder()
                .addCondition(new net.minecraftforge.common.crafting.conditions.ModLoadedCondition("farmersdelight"))
                .addRecipe(r -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, vectorwing.farmersdelight.common.registry.ModItems.WHEAT_DOUGH.get(), 3)
                        .pattern("EW ")
                        .pattern("WW ")
                        .define('W', FITags.ItemTag.WHEAT)
                        .define('E', Ingredient.fromValues(java.util.stream.Stream.of(
                                new Ingredient.TagValue(vectorwing.farmersdelight.common.tag.ForgeTags.EGGS),
                                new Ingredient.ItemValue(new net.minecraft.world.item.ItemStack(Items.WATER_BUCKET)))))
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(r))
                .build(consumer, new ResourceLocation("farmersdelight", "wheat_dough"));
    //Pie Crust
        net.minecraftforge.common.crafting.ConditionalRecipe.builder()
                .addCondition(new net.minecraftforge.common.crafting.conditions.ModLoadedCondition("farmersdelight"))
                .addRecipe(r -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.PIE_CRUST.get())
                        .pattern("WMW")
                        .pattern(" W ")
                        .define('W', FITags.ItemTag.WHEAT)
                        .define('M', ForgeTags.MILK)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(r))
                .build(consumer, new ResourceLocation("farmersdelight", "pie_crust"));
        // Honey Cookie (shapeless)
        net.minecraftforge.common.crafting.ConditionalRecipe.builder()
                .addCondition(new net.minecraftforge.common.crafting.conditions.ModLoadedCondition("farmersdelight"))
                .addRecipe(r -> ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HONEY_COOKIE.get(), 8)
                        .requires(FITags.ItemTag.WHEAT)
                        .requires(FITags.ItemTag.WHEAT)
                        .requires(HONEY_BOTTLE)
                        .unlockedBy("has_honey", has(HONEY_BOTTLE))
                        .save(r))
                .build(consumer, new ResourceLocation("farmersdelight", "honey_cookie"));

// Sweet Berry Cookie (shapeless)
        net.minecraftforge.common.crafting.ConditionalRecipe.builder()
                .addCondition(new net.minecraftforge.common.crafting.conditions.ModLoadedCondition("farmersdelight"))
                .addRecipe(r -> ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SWEET_BERRY_COOKIE.get(), 8)
                        .requires(FITags.ItemTag.WHEAT)
                        .requires(SWEET_BERRIES)
                        .requires(FITags.ItemTag.WHEAT)
                        .unlockedBy("has_sweet_berries", has(SWEET_BERRIES))
                        .save(r))
                .build(consumer, new ResourceLocation("farmersdelight", "sweet_berry_cookie"));
// Chocolate Pie
        net.minecraftforge.common.crafting.ConditionalRecipe.builder()
                .addCondition(new net.minecraftforge.common.crafting.conditions.ModLoadedCondition("farmersdelight"))
                .addRecipe(r -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHOCOLATE_PIE.get())
                        .pattern("CCC")
                        .pattern("MMM")
                        .pattern("SPS")
                        .define('C', FITags.ItemTag.COCOA)
                        .define('M', ForgeTags.MILK)
                        .define('S', SUGAR)
                        .define('P', ModItems.PIE_CRUST.get())
                        .unlockedBy("has_cocoa_beans", has(COCOA_BEANS))
                        .save(r))
                .build(consumer, new ResourceLocation("farmersdelight", "chocolate_pie"));
// Apple Pie
        net.minecraftforge.common.crafting.ConditionalRecipe.builder()
                .addCondition(new net.minecraftforge.common.crafting.conditions.ModLoadedCondition("farmersdelight"))
                .addRecipe(r -> ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.APPLE_PIE.get())
                        .pattern("WWW")
                        .pattern("AAA")
                        .pattern("SCS")
                        .define('W', FITags.ItemTag.WHEAT)
                        .define('A', FITags.ItemTag.APPLE)
                        .define('S', SUGAR)
                        .define('C', ModItems.PIE_CRUST.get())
                        .unlockedBy("has_apple", has(APPLE))
                        .save(r))
                .build(consumer, new ResourceLocation("farmersdelight", "apple_pie"));
    }

    private void cookie(Supplier<Item> cookie, Supplier<? extends ItemLike> ingred, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, cookie.get(), 8)
                .requires(FITags.ItemTag.WHEAT)
                .requires(ingred.get())
                .requires(FITags.ItemTag.WHEAT)
                .unlockedBy("has_ing", has(ingred.get()))
                .save(consumer);
    }
}
