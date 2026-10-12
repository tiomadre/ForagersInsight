package com.tiomadre.foragersinsight.core.compat;

import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.compat.jei.DiffusingRecipeCategory;
import com.tiomadre.foragersinsight.core.compat.jei.FIModRecipes;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.core.registry.FIRecipeTypes;
import com.tiomadre.foragersinsight.core.registry.FIRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIComp implements IModPlugin {

    public static final RecipeType<RecipeHolder<FIDiffuserRecipe>> DIFFUSING = RecipeType.createFromVanilla(FIRecipeTypes.DIFFUSING.get());


    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ForagersInsight.rl("jei_plugin");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new DiffusingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        FIModRecipes modRecipes = new FIModRecipes();
        registration.addRecipes(DIFFUSING, modRecipes.getDiffuserRecipe());
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack((FIItems.DIFFUSER.get())),DIFFUSING);
    }
}