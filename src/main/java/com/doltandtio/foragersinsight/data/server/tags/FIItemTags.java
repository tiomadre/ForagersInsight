package com.doltandtio.foragersinsight.data.server.tags;

import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import com.doltandtio.foragersinsight.core.registry.FIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;
import static com.doltandtio.foragersinsight.data.server.tags.FITags.ItemTag.*;

public class FIItemTags extends ItemTagsProvider {
    public FIItemTags(GatherDataEvent e, FIBlockTags blockTags) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), blockTags.contentsGetter());
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //Other
        this.tag(FITags.ItemTag.ICE).add(Items.ICE, FIItems.CRUSHED_ICE.get())
                .addOptional(new ResourceLocation("neapolitan", "ice_cubes"));
        this.tag(FITags.ItemTag.NUTS).add(FIItems.BLACK_ACORN.get());
        this.tag(FITags.ItemTag.SEEDS).add(FIItems.BLACK_ACORN.get(), FIItems.POPPY_SEEDS.get());
        this.tag(FITags.ItemTag.MILK_BUCKET).add(FIItems.SEED_MILK_BUCKET.get());
        this.tag(FITags.ItemTag.MILK_BOTTLE).add(FIItems.SEED_MILK_BOTTLE.get());
        //Crops
        this.tag(FITags.ItemTag.APPLE).add(FIItems.APPLE_SLICE.get(),Items.APPLE);
        this.tag(FITags.ItemTag.POPPY_SEEDS).add(FIItems.POPPY_SEEDS.get(), FIItems.POPPY_SEED_PASTE.get());
        this.tag(ACORN).add(FIItems.BLACK_ACORN.get(), FIItems.ACORN_MEAL.get());
        this.tag(WHEAT).add(FIItems.WHEAT_FLOUR.get(), Items.WHEAT);
        this.tag(COCOA).add(Items.COCOA_BEANS, FIItems.COCOA_POWDER.get());
        this.tag(ROOTS).add(Items.CARROT,Items.BEETROOT, FIItems.DANDELION_ROOT.get());
        this.tag(MUSHROOM).add(Items.RED_MUSHROOM,Items.BROWN_MUSHROOM);
        //Handbasket
        this.tag(HANDBASKET_ALLOWED)
                //Animal Drops, Meat, Meat Cuts
                .add(Items.COOKED_RABBIT, Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.BEEF, Items.MUTTON, Items.COD, Items.SALMON, Items.COOKED_BEEF
                ,Items.COOKED_COD,Items.COOKED_PORKCHOP,Items.COOKED_CHICKEN,Items.COOKED_SALMON,Items.COOKED_MUTTON,ModItems.COD_SLICE.get(),ModItems.COOKED_COD_SLICE.get(),ModItems.SALMON_SLICE.get(),ModItems.CHICKEN_CUTS.get()
                ,ModItems.MINCED_BEEF.get(),ModItems.BACON.get(),ModItems.MUTTON_CHOPS.get(),ModItems.COOKED_SALMON_SLICE.get(),ModItems.COOKED_BACON.get(),ModItems.COOKED_MUTTON_CHOPS.get()
                ,ModItems.HAM.get(),ModItems.BEEF_PATTY.get(),Items.INK_SAC,Items.GLOW_INK_SAC,FIItems.RAW_RABBIT_LEG.get(),FIItems.COOKED_RABBIT_LEG.get(),Items.EGG,Items.LEATHER,Items.RABBIT_FOOT,Items.RABBIT_HIDE,Items.FEATHER)
                //Crops
                .add(Items.WHEAT,Items.SWEET_BERRIES, Items.BEETROOT, Items.POTATO, Items.CARROT, Items.PUMPKIN, Items.MELON_SLICE, Items.APPLE,Items.KELP,Items.BAMBOO,
                 Items.COCOA_BEANS,Items.PUMPKIN,Items.MELON,Items.SUGAR_CANE,Items.GLOW_BERRIES,Items.CACTUS, Items.RED_MUSHROOM,Items.BROWN_MUSHROOM
                ,Items.NETHER_WART, FIItems.ROSE_HIP.get(),FIItems.ROSELLE_CALYX.get(),FIItems.BLACK_ACORN.get(),FIItems.DANDELION_ROOT.get(),FIItems.POPPY_SEEDS.get(),FIItems.SPRUCE_TIPS.get()
                ,ModItems.ONION.get(),ModItems.TOMATO.get(),ModItems.RICE.get())
                    //Crop Cuts and Crushed
                     .add(ModItems.STRAW.get(),ModItems.PUMPKIN_SLICE.get(),ModItems.CABBAGE_LEAF.get(),ModItems.RICE_PANICLE.get(),FIItems.ROSE_PETALS.get(),FIItems.ROSELLE_PETALS.get()
                     ,FIItems.APPLE_SLICE.get(),FIItems.ACORN_MEAL.get(),FIItems.COCOA_POWDER.get(),FIItems.WHEAT_FLOUR.get(),FIItems.POPPY_SEED_PASTE.get(),FIItems.CRUSHED_ICE.get())
                //Seeds
                .add(Items.WHEAT_SEEDS,Items.PUMPKIN_SEEDS,Items.MELON_SEEDS,Items.BEETROOT_SEEDS,Items.TORCHFLOWER_SEEDS,Items.PITCHER_POD,ModItems.TOMATO_SEEDS.get())
                //Flower,Plants and Vines
                .add(Items.POPPY,Items.DANDELION,Items.BLUE_ORCHID,Items.ALLIUM,Items.AZURE_BLUET,Items.RED_TULIP,Items.ORANGE_TULIP,
                Items.WHITE_TULIP,Items.PINK_TULIP,Items.OXEYE_DAISY,Items.CORNFLOWER,Items.LILY_OF_THE_VALLEY,Items.WITHER_ROSE,Items.SUNFLOWER,Items.LILAC
                ,Items.ROSE_BUSH,Items.PEONY,Items.TORCHFLOWER,Items.PITCHER_PLANT,Items.VINE,Items.MOSS_BLOCK,ModItems.WILD_CABBAGES.get(),ModItems.WILD_BEETROOTS.get(),
                 ModItems.WILD_POTATOES.get(),ModItems.WILD_TOMATOES.get(),ModItems.WILD_CARROTS.get(),ModItems.WILD_POTATOES.get())
                //Saplings
                .add(Items.OAK_SAPLING,Items.SPRUCE_SAPLING,Items.BIRCH_SAPLING,Items.JUNGLE_SAPLING,Items.ACACIA_SAPLING,Items.DARK_OAK_SAPLING
                ,Items.AZALEA,Items.FLOWERING_AZALEA, FIBlocks.BOUNTIFUL_DARK_OAK_SAPLING.get().asItem(), FIBlocks.BOUNTIFUL_OAK_SAPLING.get().asItem(),
                FIBlocks.BOUNTIFUL_SPRUCE_SAPLING.get().asItem())
                //Other
                .add(ModItems.TREE_BARK.get(),Items.HONEYCOMB,Items.SUGAR,Items.HONEY_BOTTLE,ModItems.MILK_BOTTLE.get(),FIItems.SEED_MILK_BOTTLE.get(),Items.DRIED_KELP
                ,FIItems.BIRCH_SAP_BOTTLE.get(),FIItems.BIRCH_SYRUP_BOTTLE.get())

                ;
        // Mallet
        this.tag(FITags.ItemTag.MALLETS).add(FIItems.FLINT_MALLET.get(),FIItems.IRON_MALLET.get(),
        FIItems.GOLD_MALLET.get(),FIItems.DIAMOND_MALLET.get(),FIItems.NETHERITE_MALLET.get());
        //Shears
        //this.tag(Tags.Items.TOOLS_SHEAR).add(FIItems.FLINT_SHEARS.get()); reenable when using newer FD version that uses tag
    }
    protected void registerForgeTags() {
        tag(STORAGE_BLOCK_ROSE_HIP).add(FIBlocks.ROSE_HIP_SACK.get().asItem());

        tag(STORAGE_BLOCK_POPPY_SEEDS).add(FIBlocks.POPPY_SEEDS_SACK.get().asItem());
        tag(STORAGE_BLOCK_DANDELION_ROOT).add(FIBlocks.DANDELION_ROOTS_CRATE.get().asItem());

        tag(STORAGE_BLOCK_SPRUCE_TIPS).add(FIBlocks.SPRUCE_TIPS_SACK.get().asItem());
        tag(STORAGE_BLOCK_BLACK_ACORNS).add(FIBlocks.BLACK_ACORN_SACK.get().asItem());
    }
}
