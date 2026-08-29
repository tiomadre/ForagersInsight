package com.tiomadre.foragersinsight.data.server.recipes;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import com.tiomadre.foragersinsight.core.registry.FIRecipeSerializers;
import com.tiomadre.foragersinsight.data.server.tags.FITags;
import static com.tiomadre.foragersinsight.core.registry.FIBlocks.*;
import static com.tiomadre.foragersinsight.core.registry.FIItems.*;
import com.teamabnormals.blueprint.core.data.server.BlueprintRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


import static net.minecraft.world.item.Items.*;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.ModTags;


import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FICraftingRecipes extends BlueprintRecipeProvider {
    public FICraftingRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(ForagersInsight.MOD_ID, output,provider);
    }
    //ITEMS
    //Cookies
    @Override
    public void buildRecipes(RecipeOutput output) {
        addVanillaOverrides(output);
        addFarmersDelightOverrides(output);


//        SpecialRecipeBuilder.special(FIRecipeSerializers.WAXED_BOOTS.get())
//                .save(output, ForagersInsight.rl("crafting_special_waxedboots").toString());

        cookie(ROSE_COOKIE, FIItems.ROSE_HIP, output);
        cookie(ACORN_COOKIE, BLACK_ACORN, output);

        //Dough
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_DOUGH.get(), 3)
                .requires(FITags.ItemTag.ACORN)
                .requires(FITags.ItemTag.ACORN)
                .requires(FITags.ItemTag.ACORN)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(Tags.Items.EGGS),
                        new Ingredient.ItemValue(new net.minecraft.world.item.ItemStack(Items.WATER_BUCKET))
                )))
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get()))
                .save(output);
        //DISHES
        //Comfort
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FORAGERS_GRANOLA.get())
                .requires(FIItems.ROSE_HIP.get()).requires(FITags.ItemTag.APPLE).requires(FIItems.ROSE_HIP.get())
                .requires(FITags.ItemTag.ACORN).requires(BOWL)
                .unlockedBy("has_rose_hip", has(FIItems.ROSE_HIP.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, CREAMY_SALMON_BAGEL.get(), 2)
                .requires(FITags.ItemTag.MILK).requires(POPPY_SEED_BAGEL.get()).requires(CommonTags.Items.FOODS_COOKED_SALMON)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, JAMMY_BREAKFAST_SANDWICH.get())
                .requires(SWEET_BERRIES).requires(POPPY_SEED_BAGEL.get()).requires(ModItems.BACON.get())
                .requires(ModItems.FRIED_EGG.get()).requires(SWEET_BERRIES)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, RABBIT_STEW)
                .requires(BAKED_POTATO).requires(COOKED_RABBIT_LEG.get()).requires(FITags.ItemTag.ROOTS)
                .requires(FITags.ItemTag.MUSHROOM).requires(BOWL)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "stew_from_rabbit_leg"));
        //Nourishment

        //Salads
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_AND_BEET_SALAD.get())
                .requires(KELP).requires(KELP).requires(BEETROOT).requires(BEETROOT).requires(BOWL)
                .unlockedBy("has_kelp", has(KELP)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, MEADOW_MEDLEY.get())
                .requires(FITags.ItemTag.APPLE).requires(FITags.ItemTag.POPPY_SEEDS).requires(FITags.ItemTag.POPPY_SEEDS)
                .requires(DANDELION).requires(DANDELION)
                .requires(BOWL)
                .unlockedBy("has_poppy_seed", has(POPPY_SEEDS.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, LILAC_SALAD.get())
                .requires(LILAC_BLOOM.get()).requires(LILAC_BLOOM.get())
                .requires(LILAC_BLOOM.get()).requires(LILAC_BLOOM.get())
                .requires(BOWL)
                .unlockedBy("has_lilac_bloom", has(LILAC_BLOOM.get())).save(output);
        //Sandwiches + Finger Foods
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, KELP_WRAP.get())
                .requires(KELP).requires(INK_SAC).requires(CommonTags.Items     .CROPS_TOMATO)
                .requires(CommonTags.Items.CROPS_ONION).requires(Ingredient.of(COOKED_COD,ModItems.COOKED_COD_SLICE.get(),BROWN_MUSHROOM))
                .requires(KELP)
                .unlockedBy("has_kelp", has(KELP)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_BUTTER_JAMWICH.get())
                .requires(BREAD).requires(SEED_BUTTER.get()).requires(SWEET_BERRIES)
                .requires(SWEET_BERRIES)
                .unlockedBy("has_sweet_berries", has(SWEET_BERRIES)).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SWEET_ROASTED_RABBIT_LEG.get(), 2)
                .requires(COOKED_RABBIT_LEG.get()).requires(COOKED_RABBIT_LEG.get()).requires(FITags.ItemTag.POPPY_SEEDS)
                .requires(FITags.ItemTag.POPPY_SEEDS) .requires(Ingredient.of(HONEY_BOTTLE, BIRCH_SYRUP_BOTTLE.get()))
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get())).save(output);

        //Seed Milk
            //Bucket of Seed Milk -> Bottles
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_MILK_BOTTLE.get(),4)
                .requires(SEED_MILK_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_seed_milk_bucket", has(SEED_MILK_BUCKET.get())).save(output);
            //Bottles of Seed Milk -> Bucket
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SEED_MILK_BUCKET.get())
                .requires(BUCKET).requires(SEED_MILK_BOTTLE.get()).requires(SEED_MILK_BOTTLE.get())
                .requires(SEED_MILK_BOTTLE.get()).requires(SEED_MILK_BOTTLE.get())
                .unlockedBy("has_seed_milk_bottle", has(SEED_MILK_BOTTLE.get())).save(output);
        //Sap and Syrup
            // Bucket of Syrup -> Bottles
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BIRCH_SYRUP_BOTTLE.get(),4)
                .requires(BIRCH_SYRUP_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_birch_syrup_bucket", has(BIRCH_SYRUP_BUCKET.get()))
                .save(output, ForagersInsight.rl("birch_syrup_bottle_from_bucket"));
            //Bottles of Syrup -> Bucket
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, BIRCH_SYRUP_BUCKET.get())
                .requires(BUCKET).requires(BIRCH_SYRUP_BOTTLE.get()).requires(BIRCH_SYRUP_BOTTLE.get())
                .requires(BIRCH_SYRUP_BOTTLE.get()).requires(BIRCH_SYRUP_BOTTLE.get())
                .unlockedBy("has_birch_syrup_bottle", has(BIRCH_SYRUP_BOTTLE.get()))
                .save(output, ForagersInsight.rl("birch_syrup_bucket_from_bottles"));
            //Bucket of Sap -> Bottles
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BIRCH_SAP_BOTTLE.get(),4)
                .requires(BIRCH_SAP_BUCKET.get()).requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .requires(GLASS_BOTTLE).requires(GLASS_BOTTLE)
                .unlockedBy("has_birch_sap_bucket", has(BIRCH_SAP_BUCKET.get()))
                .save(output, ForagersInsight.rl("birch_sap_bottle_from_bucket"));
            //Bottles of Sap -> Bucket
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BIRCH_SAP_BUCKET.get())
                .requires(BUCKET).requires(BIRCH_SAP_BOTTLE.get()).requires(BIRCH_SAP_BOTTLE.get())
                .requires(BIRCH_SAP_BOTTLE.get()).requires(BIRCH_SAP_BOTTLE.get())
                .unlockedBy("has_birch_sap_bottle", has(BIRCH_SAP_BOTTLE.get()))
                .save(output, ForagersInsight.rl("birch_sap_bucket_from_bottles"));
            //Syrup to Sugar
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, SUGAR,3)
                .requires(BIRCH_SYRUP_BOTTLE.get())
                .unlockedBy("has_birch_syrup_bottle", has(BIRCH_SYRUP_BOTTLE.get()))
                .save(output, ForagersInsight.rl("sugar_from_birch_syrup_bottle"));

            //Furnace Cooking
                //Amadou
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AMADOU.get()), RecipeCategory.MISC, CHARCOAL, 0.25F, 200)
                .unlockedBy("has_amadou", has(AMADOU.get()))
                .save(output, ForagersInsight.rl("charcoal_from_smelting_amadou"));

                //Syrup Bucket
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BIRCH_SAP_BUCKET.get()), RecipeCategory.FOOD, BIRCH_SYRUP_BUCKET.get(), 1.0F, 200)
                .unlockedBy("has_birch_sap_bucket", has(BIRCH_SAP_BUCKET.get()))
                .save(output, ForagersInsight.rl("birch_syrup_bucket_from_smelting"));
                //Syrup Bottle
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BIRCH_SAP_BOTTLE.get()), RecipeCategory.FOOD, BIRCH_SYRUP_BOTTLE.get(), 1.0F, 200)
                .unlockedBy("has_birch_sap_bucket", has(BIRCH_SAP_BUCKET.get()))
                .save(output, ForagersInsight.rl("birch_syrup_bottle_from_smelting"));
                //Rabbit Leg
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(RAW_RABBIT_LEG.get()), RecipeCategory.FOOD, COOKED_RABBIT_LEG.get(), 0.35F, 200)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get()))
                .save(output, ForagersInsight.rl("cooked_rabbit_leg_from_smelting"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(RAW_RABBIT_LEG.get()), RecipeCategory.FOOD, COOKED_RABBIT_LEG.get(), 0.35F, 100)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get()))
                .save(output, ForagersInsight.rl("cooked_rabbit_leg_from_smoking"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(RAW_RABBIT_LEG.get()), RecipeCategory.FOOD, COOKED_RABBIT_LEG.get(), 0.35F, 600)
                .unlockedBy("has_raw_rabbit_leg", has(RAW_RABBIT_LEG.get()))
                .save(output, ForagersInsight.rl("cooked_rabbit_leg_from_campfire_cooking"));

        //Tools and Armor
        //Flint Shears
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, FLINT_SHEARS.get())
                .requires(FLINT).requires(FLINT)
                .unlockedBy("has_flint", has(FLINT))
                .save(output);
        //Mallets
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, FLINT_MALLET.get())
                .pattern("fff")
                .pattern(" s ")
                .define('f', FLINT)
                .define('s', STICK)
                .unlockedBy("has_flint", has(FLINT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, IRON_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', IRON_INGOT)
                .define('s', STICK)
                .unlockedBy("has_iron", has(IRON_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, GOLD_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', GOLD_INGOT)
                .define('s', STICK)
                .unlockedBy("has_gold", has(GOLD_INGOT))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, DIAMOND_MALLET.get())
                .pattern("iii")
                .pattern(" s ")
                .define('i', DIAMOND)
                .define('s', STICK)
                .unlockedBy("has_diamond", has(DIAMOND))
                .save(output);
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(DIAMOND_MALLET.get()),
                        Ingredient.of(NETHERITE_INGOT),
                        RecipeCategory.TOOLS, NETHERITE_MALLET.get())
                .unlocks("has_diamond_mallet", has(DIAMOND_MALLET.get()))
                .unlocks("has_netherite_ingot", has(NETHERITE_INGOT))
                .unlocks("has_smiting_template", has(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
                .save(output, ForagersInsight.rl("netherite_mallet_smithing"));

        //Diffuser
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FIItems.DIFFUSER.get())
                .pattern("BCB")
                .pattern("CGC")
                .pattern("CFC")
                .define('B', HONEYCOMB)
                .define('C', Items.COPPER_INGOT)
                .define('G', Items.GLASS_BOTTLE)
                .define('F', Items.FLINT_AND_STEEL)
                .unlockedBy("has_copper_ingot", has(COPPER_INGOT))
                .save(output, ForagersInsight.rl("diffuser"));

        //Handbasket
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, HANDBASKET.get())
                .pattern(" ) ")
                .pattern(": :")
                .pattern("):)")
                .define(':', ModItems.STRAW.get())
                .define(')', STICK)
                .unlockedBy("has_straw", has(ModItems.STRAW.get()))
                .save(output);
        //Sap Trap
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, SAP_TRAP.get(), 3)
                .pattern("PSP")
                .pattern("BBB")
                .define('P', PAPER)
                .define('S', BIRCH_SAP_BOTTLE.get())
                .define('B', ModItems.TREE_BARK.get())
                .unlockedBy("has_birch_sap_bottle", has(BIRCH_SAP_BOTTLE.get()))
                .save(output);

        //Strop
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, STROP.get())
                .pattern(" LI")
                .pattern(" L ")
                .pattern("IL ")
                .define('L', LEATHER)
                .define('I', IRON_INGOT)
                .unlockedBy("has_leather", has(LEATHER))
                .save(output);
        //Tapper
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, FIItems.TAPPER.get())
                .requires(ModItems.FLINT_KNIFE.get())
                .requires(BUCKET)
                .unlockedBy("has_flint_knife", has(ModItems.FLINT_KNIFE.get()))
                .unlockedBy("has_bucket", has(BUCKET))
                .save(output);
        //Armor
            //Amadou Hat
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMADOU_CAP.get())
                .pattern("SAS")
                .pattern("ABA")
                .define('A', AMADOU.get())
                .define('S', STRING)
                .define('B', BIRCH_SAP_BOTTLE.get())
                .unlockedBy("has_amadou", has(AMADOU.get()))
                .save(output);

        //BLOCKS
        //Decorative
            //Foliage Mats
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_LILAC_BLOOM_MAT.get())
                .requires(FIItems.LILAC_BLOOM.get(), 4)
                .unlockedBy("has_lilac_bloom", has(FIItems.LILAC_BLOOM.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_ROSE_PETAL_MAT.get())
                .requires(FIItems.ROSE_PETALS.get(), 4)
                .unlockedBy("has_rose_petals", has(FIItems.ROSE_PETALS.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_ROSELLE_PETAL_MAT.get())
                .requires(FIItems.ROSELLE_PETALS.get(), 4)
                .unlockedBy("has_roselle_petals", has(FIItems.ROSELLE_PETALS.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_SPRUCE_TIP_MAT.get())
                .requires(FIItems.SPRUCE_TIPS.get(), 4)
                .unlockedBy("has_spruce_tips", has(FIItems.SPRUCE_TIPS.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, SCATTERED_STRAW_MAT.get())
                .requires(ModItems.STRAW.get(), 4)
                .unlockedBy("has_straw", has(ModItems.STRAW.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_LILAC_BLOOM_MAT.get())
                .requires(FIBlocks.SCATTERED_LILAC_BLOOM_MAT.get(), 2)
                .unlockedBy("has_scattered_lilac_blooms", has(FIBlocks.SCATTERED_LILAC_BLOOM_MAT.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_STRAW_MAT.get())
                .requires(FIBlocks.SCATTERED_STRAW_MAT.get(), 2)
                .unlockedBy("has_scattered_straw", has(SCATTERED_STRAW_MAT.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_ROSE_PETAL_MAT.get())
                .requires(FIBlocks.SCATTERED_ROSE_PETAL_MAT.get(), 2)
                .unlockedBy("has_scattered_rose_petals", has(FIBlocks.SCATTERED_ROSE_PETAL_MAT.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_ROSELLE_PETAL_MAT.get())
                .requires(FIBlocks.SCATTERED_ROSELLE_PETAL_MAT.get(), 2)
                .unlockedBy("has_scattered_roselle_petals", has(FIBlocks.SCATTERED_ROSELLE_PETAL_MAT.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, DENSE_SPRUCE_TIP_MAT.get())
                .requires(FIBlocks.SCATTERED_SPRUCE_TIP_MAT.get(), 2)
                .unlockedBy("has_scattered_spruce_tips", has(FIBlocks.SCATTERED_SPRUCE_TIP_MAT.get()))
                .save(output);
        //Feasts and Cakes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ACORN_CARROT_CAKE_ITEM.get())
                .requires(ACORN_DOUGH.get()).requires(CARROT).requires(CARROT)
                .requires(SUGAR).requires(Tags.Items.EGGS).requires(SUGAR)
                .requires(FITags.ItemTag.MILK).requires(FITags.ItemTag.MILK).requires(FITags.ItemTag.MILK)
                .unlockedBy("has_black_acorn", has(BLACK_ACORN.get())).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, RAINBOW_SANDWICH_ITEM.get())
                .pattern("BTC")
                .pattern("KVK")
                .pattern("CTB")
                .define('B', BREAD)
                .define('T', Ingredient.of(ModItems.TOMATO.get(), BEETROOT))
                .define('C', Ingredient.of(CARROT, DANDELION_ROOT.get(), POTATO))
                .define('K', Ingredient.of(ModItems.CABBAGE_LEAF.get(), KELP))
                .define('V', FITags.ItemTag.VEGETABLES)
                .unlockedBy("has_bread", has(BREAD))
                .save(output);
        //OTHER
        //Dyes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RED_DYE, 1)
                .requires(FIItems.ROSE_PETALS.get())
                .unlockedBy("has_rose_petals", has(FIItems.ROSE_PETALS.get()))
                .save(output, ForagersInsight.rl("red_dye_from_rose_petals"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PINK_DYE, 1)
                .requires(FIItems.ROSELLE_PETALS.get())
                .unlockedBy("has_roseLle_petals", has(FIItems.ROSELLE_PETALS.get()))
                .save(output, ForagersInsight.rl("pink_dye_from_roselle_petals"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PINK_DYE, 2)
                .requires(FIItems.ROSELLE_BUSH_ITEM.get())
                .unlockedBy("has_roselle_bush", has(FIItems.ROSELLE_BUSH_ITEM.get()))
                .save(output, ForagersInsight.rl("pink_dye_from_roselle_bush"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RED_DYE, 1)
                .requires(STOUT_BEACH_ROSE_BUSH_ITEM.get())
                .unlockedBy("has_roselle_bush", has(STOUT_BEACH_ROSE_BUSH_ITEM.get()))
                .save(output, ForagersInsight.rl("red_dye_from_stout_beach_rose_bush"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RED_DYE, 2)
                .requires(TALL_BEACH_ROSE_BUSH_ITEM.get())
                .unlockedBy("has_roselle_bush", has(TALL_BEACH_ROSE_BUSH_ITEM.get()))
                .save(output, ForagersInsight.rl("red_dye_from_tall_beach_rose_bush"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BLUE_DYE, 1)
                .requires(FIItems.BLEWIT_MUSHROOM.get())
                .unlockedBy("has_blewit", has(FIItems.BLEWIT_MUSHROOM.get()))
                .save(output, ForagersInsight.rl("blue_dye_from_blewit"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MAGENTA_DYE, 1)
                .requires(LILAC_BLOOM.get())
                .unlockedBy("has_lilac_bloom", has(LILAC_BLOOM.get()))
                .save(output, ForagersInsight.rl("magenta_dye_from_lilac_bloom"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WHITE_DYE, 2)
                .requires(GHOST_PIPE_ITEM.get())
                .unlockedBy("has_rose_petals", has(GHOST_PIPE_ITEM.get()))
                .save(output, ForagersInsight.rl("white_dye_from_ghost_pipe"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LIGHT_BLUE_DYE)
                .requires(FIBlocks.PHLOX.get())
                .unlockedBy("has_phlox", has(FIBlocks.PHLOX.get()))
                .save(output, ForagersInsight.rl("light_blue_dye_from_phlox"));

        //Hollow Log
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, HOLLOW_LOG.get(), 1)
                .pattern("TTT")
                .pattern("T T")
                .pattern("TTT")
                .define('T', ModItems.TREE_BARK.get())
                .unlockedBy("has_tree_bark", has(ModItems.TREE_BARK.get()))
                .save(output);
        //Ghost Pipe Torch
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FIBlocks.GHOST_PIPE_TORCH.get(), 6)
                .pattern("G  ")
                .pattern("G  ")
                .pattern("G  ")
                .define('G', GHOST_PIPE_ITEM.get())
                .unlockedBy("has_ghost_pipe", has(GHOST_PIPE_ITEM.get()))
                .save(output);

        //WOODSTUFF
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, LILAC_PLANKS.get(), 2)
                .requires(LILAC_LOG.get(), 1)
                .unlockedBy("has_lilac_log", has(LILAC_LOG.get())).save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, LILAC_PLANKS.get(), 2)
                .requires(STRIPPED_LILAC_LOG.get(), 1)
                .unlockedBy("has_stripped_lilac_log", has(STRIPPED_LILAC_LOG.get()))
                .save(output, ForagersInsight.rl("lilac_planks_from_stripped_lilac_log"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LILAC_STAIRS.get(), 4)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .define('P', LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, LILAC_SLAB.get(), 6)
                .pattern("PPP")
                .define('P', LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, LILAC_FENCE.get(), 3)
                .pattern("PSP")
                .pattern("PSP")
                .define('P', LILAC_PLANKS.get())
                .define('S', STICK)
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LILAC_FENCE_GATE.get())
                .pattern("SPS")
                .pattern("SPS")
                .define('P', LILAC_PLANKS.get())
                .define('S', STICK)
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LILAC_DOOR.get(), 3)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .define('P', LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LILAC_TRAPDOOR.get(), 2)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, LILAC_PRESSURE_PLATE.get())
                .pattern("PP")
                .define('P', LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, LILAC_BUTTON.get())
                .requires(LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FIBlocks.LILAC_SIGN.get(), 3)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" S ")
                .define('P', LILAC_PLANKS.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FIBlocks.LILAC_HANGING_SIGN.get(), 6)
                .pattern("C C")
                .pattern("PPP")
                .pattern("PPP")
                .define('C', CHAIN)
                .define('P', STRIPPED_LILAC_LOG.get())
                .unlockedBy("has_stripped_lilac_log", has(STRIPPED_LILAC_LOG.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, LILAC_BOAT.get())
                .pattern("P P")
                .pattern("PPP")
                .define('P', LILAC_PLANKS.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, LILAC_CHEST_BOAT.get())
                .requires(Tags.Items.CHESTS_WOODEN)
                .requires(LILAC_BOAT.get())
                .unlockedBy("has_lilac_boat", has(LILAC_BOAT.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, LILAC_CABINET.get())
                .pattern("PPP")
                .pattern("T T")
                .pattern("PPP")
                .define('P', LILAC_SLAB.get())
                .define('T', LILAC_TRAPDOOR.get())
                .unlockedBy("has_lilac_planks", has(LILAC_PLANKS.get()))
                .save(output);


        //Alternate Recipes
        //Rabbit Hide
            //Book
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BOOK)
                .requires(PAPER)
                .requires(PAPER)
                .requires(PAPER)
                .requires(RABBIT_HIDE)
                .unlockedBy("has_rabbit_hide", has(RABBIT_HIDE))
                .save(output, ForagersInsight.rl("book_from_rabbit_hide"));
            // Item Frame
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ITEM_FRAME)
                .pattern("SSS")
                .pattern("SHS")
                .pattern("SSS")
                .define('S', STICK)
                .define('H', RABBIT_HIDE)
                .unlockedBy("has_rabbit_hide", has(RABBIT_HIDE))
                .save(output, ForagersInsight.rl("item_frame_from_rabbit_hide"));
        //Ghost Pipe
            //Glow Item Frame
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GLOW_ITEM_FRAME)
                .pattern("GC ")
                .define('C', ITEM_FRAME)
                .define('G', GHOST_PIPE_ITEM.get())
                .unlockedBy("has_ghost_pipe", has(GHOST_PIPE_ITEM.get()))
                .save(output);

        //Storage
        this.storageRecipes(output, RecipeCategory.FOOD, APPLE, RecipeCategory.DECORATIONS, APPLE_CRATE.get());
        this.storageRecipes(output, RecipeCategory.FOOD, ROSE_HIP.get(), RecipeCategory.DECORATIONS, ROSE_HIP_SACK.get());
        this.storageRecipes(output, RecipeCategory.FOOD, POPPY_SEEDS.get(), RecipeCategory.DECORATIONS, POPPY_SEEDS_SACK.get());
        this.storageRecipes(output, RecipeCategory.FOOD, DANDELION_ROOT.get(), RecipeCategory.DECORATIONS, DANDELION_ROOT_SACK.get());
        this.storageRecipes(output, RecipeCategory.FOOD, BLACK_ACORN.get(), RecipeCategory.DECORATIONS, BLACK_ACORN_SACK.get());
        this.storageRecipes(output, RecipeCategory.FOOD, SPRUCE_TIPS.get(), RecipeCategory.DECORATIONS, SPRUCE_TIPS_SACK.get());
        this.storageRecipes(output, RecipeCategory.FOOD, ROSELLE_CALYX.get(), RecipeCategory.DECORATIONS, ROSELLE_CALYX_SACK.get());
        this.storageRecipes(output, RecipeCategory.FOOD, FIItems.BLEWIT_MUSHROOM.get(), RecipeCategory.DECORATIONS, FIBlocks.BLEWIT_CRATE.get());
        this.storageRecipes(output, RecipeCategory.FOOD, LILAC_BLOOM.get(), RecipeCategory.DECORATIONS, LILAC_BLOOM_CRATE.get());
        this.storageRecipes(output, RecipeCategory.MISC, FIItems.TINDER_CONK.get(), RecipeCategory.DECORATIONS, TINDER_CONK_CRATE.get());

        FICookingRecipes.buildRecipes(output);
        FICrushandCutRecipes.buildRecipes(output);
    }
    private void addVanillaOverrides(RecipeOutput output) {
        //Book
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BOOK, 2)
                .requires(PAPER)
                .requires(PAPER)
                .requires(PAPER)
                .requires(LEATHER)
                .unlockedBy("has_leather", has(LEATHER))
                .save(output, ResourceLocation.fromNamespaceAndPath("minecraft", "book"));
        //Bread
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.BREAD)
                .pattern("WWW")
                .define('W', FITags.ItemTag.WHEAT)
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(output, ResourceLocation.fromNamespaceAndPath("minecraft", "bread"));

        //Cookie
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.COOKIE, 8)
                .requires(FITags.ItemTag.WHEAT)
                .requires(FITags.ItemTag.COCOA)
                .requires(FITags.ItemTag.WHEAT)
                .unlockedBy("has_cocoa", has(COCOA_BEANS))
                .save(output, ResourceLocation.fromNamespaceAndPath("minecraft", "cookie"));
        //Cake
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, Items.CAKE)
                .pattern("MMM")
                .pattern("SES")
                .pattern("WWW")
                .define('M', FITags.ItemTag.MILK)
                .define('S', Items.SUGAR)
                .define('E', Tags.Items.EGGS)
                .define('W', FITags.ItemTag.WHEAT)
                .unlockedBy("has_egg", has(Items.EGG))
                .save(output, ResourceLocation.fromNamespaceAndPath("minecraft", "cake"));
        //Item Frame
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ITEM_FRAME, 2)
                .pattern("SSS")
                .pattern("SLS")
                .pattern("SSS")
                .define('S', STICK)
                .define('L', LEATHER)
                .unlockedBy("has_leather", has(LEATHER))
                .save(output, ResourceLocation.fromNamespaceAndPath("minecraft", "item_frame"));
        //Suspicious Stew
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, Items.SUSPICIOUS_STEW)
                .requires(Ingredient.of(Items.BROWN_MUSHROOM, FIItems.BLEWIT_MUSHROOM.get()))
                .requires(Ingredient.of(Items.RED_MUSHROOM, FIItems.BLEWIT_MUSHROOM.get()))
                .requires(ItemTags.SMALL_FLOWERS)
                .requires(BOWL)
                .unlockedBy("has_mushroom", has(FITags.ItemTag.MUSHROOM))
                .save(output, ResourceLocation.fromNamespaceAndPath("minecraft", "suspicious_stew"));

    }
    private void addFarmersDelightOverrides(RecipeOutput output) {
    //Wheat Dough
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, vectorwing.farmersdelight.common.registry.ModItems.WHEAT_DOUGH.get(), 3)
                        .pattern("EW ")
                        .pattern("WW ")
                        .define('W', FITags.ItemTag.WHEAT)
                        .define('E', Ingredient.fromValues(java.util.stream.Stream.of(
                                new Ingredient.TagValue(Tags.Items.EGGS),
                                new Ingredient.ItemValue(new net.minecraft.world.item.ItemStack(Items.WATER_BUCKET)))))
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);
    //Pie Crust
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.PIE_CRUST.get())
                        .pattern("WMW")
                        .pattern(" W ")
                        .define('W', FITags.ItemTag.WHEAT)
                        .define('M', FITags.ItemTag.MILK)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);
        // Honey Cookie (shapeless)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.HONEY_COOKIE.get(), 8)
                        .requires(FITags.ItemTag.WHEAT)
                        .requires(FITags.ItemTag.WHEAT)
                        .requires(HONEY_BOTTLE)
                        .unlockedBy("has_honey", has(HONEY_BOTTLE))
                        .save(output);

