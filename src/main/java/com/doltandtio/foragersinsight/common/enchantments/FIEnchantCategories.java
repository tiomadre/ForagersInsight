package com.doltandtio.foragersinsight.common.enchantments;

import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.ForgeRegistries;

public class FIEnchantCategories {

    public static final EnchantmentCategory Harvest_Exclusive = EnchantmentCategory.create("hoe_shears_knife", item -> {
        // Hoes
        if (item.getDescriptionId().contains("hoe")) return true;
        // Shears
        if (item instanceof ShearsItem) return true;
        // Knives
        String registryName = ForgeRegistries.ITEMS.getKey(item).toString();
        return registryName.startsWith("farmersdelight:") && registryName.contains("_knife");
    });

}