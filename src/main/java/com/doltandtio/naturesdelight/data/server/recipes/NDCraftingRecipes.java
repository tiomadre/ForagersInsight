package com.doltandtio.naturesdelight.data.server.recipes;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.doltandtio.naturesdelight.data.server.tags.NDTags;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.*;
import static net.minecraft.world.item.Items.*;

public class NDCraftingRecipes extends BlueprintRecipeProvider {
    public NDCraftingRecipes(GatherDataEvent e) {
        super(NaturesDelight.MOD_ID, e.getGenerator().getPackOutput());
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        cookie(ROSE_COOKIE, NDItems.ROSE_HIP, consumer);
        cookie(ACORN_COOKIE, BLACK_ACORN, consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NDItems.ROSE_GRANITA.get())
                .requires(NDItems.ROSE_HIP.get()).requires(ROSE_PETALS.get()).requires(MELON_SLICE)
                .requires(NDTags.ItemTag.ICE).requires(GLASS_BOTTLE)
                .unlockedBy("has_rose_hip", has(NDItems.ROSE_HIP.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MEADOW_MEDLEY.get())
                .requires(APPLE_SLICE.get()).requires(POPPY_SEEDS.get()).requires(POPPY_SEEDS.get())
                .requires(DANDELION).requires(DANDELION)
                .requires(BOWL)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_WRAP.get())
                .requires(KELP).requires(INK_SAC).requires(ForgeTags.CROPS_TOMATO)
                .requires(ForgeTags.CROPS_ONION).requires(ForgeTags.COOKED_FISHES_COD).requires(KELP)
                .unlockedBy("has_kelp", has(KELP)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_AND_BEET_SALAD.get())
                .requires(KELP).requires(INK_SAC).requires(ForgeTags.CROPS_TOMATO)
                .requires(ForgeTags.CROPS_ONION).requires(ForgeTags.COOKED_FISHES_COD).requires(KELP)
                .unlockedBy("has_kelp", has(KELP)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_MILK_BOTTLE.get())
                .requires(SEED_MILK_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_seed_milk_bucket", has(SEED_MILK_BUCKET.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_MILK_BUCKET.get())
                .requires(BUCKET).requires(SEED_MILK_BOTTLE.get()).requires(SEED_MILK_BOTTLE.get())
                .requires(SEED_MILK_BOTTLE.get()).requires(SEED_MILK_BOTTLE.get())
                .unlockedBy("has_seed_milk_bottle", has(SEED_MILK_BOTTLE.get())).save(consumer);


        //FD Recipe Additions


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
