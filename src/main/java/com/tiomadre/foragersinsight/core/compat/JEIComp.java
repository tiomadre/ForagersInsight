package com.tiomadre.foragersinsight.core.compat;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.compat.jei.DiffusingRecipeCategory;
import com.tiomadre.foragersinsight.core.compat.jei.FIJEIRecipes;
import com.tiomadre.foragersinsight.core.compat.jei.FIRecipeTypes;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIComp implements IModPlugin {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "jei_plugin");




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
        FIJEIRecipes modRecipes = new FIJEIRecipes();
        registration.addRecipes(FIRecipeTypes.DIFFUSING, modRecipes.getDiffuserRecipe());
    }

    @Override
    public void registerRecipeCatalysts(@NotNull IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack((FIItems.DIFFUSER.get())), FIRecipeTypes.DIFFUSING);
    }
}