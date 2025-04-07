package com.doltandtio.naturesdelight.data.server.recipes;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.doltandtio.naturesdelight.data.server.tags.NDTags;
import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ACORN_CARROT_CAKE;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import static net.minecraft.world.item.Items.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NDCraftingRecipes extends BlueprintRecipeProvider {
    public NDCraftingRecipes(GatherDataEvent e) {
        super(NaturesDelight.MOD_ID, e.getGenerator().getPackOutput());
    }
//ITEMS
    //Cookies
    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        cookie(ROSE_COOKIE, NDItems.ROSE_HIP, consumer);
        cookie(ACORN_COOKIE, BLACK_ACORN, consumer);

        //ACORN DOUGH
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_DOUGH.get(), 3)
                .requires(NDTags.ItemTag.ACORN).requires(NDTags.ItemTag.ACORN)
                .requires(NDTags.ItemTag.ACORN).requires(WATER_BUCKET)
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_DOUGH.get(), 3)
                .requires(NDTags.ItemTag.ACORN).requires(NDTags.ItemTag.ACORN)
                .requires(NDTags.ItemTag.ACORN).requires(ForgeTags.EGGS)
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get())).save(consumer);
    //DISHES
        //Comfort
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FORAGERS_GRANOLA.get())
                .requires(NDItems.ROSE_HIP.get()).requires(NDTags.ItemTag.APPLE).requires(NDItems.ROSE_HIP.get())
                .requires(NDTags.ItemTag.ACORN).requires(BOWL)
                .unlockedBy("has_rose_hip", has(NDItems.ROSE_HIP.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CREAMY_SALMON_BAGEL.get(), 2)
                .requires(ForgeTags.MILK).requires(POPPY_SEED_BAGEL.get()).requires(ForgeTags.COOKED_FISHES_SALMON)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, JAMMY_BREAKFAST_SANDWICH.get())
                .requires(SWEET_BERRIES).requires(POPPY_SEED_BAGEL.get()).requires(ModItems.BACON.get())
                .requires(ModItems.FRIED_EGG.get()).requires(SWEET_BERRIES)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RABBIT_STEW)
                .requires(BAKED_POTATO).requires(COOKED_RABBIT_LEG.get()).requires(NDTags.ItemTag.ROOTS)
                .requires(NDTags.ItemTag.MUSHROOM).requires(BOWL)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get())).save(consumer);
        //Nourishment
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NDItems.ROSE_GRANITA.get())
                .requires(NDItems.ROSE_HIP.get()).requires(ROSE_PETALS.get()).requires(MELON_SLICE)
                .requires(NDTags.ItemTag.ICE).requires(GLASS_BOTTLE)
                .unlockedBy("has_rose_hip", has(NDItems.ROSE_HIP.get())).save(consumer);
        //Salads
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_AND_BEET_SALAD.get())
                .requires(KELP).requires(INK_SAC).requires(ForgeTags.CROPS_TOMATO)
                .requires(ForgeTags.CROPS_ONION).requires(ForgeTags.COOKED_FISHES_COD).requires(KELP)
                .unlockedBy("has_kelp", has(KELP)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MEADOW_MEDLEY.get())
                .requires(NDTags.ItemTag.APPLE).requires(NDTags.ItemTag.POPPY_SEEDS).requires(NDTags.ItemTag.POPPY_SEEDS)
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
                .unlockedBy("has_sunflower_kernels", has(SUNFLOWER_KERNELS.get())).save(consumer);

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


 //BLOCKS
        //Feats and Cakes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_CARROT_CAKE.get())
                .requires(ACORN_DOUGH.get()).requires(CARROT).requires(CARROT)
                .requires(SUGAR).requires(ForgeTags.EGGS).requires(SUGAR)
                .requires(ForgeTags.MILK).requires(ForgeTags.MILK).requires(ForgeTags.MILK)
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get())).save(consumer);
        //Storage
        this.storageRecipes(consumer, RecipeCategory.FOOD, NDItems.ROSE_HIP.get(), RecipeCategory.DECORATIONS, ROSE_HIP_SACK.get());

        this.storageRecipes(consumer, RecipeCategory.FOOD, POPPY_SEEDS.get(), RecipeCategory.DECORATIONS, POPPY_SEEDS_SACK.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, DANDELION_ROOT.get(), RecipeCategory.DECORATIONS, DANDELION_ROOTS_CRATE.get());

        this.storageRecipes(consumer, RecipeCategory.FOOD, BLACK_ACORN.get(), RecipeCategory.DECORATIONS, BLACK_ACORN_SACK.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, SPRUCE_TIPS.get(), RecipeCategory.DECORATIONS, SPRUCE_TIPS_SACK.get());

        NDCookingRecipes.buildRecipes(consumer);
        NDCuttingRecipes.buildRecipes(consumer);
    }


    private void cookie(Supplier<Item> cookie, Supplier<? extends ItemLike> ingred, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, cookie.get(), 8)
                .requires(ingred.get()).requires(Items.WHEAT, 2)
                .unlockedBy("has_ing", has(ingred.get())).save(consumer);
    }
}
