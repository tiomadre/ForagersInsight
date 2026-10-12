package com.tiomadre.foragersinsight.common.recipe;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class FIDiffuserRecipeBuilder implements RecipeBuilder {



    private final String scentName;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final int scentDuration;
    private final double scentRadius;
    private final Holder<MobEffect> effect;
    private final int effectAmplifier;
    private final String icon;
    private final String translationKey;
    private final String descriptionKey;


    public FIDiffuserRecipeBuilder(String scentName, int scentDuration, double scentRadius, Holder<MobEffect> effect,
                                   int effectAmplifier, String icon, String translationKey, String descriptionKey){
        this.scentName=scentName;
        this.scentDuration= scentDuration;
        this.scentRadius=scentRadius;
        this.effect=effect;
        this.effectAmplifier = effectAmplifier;
        this.icon = icon;
        this.translationKey = translationKey;
        this.descriptionKey = descriptionKey;
    }

    public static FIDiffuserRecipeBuilder diffusingrecipe(String scentName, int scentDuration, double scentRadius, Holder<MobEffect> effect,
                                                     int effectAmplifier, String icon, String translationKey, String descriptionKey) {
        return new FIDiffuserRecipeBuilder(scentName,   scentDuration, scentRadius, effect, effectAmplifier,
                icon, translationKey, descriptionKey);
    }



    public FIDiffuserRecipeBuilder addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public FIDiffuserRecipeBuilder addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public FIDiffuserRecipeBuilder addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public FIDiffuserRecipeBuilder addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public FIDiffuserRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.add(ingredientIn);
        }
        return this;
    }



    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return null;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public Item getResult() {
        return null;
    }

    public void build(RecipeOutput output) {
        save(output, ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, scentName));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        ResourceLocation recipeId = resourceLocation.withPrefix("diffusing/");
        Advancement.Builder advancementBuilder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        FIDiffuserRecipe recipe = new FIDiffuserRecipe(
                this.scentName,
                this.ingredients,
                this.scentDuration,
                this.scentRadius,
                this.effect,
                this.effectAmplifier,
                this.icon,
                this.translationKey,
                this.descriptionKey

        );
        recipeOutput.accept(recipeId, recipe, advancementBuilder.build(resourceLocation.withPrefix("recipes/diffusing/")));
    }
}
