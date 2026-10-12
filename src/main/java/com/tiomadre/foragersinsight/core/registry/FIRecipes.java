package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.data.server.recipes.FICookingRecipes;
import com.tiomadre.foragersinsight.data.server.recipes.FICrushandCutRecipes;
import com.tiomadre.foragersinsight.data.server.recipes.FIDiffusingRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class FIRecipes extends RecipeProvider {
    public FIRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output){
        FICookingRecipes.buildRecipes(output);
        FICrushandCutRecipes.buildRecipes(output);
        FIDiffusingRecipes.register(output);
    }

}
