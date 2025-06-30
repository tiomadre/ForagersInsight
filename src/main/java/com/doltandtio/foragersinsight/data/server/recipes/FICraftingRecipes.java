package com.doltandtio.foragersinsight.data.server.recipes;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import com.doltandtio.foragersinsight.data.server.tags.FITags;
import static com.doltandtio.foragersinsight.core.registry.FIBlocks.*;
import static com.doltandtio.foragersinsight.core.registry.FIItems.*;
import static com.doltandtio.foragersinsight.core.registry.FIItems.ACORN_CARROT_CAKE_ITEM;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
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
        cookie(ROSE_COOKIE, FIItems.ROSE_HIP, consumer);
        cookie(ACORN_COOKIE, BLACK_ACORN, consumer);

        //ACORN DOUGH
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FIItems.ROSE_GRANITA.get())
                .requires(FIItems.ROSE_HIP.get()).requires(ROSE_PETALS.get()).requires(MELON_SLICE)
                .requires(FITags.ItemTag.ICE).requires(GLASS_BOTTLE)
                .unlockedBy("has_rose_hip", has(FIItems.ROSE_HIP.get())).save(consumer);
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
        //Tools
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, FLINT_SHEARS.get())
                .requires(FLINT).requires(FLINT);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FLINT_MALLET.get())
                .pattern("fff")
                .pattern(" s ")
                .define('f', FLINT)
                .define('s', STICK)
                .unlockedBy("has_flint", InventoryChangeTrigger.TriggerInstance.hasItems(FLINT));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IRON_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', IRON_INGOT)
                .define('s', STICK)
                .unlockedBy("has_iron", InventoryChangeTrigger.TriggerInstance.hasItems(IRON_INGOT));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GOLD_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', GOLD_INGOT)
                .define('s', STICK)
                .unlockedBy("has_gold", InventoryChangeTrigger.TriggerInstance.hasItems(GOLD_INGOT));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DIAMOND_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', DIAMOND)
                .define('s', STICK)
                .unlockedBy("has_diamond", InventoryChangeTrigger.TriggerInstance.hasItems(DIAMOND));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, NETHERITE_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', NETHERITE_INGOT)
                .define('s', STICK)
                .unlockedBy("has_netherite", InventoryChangeTrigger.TriggerInstance.hasItems(IRON_INGOT));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HANDBASKET.get())
                .pattern(" ) ")
                .pattern(": :")
                .pattern("):)")
                .define(':', ModItems.STRAW.get())
                .define(')', STICK)
                .unlockedBy("has_straw", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAW.get()));
 //BLOCKS
        //Decorative
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SCATTERED_ROSE_PETAL_MAT.get(), 4)
                .pattern("PP")
                .pattern("PP")
                .define('P', FIItems.ROSE_PETALS.get())
                .unlockedBy("has_rose_petals", has(FIItems.ROSE_PETALS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SCATTERED_SPRUCE_TIP_MAT.get(), 4)
                .pattern("TT")
                .pattern("TT")
                .define('T', FIItems.SPRUCE_TIPS.get())
                .unlockedBy("has_spruce_tips", has(FIItems.SPRUCE_TIPS.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DENSE_ROSE_PETAL_MAT.get(), 4)
                .pattern("PP")
                .pattern("PP")
                .define('P', FIItems.ROSE_PETALS.get())
                .unlockedBy("has_rose_petals", has(FIItems.ROSE_PETALS.get()))
                .save(consumer, ForagersInsight.rl("dense_rose_petal_mat"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, DENSE_SPRUCE_TIP_MAT.get(), 4)
                .pattern("TT")
                .pattern("TT")
                .define('T', FIItems.SPRUCE_TIPS.get())
                .unlockedBy("has_spruce_tips", has(FIItems.SPRUCE_TIPS.get()))
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


    private void cookie(Supplier<Item> cookie, Supplier<? extends ItemLike> ingred, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, cookie.get(), 8)
                .requires(ingred.get()).requires(Items.WHEAT, 2)
                .unlockedBy("has_ing", has(ingred.get())).save(consumer);
    }
}
