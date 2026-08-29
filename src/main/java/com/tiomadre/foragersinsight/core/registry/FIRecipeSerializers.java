package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.crafting.WaxedBootsRecipe;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FIRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ForagersInsight.MOD_ID);

    public static final Supplier<SimpleCraftingRecipeSerializer<?>> WAXED_BOOTS = RECIPE_SERIALIZERS.register(
            "crafting_special_waxedboots",
            () -> new SimpleCraftingRecipeSerializer<>(WaxedBootsRecipe::new));
}