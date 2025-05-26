package com.doltandtio.foragersinsight.core.registry;

import com.doltandtio.foragersinsight.common.item.*;
import com.doltandtio.foragersinsight.common.item.MilkBucketItem;
import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.other.FIFoods;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;
import vectorwing.farmersdelight.common.registry.ModMaterials;

import static vectorwing.farmersdelight.common.registry.ModItems.basicItem;


@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIItems {
    public static final ItemSubRegistryHelper HELPER = ForagersInsight.REGISTRY_HELPER.getItemSubHelper();

    //Acorn Dough
    public static final RegistryObject<Item> ACORN_DOUGH = HELPER.createItem("acorn_dough", () ->
            new Item(new Item.Properties().food(FIFoods.ACORN_DOUGH)));
    //Cuts & Knife Drops
    public static final RegistryObject<Item> APPLE_SLICE = HELPER.createItem("apple_slice", () ->
            new Item(new Item.Properties().food(FIFoods.MID_SAT_MORSELS)));
    public static final RegistryObject<Item> RAW_RABBIT_LEG = HELPER.createItem("raw_rabbit_leg", () -> new ConsumableItem(
            new Item.Properties().food((FIFoods.RAW_RABBIT_LEG))));
    public static final RegistryObject<Item> COOKED_RABBIT_LEG = HELPER.createItem("cooked_rabbit_leg", () -> new ConsumableItem(
            new Item.Properties().food((FIFoods.COOKED_RABBIT_LEG))));
    public static final RegistryObject<Item> ROSE_PETALS = HELPER.createItem("rose_petals", () -> new Item(new Item.Properties()));

    //Crushed
    public static final RegistryObject<Item> CRUSHED_ICE = HELPER.createItem("crushed_ice", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POPPY_SEED_PASTE = HELPER.createItem("poppy_seed_paste", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ACORN_MEAL = HELPER.createItem("acorn_meal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COCOA_POWDER = HELPER.createItem("cocoa_powder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHEAT_FLOUR = HELPER.createItem("wheat_flour", () -> new Item(new Item.Properties()));

    //Crops
    public static final RegistryObject<Item> BLACK_ACORN = HELPER.createItem("black_acorn", () ->
            new Item(new Item.Properties().food(FIFoods.MID_SAT_MORSELS)));

    public static final RegistryObject<Item> DANDELION_ROOT = HELPER.createItem("dandelion_root", () ->
            new ItemNameBlockItem(FIBlocks.DANDELION_BUSH.get(), new Item.Properties().food(FIFoods.LOW_SAT_MORSELS)));
    public static final RegistryObject<Item> SUNFLOWER_KERNELS = HELPER.createItem("sunflower_kernels", () ->
            new ItemNameBlockItem(FIBlocks.SUNFLOWER_CROP.get(), new Item.Properties().food(FIFoods.LOW_SAT_MORSELS)));

    public static final RegistryObject<Item> POPPY_SEEDS = HELPER.createItem("poppy_seeds", () ->
            new ItemNameBlockItem(FIBlocks.POPPY_BUSH.get(), new Item.Properties().food(FIFoods.NO_SAT_MORSELS)));
    public static final RegistryObject<Item> ROSE_HIP = HELPER.createItem("rose_hip", () ->
            new ItemNameBlockItem(FIBlocks.ROSE_CROP.get(), new Item.Properties().food(FIFoods.NO_SAT_MORSELS)));
    public static final RegistryObject<Item> SPRUCE_TIPS = HELPER.createItem("spruce_tips", () ->
            new Item(new Item.Properties().food(FIFoods.LOW_SAT_MORSELS)));
    //Ingredients
    public static final RegistryObject<Item> GREEN_SAUCE = HELPER.createItem("green_sauce", () -> new Item(new Item.Properties()
            .food(FIFoods.SAUCE_BOWLS)));
    public static final RegistryObject<Item> SUNFLOWER_BUTTER = HELPER.createItem("sunflower_butter", () -> new Item(new Item.Properties()
            .food(FIFoods.SAUCE_BOWLS)));
    //DISHES
        //Baked Goods & Sweets
    public static final RegistryObject<Item> ACORN_COOKIE = HELPER.createItem("acorn_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final RegistryObject<Item> ROSE_COOKIE = HELPER.createItem("rose_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final RegistryObject<Item> RED_VELVET_CUPCAKE = HELPER.createItem("red_velvet_cupcake", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD)));
    public static final RegistryObject<Item> BLACK_FOREST_MUFFIN = HELPER.createItem("black_forest_muffin", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD)));
    public static final RegistryObject<Item> POPPY_SEED_BAGEL = HELPER.createItem("poppy_seed_bagel", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD)));
        //Cakes and Slices
        public static final RegistryObject<Item> ACORN_CARROT_CAKE_ITEM = HELPER.createItem("acorn_carrot_cake",
                () -> new BlockItem(FIBlocks.ACORN_CARROT_CAKE.get(), basicItem()));
        public static final RegistryObject<Item> SLICE_OF_ACORN_CARROT_CAKE = HELPER.createItem("slice_of_acorn_carrot_cake", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.CAKE_SLICE)));
        //Chilled
    public static final RegistryObject<Item> ROSE_GRANITA = HELPER.createItem("rose_granita", () -> new DrinkableItem(new Item.Properties()
            .food(FIFoods.ROSE_GRANITA).craftRemainder(Items.GLASS_BOTTLE)));
        //Comfort
    public static final RegistryObject<Item> CARROT_POPPY_CHOWDER = HELPER.createItem("carrot_poppy_chowder", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.CARROT_POPPY_CHOWDER).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> COD_AND_PUMPKIN_STEW = HELPER.createItem("cod_and_pumpkin_stew", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.COD_AND_PUMPKIN_STEW).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> CREAMY_SALMON_BAGEL = HELPER.createItem("creamy_salmon_bagel", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.CREAMY_SALMON_BAGEL)));
    public static final RegistryObject<Item> FORAGERS_GRANOLA = HELPER.createItem("foragers_granola", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.FORAGERS_GRANOLA).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> JAMMY_BREAKFAST_SANDWICH = HELPER.createItem("jammy_breakfast_sandwich", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.JAMMY_BREAKFAST_SANDWICH)));
    public static final RegistryObject<Item> STEAMY_KELP_RICE = HELPER.createItem("steamy_kelp_rice", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.STEAMY_KELP_RICE).craftRemainder(Items.BOWL)));
        //Medicinal

        //Nourishment
    public static final RegistryObject<Item> ACORN_NOODLES = HELPER.createItem("acorn_noodles", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.ACORN_NOODLES).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> ROSE_ROASTED_ROOTS = HELPER.createItem("rose_roasted_roots", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.ROSE_ROASTED_ROOTS).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> SEASIDE_SIZZLER = HELPER.createItem("seaside_sizzler", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SEASIDE_SIZZLER).craftRemainder(Items.BOWL)));
    //Salads
    public static final RegistryObject<Item> KELP_AND_BEET_SALAD = HELPER.createItem("kelp_and_beet_salad", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SALAD).craftRemainder(Items.BOWL)));
    public static final RegistryObject<Item> MEADOW_MEDLEY = HELPER.createItem("meadow_medley", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SALAD).craftRemainder(Items.BOWL)));
    //Sandwiches & Finger Foods
    public static final RegistryObject<Item> KELP_WRAP = HELPER.createItem("kelp_wrap", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.KELP_WRAP)));
    public static final RegistryObject<Item> DANDELION_FRIES = HELPER.createItem("dandelion_fries", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.DANDELION_FRIES)));
    public static final RegistryObject<Item> SEED_BUTTER_JAMWICH = HELPER.createItem("seed_butter_jamwich", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SEED_BUTTER_JAMWICH)));
    public static final RegistryObject<Item> SWEET_ROASTED_RABBIT_LEG = HELPER.createItem("sweet_roasted_rabbit_leg", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SWEET_ROASTED_RABBIT_LEG)));
    //Drinks
    public static final RegistryObject<Item> ROSE_CORDIAL = HELPER.createItem("rose_cordial", () -> new DrinkableItem(new Item.Properties()
            .food(FIFoods.ROSE_CORDIAL).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> GLOWING_CARROT_JUICE = HELPER.createItem("glowing_carrot_juice", () -> new GlowJuiceItem(new Item.Properties()
            .food(FIFoods.GLOWING_CARROT_JUICE).craftRemainder(Items.GLASS_BOTTLE)));
        //Chilled

        //Medicinal
    public static final RegistryObject<Item> DANDELION_ROOT_TEA = HELPER.createItem("dandelion_root_tea", () -> new DrinkableItem(new Item.Properties()
            .food(FIFoods.DANDELION_ROOT_TEA).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> FOREST_ELIXIR = HELPER.createItem("forest_elixir", () -> new DrinkableItem(new Item.Properties()
            .food(FIFoods.FOREST_ELIXIR).craftRemainder(Items.GLASS_BOTTLE)));

    //Seed Milk Stuff
    public static final RegistryObject<Item> SEED_MILK_BOTTLE = HELPER.createItem("seed_milk_bottle", () -> new MilkBottleItem(
            new Item.Properties().food(FIFoods.SEED_MILK_BOTTLE)));
    public static final RegistryObject<Item> SEED_MILK_BUCKET = HELPER.createItem("seed_milk_bucket", () -> new MilkBucketItem(
            new Item.Properties().food(FIFoods.SEED_MILK_BUCKET)));
//Workstations & Tools
    //Chilled Pot
    //Handbasket
    public static final RegistryObject<Item> HANDBASKET = HELPER.createItem("handbasket", () ->
        new HandbasketItem(new Item.Properties().stacksTo(1)));
    //Mallets
    public static final RegistryObject<Item> FLINT_MALLET = HELPER.createItem("flint_mallet", () ->
            new MalletItem(ModMaterials.FLINT, 1, -3F, basicItem()));
    public static final RegistryObject<Item> IRON_MALLET = HELPER.createItem("iron_mallet", () ->
            new MalletItem(Tiers.IRON, 2, -3F, basicItem()));
    public static final RegistryObject<Item> GOLD_MALLET = HELPER.createItem("gold_mallet", () ->
            new MalletItem(Tiers.GOLD, 1, -3F,basicItem()));
    public static final RegistryObject<Item> DIAMOND_MALLET = HELPER.createItem("diamond_mallet", () ->
            new MalletItem(Tiers.DIAMOND, 2, -3F, basicItem()));
    public static final RegistryObject<Item> NETHERITE_MALLET = HELPER.createItem("netherite_mallet", () ->
            new MalletItem(Tiers.NETHERITE, 2, -2.9F, basicItem()));
    //Shears
    public static final RegistryObject<Item> FLINT_SHEARS = HELPER.createItem("flint_shears", () ->
            new FIShearsItem(new Item.Properties().durability(119)));

}