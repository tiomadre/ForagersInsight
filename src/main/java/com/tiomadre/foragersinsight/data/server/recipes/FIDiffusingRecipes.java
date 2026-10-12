package com.tiomadre.foragersinsight.data.server.recipes;

import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipe;
import com.tiomadre.foragersinsight.common.recipe.FIDiffuserRecipeBuilder;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.core.registry.FIMobEffects;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.effect.MobEffects;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class FIDiffusingRecipes {



    public static void register(RecipeOutput output){
        //Rosey
        FIDiffuserRecipeBuilder.diffusingrecipe("rosey", 200,  8.0,
                MobEffects.REGENERATION,0, "textures/scents/rosey.png",
                "foragersinsight.diffuser.rosey",
                "foragersinsight.diffuser.rosey.description")
                .addIngredient(FIItems.ROSE_PETALS,3)
                .build(output);

        FIDiffuserRecipeBuilder.diffusingrecipe("rosey_ii",800,12.0,MobEffects.REGENERATION,
                1,"textures/scents/rosey_ii.png",
                "foragersinsight.diffuser.rosey_ii",
                "foragersinsight.diffuser.rosey_ii.description")
                .addIngredient(FIBlocks.DENSE_ROSE_PETAL_MAT)
                .build(output);

        //Coniferous
        FIDiffuserRecipeBuilder.diffusingrecipe("coniferous", 500, 8.0,
                MobEffects.DAMAGE_RESISTANCE,0, "textures/scents/coniferous.png",
                "foragersinsight.diffuser.coniferous",
                "foragersinsight.diffuser.coniferous.description")
                .addIngredient(FIItems.SPRUCE_TIPS,3)
                .build(output);

        FIDiffuserRecipeBuilder.diffusingrecipe("coniferous_ii", 1500, 12.0,
                        MobEffects.DAMAGE_RESISTANCE,0, "textures/scents/coniferous_ii.png",
                        "foragersinsight.diffuser.coniferous_ii",
                        "foragersinsight.diffuser.coniferous_ii.description")
                .addIngredient(FIBlocks.DENSE_SPRUCE_TIP_MAT,3)
                .build(output);

        //Floral
        FIDiffuserRecipeBuilder.diffusingrecipe("floral",1200,8.0, FIMobEffects.BLOOM
        ,0,"textures/scents/floral.png","foragersinsight.diffuser.floral",
                "foragersinsight.diffuser.floral.description")
                .addIngredient(FIItems.LILAC_BLOOM,3)
                .build(output);

        FIDiffuserRecipeBuilder.diffusingrecipe("floral_ii",2000,12.0, FIMobEffects.BLOOM
                ,1,"textures/scents/floral_ii.png","foragersinsight.diffuser.floral_ii",
                "foragersinsight.diffuser.floral_ii.description")
                .addIngredient(FIBlocks.DENSE_ROSE_PETAL_MAT)
                .addIngredient(FIBlocks.DENSE_LILAC_BLOOM_MAT)
                .addIngredient(FIBlocks.DENSE_ROSE_PETAL_MAT)
                .build(output);

        //Foul
        FIDiffuserRecipeBuilder.diffusingrecipe("foul",100,8.0,
                FIMobEffects.ODOROUS,0,"textures/scents/foul.png",
                "foragersinsight.diffuser.foul_ii",
                "foragersinsight.diffuser.foul_ii.description")
                .addIngredient(FIBlocks.SKUNK_CABBAGE,3)
                .build(output);

        FIDiffuserRecipeBuilder.diffusingrecipe("foul_ii",1500,12.0,
        FIMobEffects.ODOROUS,0,"textures/scents/foul_ii.png",
                "foragersinsight.diffuser.foul_ii",
                "foragersinsight.diffuser.foul_ii.description")
                .addIngredient(ModBlocks.ORGANIC_COMPOST.get(),3)
                .build(output);

    }



}
