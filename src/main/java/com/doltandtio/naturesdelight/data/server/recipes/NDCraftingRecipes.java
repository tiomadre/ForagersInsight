package com.doltandtio.naturesdelight.data.server.recipes;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.doltandtio.naturesdelight.data.server.tags.NDTags;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.*;
import static com.doltandtio.naturesdelight.core.registry.NDItems.*;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;
import static net.minecraft.world.item.Items.MELON_SLICE;

public class NDCraftingRecipes extends BlueprintRecipeProvider {
    public NDCraftingRecipes(GatherDataEvent e) {
        super(NaturesDelight.MOD_ID, e.getGenerator().getPackOutput());
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        cookie(ROSE_COOKIE, ROSE_HIP, consumer);
        cookie(ACORN_COOKIE, BLACK_ACORN, consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NDItems.ROSE_GRANITA.get())
                .requires(ROSE_HIP.get()).requires(ROSE_PETALS.get()).requires(MELON_SLICE)
                .requires(NDTags.ItemTag.ICE).requires(GLASS_BOTTLE)
                .unlockedBy("has_rose_hip", has(ROSE_HIP.get())).save(consumer);

        this.storageRecipes(consumer, RecipeCategory.FOOD, ROSE_HIP.get(), RecipeCategory.DECORATIONS, ROSE_HIP_CRATE.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, ROSE_PETALS.get(), RecipeCategory.DECORATIONS, ROSE_PETALS_SACK.get());

        this.storageRecipes(consumer, RecipeCategory.FOOD, POPPY_SEEDS.get(), RecipeCategory.DECORATIONS, POPPY_SEEDS_SACK.get());
        this.storageRecipes(consumer, RecipeCategory.FOOD, DANDELION_ROOT.get(), RecipeCategory.DECORATIONS, DANDELION_ROOTS_CRATE.get());
        NDCookingRecipes.buildRecipes(consumer);
        NDCuttingRecipes.buildRecipes(consumer);
    }


    private void cookie(Supplier<Item> cookie, Supplier<? extends ItemLike> ingred, Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, cookie.get(), 8)
                .requires(ingred.get()).requires(Items.WHEAT, 2)
                .unlockedBy("has_ing", has(ingred.get())).save(consumer);
    }
}
