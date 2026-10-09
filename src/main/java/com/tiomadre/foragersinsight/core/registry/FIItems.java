package com.tiomadre.foragersinsight.core.registry;

import com.teamabnormals.blueprint.common.item.BlueprintBoatItem;
import com.tiomadre.foragersinsight.common.item.*;
import com.tiomadre.foragersinsight.common.item.MilkBucketItem;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.other.FIFoods;
import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.registries.DeferredItem;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModMaterials;



import static vectorwing.farmersdelight.common.registry.ModItems.basicItem;


//@Mod.EventBusSubscriber(modid = ForagersInsight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FIItems {
    public static final ItemSubRegistryHelper HELPER = ForagersInsight.REGISTRY_HELPER.getItemSubHelper();

    //Acorn Dough
    public static final DeferredItem<Item> ACORN_DOUGH = HELPER.createItem("acorn_dough", () ->
            new Item(new Item.Properties().food(FIFoods.ACORN_DOUGH)));
    //Cuts & Knife Drops
    public static final DeferredItem<Item> APPLE_SLICE = HELPER.createItem("apple_slice", () ->
            new Item(new Item.Properties().food(FIFoods.MID_SAT_MORSELS)));
    public static final DeferredItem<Item> RAW_RABBIT_LEG = HELPER.createItem("raw_rabbit_leg", () -> new ConsumableItem(
            new Item.Properties().food((FIFoods.RAW_RABBIT_LEG))));
    public static final DeferredItem<Item> COOKED_RABBIT_LEG = HELPER.createItem("cooked_rabbit_leg", () -> new ConsumableItem(
            new Item.Properties().food((FIFoods.COOKED_RABBIT_LEG))));
    public static final DeferredItem<Item> ROSELLE_PETALS = HELPER.createItem("roselle_petals", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ROSE_PETALS = HELPER.createItem("rose_petals", () -> new Item(new Item.Properties()));

    //Crushed & Mallet Drops
    public static final DeferredItem<Item> CRUSHED_ICE = HELPER.createItem("crushed_ice", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> POPPY_SEED_PASTE = HELPER.createItem("poppy_seed_paste", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACORN_MEAL = HELPER.createItem("acorn_meal", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COCOA_POWDER = HELPER.createItem("cocoa_powder", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WHEAT_FLOUR = HELPER.createItem("wheat_flour", () -> new Item(new Item.Properties()));

    //Crop Drops
    public static final DeferredItem<Item> BLACK_ACORN = HELPER.createItem("black_acorn", () ->
            new Item(new Item.Properties().food(FIFoods.MID_SAT_MORSELS)));
    public static final DeferredItem<Item> DANDELION_ROOT = HELPER.createItem("dandelion_root", () ->
            new ItemNameBlockItem(FIBlocks.DANDELION_BUSH.get(), new Item.Properties().food(FIFoods.LOW_SAT_MORSELS)));
    public static final DeferredItem<Item> LILAC_BLOOM = HELPER.createItem("lilac_bloom", () ->
            new Item(new Item.Properties().food(FIFoods.NO_SAT_MORSELS)));
    public static final DeferredItem<Item> POPPY_SEEDS = HELPER.createItem("poppy_seeds", () ->
            new ItemNameBlockItem(FIBlocks.POPPY_BUSH.get(), new Item.Properties().food(FIFoods.NO_SAT_MORSELS)));
    public static final DeferredItem<Item> ROSELLE_CALYX = HELPER.createItem("roselle_calyx", () ->
            new ItemNameBlockItem(FIBlocks.ROSELLE_CROP.get(), new Item.Properties().food(FIFoods.LOW_SAT_MORSELS)));
    public static final DeferredItem<Item> ROSE_HIP = HELPER.createItem("rose_hip", () ->
            new ItemNameBlockItem(FIBlocks.ROSE_CROP.get(), new Item.Properties().food(FIFoods.NO_SAT_MORSELS)));
    public static final DeferredItem<Item> SPRUCE_TIPS = HELPER.createItem("spruce_tips", () ->
            new Item(new Item.Properties().food(FIFoods.LOW_SAT_MORSELS)));
    
        //Seed Only
    public static final DeferredItem<Item> TINDER_CONK_SPORES = HELPER.createItem("tinder_conk_spores", () ->
            new ItemNameBlockItem(FIBlocks.TINDER_CONK.get(), new Item.Properties()));
    //Ingredients
    public static final DeferredItem<Item> GREEN_SAUCE = HELPER.createItem("green_sauce", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SAUCE_BOWLS).craftRemainder(Items.BOWL)));
    public static final DeferredItem<Item> SEED_BUTTER = HELPER.createItem("seed_butter", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SAUCE_BOWLS).craftRemainder(Items.BOWL)));
    public static final DeferredItem<Item> BIRCH_SYRUP_BUCKET = HELPER.createItem("birch_syrup_bucket", () -> new Item(new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final DeferredItem<Item> BIRCH_SYRUP_BOTTLE = HELPER.createItem("birch_syrup_bottle",
            () -> new HoneyBottleItem(new Item.Properties().stacksTo(16).food(FIFoods.BIRCH_SYRUP_BOTTLE).craftRemainder(Items.GLASS_BOTTLE)));

    //DISHES and FOODSTUFF
        //Baked Goods & Sweets
    public static final DeferredItem<Item> ACORN_COOKIE = HELPER.createItem("acorn_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final DeferredItem<Item> ROSE_COOKIE = HELPER.createItem("rose_cookie", () -> new Item(new Item.Properties()
            .food(FoodValues.COOKIES)));
    public static final DeferredItem<Item> RED_VELVET_CUPCAKE = HELPER.createItem("red_velvet_cupcake", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD), true));
    public static final DeferredItem<Item> BLACK_FOREST_MUFFIN = HELPER.createItem("black_forest_muffin", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD), true));
    public static final DeferredItem<Item> POPPY_SEED_BAGEL = HELPER.createItem("poppy_seed_bagel", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD), true));
    public static final DeferredItem<Item> LILAC_TEACAKE = HELPER.createItem("lilac_teacake", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BAKED_GOOD), true));
    public static final DeferredItem<Item> CANDIED_CALYCES = HELPER.createItem("candied_calyces", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.CANDIED_CALYCES)));
    public static final DeferredItem<Item> APPLE_DIPPERS = HELPER.createItem("apple_dippers", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.APPLE_DIPPERS)));
        //Feasts and Servings
    public static final DeferredItem<Item> RAINBOW_SANDWICH_ITEM = HELPER.createItem("rainbow_sandwich", () ->
            new BlockItem(FIBlocks.RAINBOW_SANDWICH.get(), basicItem()));
    public static final DeferredItem<Item> SLICE_OF_RAINBOW_SANDWICH = HELPER.createItem("slice_of_rainbow_sandwich", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.RAINBOW_SANDWICH_SLICE), true));

        //Cakes and Slices
    public static final DeferredItem<Item> ACORN_CARROT_CAKE_ITEM = HELPER.createItem("acorn_carrot_cake", () ->
            new BlockItem(FIBlocks.ACORN_CARROT_CAKE.get(), basicItem()));
    public static final DeferredItem<Item> SLICE_OF_ACORN_CARROT_CAKE = HELPER.createItem("slice_of_acorn_carrot_cake", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.CAKE_SLICE), true));
        //Comfort
    public static final DeferredItem<Item> BLEWIT_BITES = HELPER.createItem("blewit_bites", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.BLEWIT_BITES), true));
    public static final DeferredItem<Item> CARROT_POPPY_CHOWDER = HELPER.createItem("carrot_poppy_chowder", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.CARROT_POPPY_CHOWDER).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> COD_AND_PUMPKIN_STEW = HELPER.createItem("cod_and_pumpkin_stew", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.COD_AND_PUMPKIN_STEW).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> CREAMY_SALMON_BAGEL = HELPER.createItem("creamy_salmon_bagel", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.CREAMY_SALMON_BAGEL), true));
    public static final DeferredItem<Item> FORAGERS_GRANOLA = HELPER.createItem("foragers_granola", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.FORAGERS_GRANOLA).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> JAMMY_BREAKFAST_SANDWICH = HELPER.createItem("jammy_breakfast_sandwich", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.JAMMY_BREAKFAST_SANDWICH), true));
    public static final DeferredItem<Item> HEARTY_SPRUCE_PILAF = HELPER.createItem("hearty_spruce_pilaf", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.HEARTY_SPRUCE_PILAF).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> STEAMY_KELP_RICE = HELPER.createItem("steamy_kelp_rice", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.STEAMY_KELP_RICE).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> ROSE_HIP_SOUP = HELPER.createItem("rose_hip_soup", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.ROSE_HIP_SOUP).craftRemainder(Items.BOWL), true));

        //Nourishment
    public static final DeferredItem<Item> ACORN_NOODLES = HELPER.createItem("acorn_noodles", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.ACORN_NOODLES).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> GLAZED_PORKCHOP_AND_ACORN_GRITS = HELPER.createItem("glazed_porkchop_and_acorn_grits", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.GLAZED_PORKCHOP_AND_ACORN_GRITS).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> ROSE_ROASTED_ROOTS = HELPER.createItem("rose_roasted_roots", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.ROSE_ROASTED_ROOTS).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> SEASIDE_SIZZLER = HELPER.createItem("seaside_sizzler", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SEASIDE_SIZZLER).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> SYRUP_TOAST_STACKS = HELPER.createItem("syrup_toast_stacks", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SYRUP_TOAST_STACKS).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> WOODLAND_PASTA = HELPER.createItem("woodland_pasta", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.WOODLAND_PASTA).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> TART_WHEAT_PILAF = HELPER.createItem("tart_wheat_pilaf", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.TART_WHEAT_PILAF).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> SAVORY_PASTA_ROLL = HELPER.createItem("savory_pasta_roll", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SAVORY_PASTA_ROLL).craftRemainder(Items.BOWL), true));
        //Salads
    public static final DeferredItem<Item> KELP_AND_BEET_SALAD = HELPER.createItem("kelp_and_beet_salad", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SALAD).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> MEADOW_MEDLEY = HELPER.createItem("meadow_medley", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SALAD).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> LILAC_SALAD = HELPER.createItem("lilac_salad", () -> new ConsumableItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SALAD).craftRemainder(Items.BOWL), true));

    //Sandwiches & Finger Foods
    public static final DeferredItem<Item> KELP_WRAP = HELPER.createItem("kelp_wrap", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.KELP_WRAP)));
    public static final DeferredItem<Item> DANDELION_FRIES = HELPER.createItem("dandelion_fries", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.DANDELION_FRIES).craftRemainder(Items.PAPER)));
    public static final DeferredItem<Item> SEED_BUTTER_JAMWICH = HELPER.createItem("seed_butter_jamwich", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SEED_BUTTER_JAMWICH)));
    public static final DeferredItem<Item> SWEET_ROASTED_RABBIT_LEG = HELPER.createItem("sweet_roasted_rabbit_leg", () -> new ConsumableItem(
            new Item.Properties().food(FIFoods.SWEET_ROASTED_RABBIT_LEG)));

    //Other
    public static final DeferredItem<Item> AUSPICIOUS_STEW = HELPER.createItem("auspicious_stew", () -> new AuspiciousStewItem(
            new Item.Properties().stacksTo(16).food(FIFoods.AUSPICIOUS_STEW).craftRemainder(Items.BOWL), true));
    public static final DeferredItem<Item> AMADOU = HELPER.createItem("amadou", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GHOST_PIPE_TORCH = HELPER.createItem("ghost_pipe_torch", () ->
            new StandingAndWallBlockItem(FIBlocks.GHOST_PIPE_TORCH.get(), FIBlocks.WALL_GHOST_PIPE_TORCH.get(), basicItem(), Direction.DOWN));


    //Drinks
        //Seed Milk
    public static final DeferredItem<Item> SEED_MILK_BOTTLE = HELPER.createItem("seed_milk_bottle", () -> new SeedMilkBottleItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SEED_MILK_BOTTLE).craftRemainder(Items.GLASS_BOTTLE), true));
    public static final DeferredItem<Item> SEED_MILK_BUCKET = HELPER.createItem("seed_milk_bucket", () -> new MilkBucketItem(
            new Item.Properties().stacksTo(16).food(FIFoods.SEED_MILK_BUCKET).craftRemainder(Items.BUCKET), true));

    //Tonics (Medicinal)
    public static final DeferredItem<Item> ROSE_CORDIAL = HELPER.createItem("rose_cordial", () -> new TonicItem(new Item.Properties()
            .food(FIFoods.ROSE_CORDIAL).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true));
    public static final DeferredItem<Item> GLOWING_CARROT_JUICE = HELPER.createItem("glowing_carrot_juice", () -> new TonicItem(new Item.Properties()
            .food(FIFoods.GLOWING_CARROT_JUICE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true));
    public static final DeferredItem<Item> DANDELION_ROOT_TEA = HELPER.createItem("dandelion_root_tea", () -> new TonicItem(new Item.Properties()
            .food(FIFoods.DANDELION_ROOT_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true));
    public static final DeferredItem<Item> FOREST_ELIXIR = HELPER.createItem("forest_elixir", () -> new TonicItem(new Item.Properties()
            .food(FIFoods.FOREST_ELIXIR).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true));
    public static final DeferredItem<Item> ROSELLE_JUICE = HELPER.createItem("roselle_juice", () -> new TonicItem(new Item.Properties()
            .food(FIFoods.ROSELLE_JUICE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), true));

    //Sap Stuff
    public static final DeferredItem<Item> BIRCH_SAP_BUCKET = HELPER.createItem("birch_sap_bucket", () ->
            new Item(new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    public static final DeferredItem<Item> BIRCH_SAP_BOTTLE = HELPER.createItem("birch_sap_bottle", () ->
            new Item(new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));


    //Wildflowers + Flora
    public static final DeferredItem<Item> ROSELLE_BUSH_ITEM = HELPER.createItem("roselle_bush", () ->
            new BlockItem(FIBlocks.ROSELLE_BUSH.get(),basicItem()));
    public static final DeferredItem<Item> STOUT_BEACH_ROSE_BUSH_ITEM = HELPER.createItem("stout_beach_rose_bush", () ->
            new BlockItem(FIBlocks.STOUT_BEACH_ROSE_BUSH.get(),basicItem()));
    public static final DeferredItem<Item> TALL_BEACH_ROSE_BUSH_ITEM = HELPER.createItem("tall_beach_rose_bush", () ->
            new BlockItem(FIBlocks.TALL_BEACH_ROSE_BUSH.get(),basicItem()));
    public static final DeferredItem<Item> WOODLAND_FERN_ITEM = HELPER.createItem("woodland_fern", () ->
            new BlockItem(FIBlocks.WOODLAND_FERN.get(), basicItem()));
    public static final DeferredItem<Item> GHOST_PIPE_ITEM = HELPER.createItem("ghost_pipe", () ->
            new BlockItem(FIBlocks.GHOST_PIPE.get(), basicItem()));
    public static final DeferredItem<Item> SKUNK_CABBAGE_ITEM = HELPER.createItem("skunk_cabbage", () ->
            new BlockItem(FIBlocks.SKUNK_CABBAGE.get(), basicItem()));
    //Fungi
    public static final DeferredItem<Item> BLEWIT_MUSHROOM = HELPER.createItem("blewit_mushroom",  () ->
            new ModerateFoodBlockItem(FIBlocks.BLEWIT_MUSHROOM.get(), new Item.Properties().food(FIFoods.BLEWIT)));
    public static final DeferredItem<Item> TINDER_CONK = HELPER.createItem("tinder_conk", () ->
            new BlockItem(FIBlocks.TINDER_CONK.get(), new Item.Properties()));

    //Wood Items
    public static final DeferredItem<Item> LILAC_SIGN = HELPER.createItem("lilac_sign", () ->
            new SignItem(new Item.Properties().stacksTo(16), FIBlocks.LILAC_SIGN.get(), FIBlocks.LILAC_WALL_SIGN.get()));
    public static final DeferredItem<Item> LILAC_HANGING_SIGN = HELPER.createItem("lilac_hanging_sign", () ->
            new HangingSignItem(FIBlocks.LILAC_HANGING_SIGN.get(), FIBlocks.LILAC_WALL_HANGING_SIGN.get(),
                    new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> LILAC_BOAT = HELPER.createItem("lilac_boat", () ->
            new BlueprintBoatItem(false, FIBoatTypes.LILAC, basicItem().stacksTo(1)));
    public static final DeferredItem<Item> LILAC_CHEST_BOAT = HELPER.createItem("lilac_chest_boat", () ->
            new BlueprintBoatItem(true, FIBoatTypes.LILAC, basicItem().stacksTo(1)));
// Tools & Armor
    //Armor
    public static final DeferredItem<Item> AMADOU_CAP = HELPER.createItem("amadou_cap", () ->
        new AmadouCapItem(new Item.Properties().stacksTo(1).component(DataComponents.DYED_COLOR,new DyedItemColor(DyedItemColor.LEATHER_COLOR,true))));
    //Diffuser
    public static final DeferredItem<Item> DIFFUSER = HELPER.createItem("diffuser", () ->
        new DiffuserBlockItem(FIBlocks.DIFFUSER.get(), basicItem().stacksTo(1)));
    //Handbasket
    public static final DeferredItem<Item> HANDBASKET = HELPER.createItem("handbasket", () ->
        new HandbasketItem(new Item.Properties().stacksTo(1)));
    //Mallets
    public static final DeferredItem<Item> FLINT_MALLET = HELPER.createItem("flint_mallet", () ->
            new MalletItem(ModMaterials.FLINT, 1, -3F, basicItem()));
    public static final DeferredItem<Item> IRON_MALLET = HELPER.createItem("iron_mallet", () ->
            new MalletItem(Tiers.IRON, 2, -3F, basicItem()));
    public static final DeferredItem<Item> GOLD_MALLET = HELPER.createItem("gold_mallet", () ->
            new MalletItem(Tiers.GOLD, 1, -3F,basicItem()));
    public static final DeferredItem<Item> DIAMOND_MALLET = HELPER.createItem("diamond_mallet", () ->
            new MalletItem(Tiers.DIAMOND, 2, -3F, basicItem()));
    public static final DeferredItem<Item> NETHERITE_MALLET = HELPER.createItem("netherite_mallet", () ->
            new MalletItem(Tiers.NETHERITE, 2, -2.9F, basicItem()));
    //Shears
    public static final DeferredItem<Item> FLINT_SHEARS = HELPER.createItem("flint_shears", () ->
            new FIShearsItem(new Item.Properties().durability(119)));
    //Tapper
    public static final DeferredItem<Item> TAPPER = HELPER.createItem("tapper", () ->
            new TapperItem(
            new Item.Properties().stacksTo(1)));
    //Other
    public static final DeferredItem<Item> STROP = HELPER.createItem("strop", () -> new StropItem(new Item.Properties().durability(64)));



}

