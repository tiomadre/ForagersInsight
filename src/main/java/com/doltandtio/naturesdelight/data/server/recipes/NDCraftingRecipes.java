package com.doltandtio.naturesdelight.data.server.recipes;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.doltandtio.naturesdelight.data.server.tags.NDTags;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.Consumer;

import static com.doltandtio.naturesdelight.core.registry.NDBlocks.ROSE_HIP;
import static com.doltandtio.naturesdelight.core.registry.NDItems.ROSE_PETALS;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;
import static net.minecraft.world.item.Items.MELON_SLICE;

public class NDCraftingRecipes extends BlueprintRecipeProvider {
    public NDCraftingRecipes(GatherDataEvent e) {
        super(NaturesDelight.MOD_ID, e.getGenerator().getPackOutput());
    }

    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NDItems.ROSE_COOKIE.get(), 8)
                .requires(ROSE_HIP.get()).requires(Items.WHEAT, 2)
                .unlockedBy("has_rose_hip", has(ROSE_HIP.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NDItems.ROSE_GRANITA.get())
                .requires(ROSE_HIP.get()).requires(ROSE_PETALS.get()).requires(MELON_SLICE)
                .requires(NDTags.ItemTag.ICE).requires(GLASS_BOTTLE)
                .unlockedBy("has_rose_hip", has(ROSE_HIP.get())).save(consumer);

        NDCookingRecipes.buildRecipes(consumer);
    }
}
