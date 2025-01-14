package com.doltandtio.naturesdelight.data.server;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.registry.NDBlocks;
import com.doltandtio.naturesdelight.core.registry.NDItems;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.function.Consumer;

public class NDCraftingRecipes extends BlueprintRecipeProvider {
    public NDCraftingRecipes(GatherDataEvent e) {
        super(NaturesDelight.MOD_ID, e.getGenerator().getPackOutput());
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, NDItems.ROSE_COOKIE.get(), 8)
                .requires(NDBlocks.ROSE_HIP.get()).requires(Items.WHEAT, 2)
                .unlockedBy("has_rose_hip", has(NDBlocks.ROSE_HIP.get())).save(consumer);
    }
}