// Sweet Berry Cookie (shapeless)
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SWEET_BERRY_COOKIE.get(), 8)
                        .requires(FITags.ItemTag.WHEAT)
                        .requires(SWEET_BERRIES)
                        .requires(FITags.ItemTag.WHEAT)
                        .unlockedBy("has_sweet_berries", has(SWEET_BERRIES))
                        .save(output);
// Chocolate Pie
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.CHOCOLATE_PIE.get())
                        .pattern("CCC")
                        .pattern("MMM")
                        .pattern("SPS")
                        .define('C', FITags.ItemTag.COCOA)
                        .define('M', FITags.ItemTag.MILK)
                        .define('S', SUGAR)
                        .define('P', ModItems.PIE_CRUST.get())
                        .unlockedBy("has_cocoa_beans", has(COCOA_BEANS))
                        .save(output);
// Apple Pie
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.APPLE_PIE.get())
                        .pattern("WWW")
                        .pattern("AAA")
                        .pattern("SCS")
                        .define('W', FITags.ItemTag.WHEAT)
                        .define('A', FITags.ItemTag.APPLE)
                        .define('S', SUGAR)
                        .define('C', ModItems.PIE_CRUST.get())
                        .unlockedBy("has_apple", has(APPLE))
                        .save(output);
    }

    private void cookie(Supplier<Item> cookie, Supplier<? extends ItemLike> ingred, RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, cookie.get(), 8)
                .requires(FITags.ItemTag.WHEAT)
                .requires(ingred.get())
                .requires(FITags.ItemTag.WHEAT)
                .unlockedBy("has_ing", has(ingred.get()))
                .save(output);
    }
}
