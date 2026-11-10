package com.tiomadre.foragersinsight.core.compat.jei;

import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.registry.FIModRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeHolder;

public class FIRecipeTypes {

    public static final RecipeType<RecipeHolder<FIDiffuserRecipe>> DIFFUSING = RecipeType.createFromVanilla(FIModRecipeTypes.DIFFUSING.get());
}
