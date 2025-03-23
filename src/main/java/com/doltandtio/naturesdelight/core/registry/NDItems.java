package com.doltandtio.naturesdelight.core.registry;

import com.doltandtio.naturesdelight.common.item.MilkBucketItem;
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
import vectorwing.farmersdelight.common.item.MilkBottleItem;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mod.EventBusSubscriber(modid = NaturesDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NDItems {
    public static final ItemSubRegistryHelper HELPER = NaturesDelight.REGISTRY_HELPER.getItemSubHelper();
//Cuts & Knife Drops
    public static final RegistryObject<Item> APPLE_SLICE = HELPER.createItem("apple_slice", () ->
            new Item(new Item.Properties().food(NDFoods.APPLE_SLICE)));
    public static final RegistryObject<Item> RAW_RABBIT_LEG = HELPER.createItem("raw_rabbit_leg",() -> new ConsumableItem(
            new Item.Properties().food((NDFoods.RAW_RABBIT_LEG))));
    public static final RegistryObject<Item> COOKED_RABBIT_LEG = HELPER.createItem("cooked_rabbit_leg",() -> new ConsumableItem(
            new Item.Properties().food((NDFoods.COOKED_RABBIT_LEG))));
    public static final RegistryObject<Item> ROSE_PETALS = HELPER.createItem("rose_petals", () -> new Item(new Item.Properties()));

//Crushed
    public static final RegistryObject<Item> CRUSHED_ICE = HELPER.createItem("crushed_ice", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POPPY_SEED_PASTE = HELPER.createItem("poppy_seed_paste", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ACORN_MEAL = HELPER.createItem("acorn_meal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_POWDER = HELPER.createItem("cocoa_powder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHEAT_FLOUR = HELPER.createItem("wheat_flour",() -> new Item(new Item.Properties()));

//Crops
    public static final RegistryObject<Item> BLACK_ACORN = HELPER.createItem("black_acorn", () ->
        new Item(new Item.Properties().food(NDFoods.BLACK_ACORN)));
    public static final RegistryObject<Item> DANDELION_ROOT = HELPER.createItem("dandelion_root", () ->
            new ItemNameBlockItem(NDBlocks.DANDELION_BUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> SUNFLOWER_KERNELS = HELPER.createItem("sunflower_kernels", () ->
            new Item(new Item.Properties().food(NDFoods.SUNFLOWER_KERNELS)));

                //This is for 1 Hunger no Saturation Crops: Poppy Seeds/Rose Hips/Spruce Tips
    public static final RegistryObject<Item> POPPY_SEEDS = HELPER.createItem("poppy_seeds", () ->
            new ItemNameBlockItem(NDBlocks.POPPY_BUSH.get(), new Item.Properties().food(NDFoods.NO_SATURATION_PSRHST)));
    public static final RegistryObject<Item> ROSE_HIP = HELPER.createItem("rose_hip", () ->
            new ItemNameBlockItem(NDBlocks.ROSE_HIP.get(), new Item.Properties().food(NDFoods.NO_SATURATION_PSRHST)));
    public static final RegistryObject<Item> SPRUCE_TIPS = HELPER.createItem("spruce_tips", () -> new Item(new Item.Properties()));

//Cookies and Sweets
    public static final RegistryObject<Item> ACORN_COOKIE = HELPER.createItem("acorn_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final RegistryObject<Item> ROSE_COOKIE = HELPER.createItem("rose_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final RegistryObject<Item> RED_VELVET_CUPCAKE = HELPER.createItem("red_velvet_cupcake",() -> new ConsumableItem(
            new Item.Properties().food(NDFoods.RED_VELVET_CUPCAKE)));
//DISHES
    //Chilled
    public static final RegistryObject<Item> ROSE_GRANITA = HELPER.createItem("rose_granita", () -> new DrinkableItem(new Item.Properties()
            .food(NDFoods.ROSE_GRANITA).craftRemainder(Items.GLASS_BOTTLE)));
    //Comfort
    public static final RegistryObject<Item> COD_AND_PUMPKIN_STEW = HELPER.createItem("cod_and_pumpkin_stew", () -> new ConsumableItem(
        new Item.Properties().food(NDFoods.COD_AND_PUMPKIN_STEW).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> STEAMY_KELP_RICE = HELPER.createItem("steamy_kelp_rice", () -> new ConsumableItem(
            new Item.Properties().food(NDFoods.STEAMY_KELP_RICE).craftRemainder(Items.BOWL)));
    //Medicinal

    //Nourishment
    public static final RegistryObject<Item> ROSE_ROASTED_ROOTS = HELPER.createItem("rose_roasted_roots", () -> new ConsumableItem(
            new Item.Properties().food(NDFoods.ROSE_ROASTED_ROOTS).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> SEASIDE_SIZZLER = HELPER.createItem("seaside_sizzler", () -> new ConsumableItem(
            new Item.Properties().food(NDFoods.SEASIDE_SIZZLER).craftRemainder(Items.BOWL)));
    //Salads
    public static final RegistryObject<Item> KELP_AND_BEET_SALAD = HELPER.createItem("kelp_and_beet_salad",() -> new ConsumableItem(
            new Item.Properties().food(NDFoods.KELP_AND_BEET_SALAD).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> MEADOW_MEDLEY = HELPER.createItem("meadow_medley",() -> new ConsumableItem(
            new Item.Properties().food(NDFoods.MEADOW_MEDLEY).craftRemainder(Items.BOWL)));
    //Sandwiches + Finger Foods
    public static final RegistryObject<Item> KELP_WRAP = HELPER.createItem("kelp_wrap",() -> new ConsumableItem(
            new Item.Properties().food(NDFoods.KELP_WRAP).craftRemainder(Items.BOWL)));

//Drinks
    public static final RegistryObject<Item> ROSE_CORDIAL = HELPER.createItem("rose_cordial", () -> new DrinkableItem(new Item.Properties()
            .food(NDFoods.ROSE_CORDIAL).craftRemainder(Items.GLASS_BOTTLE)));
    //Chilled Drinks

// Seed Milk Stuff
    public static final RegistryObject<Item> SEED_MILK_BOTTLE = HELPER.createItem("seed_milk_bottle", () -> new MilkBottleItem(
            new Item.Properties().food(NDFoods.SEED_MILK_BOTTLE)));
    public static final RegistryObject<Item> SEED_MILK_BUCKET = HELPER.createItem("seed_milk_bucket", () -> new MilkBucketItem(
            new Item.Properties().food(NDFoods.SEED_MILK_BUCKET)));
}
