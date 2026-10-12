package com.tiomadre.foragersinsight.core.compat.jei;

import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.registry.FIRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public class FIModRecipes {

    private final RecipeManager recipeManager;

    public FIModRecipes(){
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }


    public List<RecipeHolder<FIDiffuserRecipe>> getDiffuserRecipe() {
        return recipeManager.getAllRecipesFor(FIRecipeTypes.DIFFUSING.get());
    }

}
