package com.tiomadre.foragersinsight.core.registry;



import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;



public final class FIEnchantments {

    public static final ResourceKey<Enchantment> FARMHAND =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("foragersinsight", "farmhand"));

    public static final ResourceKey<Enchantment> LUCK_OF_THE_TREES =
            ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("foragersinsight", "luck_of_the_trees"));
}