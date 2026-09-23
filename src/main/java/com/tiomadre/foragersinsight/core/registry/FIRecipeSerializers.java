package com.tiomadre.foragersinsight.core.registry;

import com.tiomadre.foragersinsight.common.crafting.WaxedBootsRecipe;
import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FIRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ForagersInsight.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES= DeferredRegister.create(Registries.RECIPE_TYPE, ForagersInsight.MOD_ID);


    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FIDiffuserRecipe>> DIFFUSER_SERIALIZER =
            RECIPE_SERIALIZERS.register("diffusing", FIDiffuserRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FIDiffuserRecipe>> DIFFUSER_TYPE =
            RECIPE_TYPES.register("diffusing", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "diffusing";
                }
            });
    

    public static final Supplier<RecipeSerializer<?>> WAXED_BOOTS = RECIPE_SERIALIZERS
            .register("crafting_special_waxedboots",(WaxedBootsRecipe.Serializer::new));


    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}