package com.tiomadre.foragersinsight.common.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.other.toolevents.WaxedBoots;
import com.tiomadre.foragersinsight.core.registry.FIDataComponents;
import com.tiomadre.foragersinsight.core.registry.FIRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WaxedBootsRecipe implements CraftingRecipe {
    private static final Ingredient HONEYCOMB = Ingredient.of(Items.HONEYCOMB);
    private static final Ingredient HONEYCOMB_BLOCK = Ingredient.of(Items.HONEYCOMB_BLOCK);


    private final CraftingBookCategory category;
    private final Ingredient waxedBootIngredient;

    public WaxedBootsRecipe(CraftingBookCategory category, Ingredient waxedBootIngredient) {
        this.category = category;
        this.waxedBootIngredient = waxedBootIngredient;
    }



    public static java.util.List<CraftingRecipe> createJeiRecipes() {
        return java.util.List.of(
                new ShapelessRecipe( "waxed_boots",
                        CraftingBookCategory.EQUIPMENT, createResult(false), createIngredients(false)),
                new ShapelessRecipe( "waxed_boots",
                        CraftingBookCategory.EQUIPMENT, createResult(true), createIngredients(true))
        );
    }

    @Override
    public CraftingBookCategory category() {
        return category;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        List<ItemStack> nonEmpty = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) nonEmpty.add(stack);
        }
        if (nonEmpty.size() != 2) return false;

        boolean hasWax = false;
        boolean hasBoot = false;
        for (ItemStack stack : nonEmpty) {
            if (waxedBootIngredient.test(stack)) {
                hasWax = true;
            } else if (stack.is(Tags.Items.FOODS)
                    && !stack.has(FIDataComponents.BOOTWAXED.get())) {
                hasBoot = true;
            }
        }
        return hasWax && hasBoot;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack bootStack = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty() && !waxedBootIngredient.test(stack) && stack.is(Tags.Items.ARMORS)){  //make boot tag later
                bootStack = stack;
                break;
            }
        }
        if (bootStack.isEmpty()) return ItemStack.EMPTY;
      

        ItemStack result = bootStack.copyWithCount(1);
        result.set(FIDataComponents.BOOTWAXED.get(), true);
        return result;
    }

    private java.util.Optional<ItemStack> getCraftingResult(CraftingContainer container) {
        ItemStack boots = ItemStack.EMPTY;
        int honeycombCount = 0;
        boolean hasHoneycombBlock = false;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }

            if (HONEYCOMB.test(stack)) {
                honeycombCount++;
                continue;
            }

            if (HONEYCOMB_BLOCK.test(stack) && !hasHoneycombBlock) {
                hasHoneycombBlock = true;
                continue;
            }

            if (WaxedBoots.isBoots(stack) && boots.isEmpty()) {
                boots = stack;
                continue;
            }

            return java.util.Optional.empty();
        }

        boolean isNormalRecipe = honeycombCount == WaxedBoots.HONEYCOMB_COUNT && !hasHoneycombBlock;
        boolean isHoneycombBlockRecipe = honeycombCount == 0 && hasHoneycombBlock;
        if (boots.isEmpty() || (!isNormalRecipe && !isHoneycombBlockRecipe)) {
            return java.util.Optional.empty();
        }

        ItemStack result = boots.copy();
        result.setCount(1);
        if (isHoneycombBlockRecipe) {
            WaxedBoots.waxWithHoneycombBlock(result);
        } else {
            WaxedBoots.wax(result);
        }
        return java.util.Optional.of(result);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return createIngredients(false);
    }

    private static NonNullList<Ingredient> createIngredients(boolean useHoneycombBlock) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(createBootsIngredient());
        if (useHoneycombBlock) {
            ingredients.add(HONEYCOMB_BLOCK);
        } else {
            ingredients.add(HONEYCOMB);
            ingredients.add(HONEYCOMB);
        }
        return ingredients;
    }

    private static Ingredient createBootsIngredient() {
        java.util.List<ItemStack> boots = BuiltInRegistries.ITEM.stream()
                .map(ItemStack::new)
                .filter(WaxedBoots::isBoots)
                .toList();
        return Ingredient.of(boots.stream());
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider registries) {
        return createResult(false);
    }

    private static ItemStack createResult(boolean useHoneycombBlock) {
        ItemStack result = new ItemStack(Items.DIAMOND_BOOTS);
        if (useHoneycombBlock) {
            WaxedBoots.waxWithHoneycombBlock(result);
        } else {
            WaxedBoots.wax(result);
        }
        return result;
    }


    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return FIRecipeSerializers.WAXED_BOOTS.get();
    }


    public static class Serializer implements RecipeSerializer<WaxedBootsRecipe> {

        public static final MapCodec<WaxedBootsRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                CraftingBookCategory.CODEC
                        .optionalFieldOf("category", CraftingBookCategory.MISC)
                        .forGetter(WaxedBootsRecipe::category),
                Ingredient.CODEC
                        .fieldOf("salt")
                        .forGetter(r -> r.waxedBootIngredient)
        ).apply(inst, WaxedBootsRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WaxedBootsRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.fromCodec(CraftingBookCategory.CODEC), WaxedBootsRecipe::category,
                        Ingredient.CONTENTS_STREAM_CODEC, r -> r.waxedBootIngredient,
                        WaxedBootsRecipe::new
                );

        @Override
        public MapCodec<WaxedBootsRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WaxedBootsRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}