package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.doltandtio.naturesdelight.core.other.NDFoods;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NDItems {
    public static final ItemSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getItemSubHelper();

    public static final RegistryObject<Item> APPLE_SLICE = HELPER.createItem("apple_slice", () ->
            new Item(new Item.Properties().food(NDFoods.APPLE_SLICE)));

    public static final RegistryObject<Item> CRUSHED_ICE = HELPER.createItem("crushed_ice", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLACK_ACORN = HELPER.createItem("black_acorn", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ACORN_COOKIE = HELPER.createItem("acorn_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));



    public static final RegistryObject<Item> POPPY_SEEDS = HELPER.createItem("poppy_seeds", () ->
            new ItemNameBlockItem(NDBlocks.POPPY_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> DANDELION_ROOT = HELPER.createItem("dandelion_root", () ->
            new ItemNameBlockItem(NDBlocks.DANDELION_BUSH.get(), new Item.Properties()));

    public static final RegistryObject<Item> ROSE_PETALS = HELPER.createItem("rose_petals", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ROSE_COOKIE = HELPER.createItem("rose_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final RegistryObject<Item> ROSE_GRANITA = HELPER.createItem("rose_granita", () -> new DrinkableItem(new Item.Properties()
            .food(NDFoods.ROSE_GRANITA).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> ROSE_CORDIAL = HELPER.createItem("rose_cordial", () -> new DrinkableItem(new Item.Properties()
            .food(NDFoods.ROSE_CORDIAL).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> ROSE_ROASTED_ROOTS = HELPER.createItem("rose_roasted_roots", () -> new ConsumableItem(
            new Item.Properties().food(NDFoods.ROSE_ROASTED_ROOTS).craftRemainder(Items.BOWL)
    ));
}
