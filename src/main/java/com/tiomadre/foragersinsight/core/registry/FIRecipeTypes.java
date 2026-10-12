package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FIRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ForagersInsight.MOD_ID);

    public static final Supplier<RecipeType<FIDiffuserRecipe>> DIFFUSING = RECIPE_TYPES.register("diffusing", () -> registerRecipeType("diffusing"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return ForagersInsight.MOD_ID + ":" + identifier;
            }
        };
    }

}
