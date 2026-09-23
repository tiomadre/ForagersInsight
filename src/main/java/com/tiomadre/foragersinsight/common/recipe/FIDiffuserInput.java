package com.tiomadre.foragersinsight.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.Optional;


public record FIDiffuserInput(ItemStack ingredient1, ItemStack ingredient2, ItemStack ingredient3) implements RecipeInput {
    public ItemStack getItem(int index) {
        ItemStack var10000;
               switch (index) {
               case 0 -> var10000 = this.ingredient1;
               case 1 -> var10000 = this.ingredient2;
               case 2 -> var10000= this.ingredient3;
               default -> throw new IllegalArgumentException("Recipe does not contain slot " + index);
           }

        return var10000;
    }

    public int size() {
        return 3;
    }
}
