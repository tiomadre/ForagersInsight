package com.tiomadre.foragersinsight.data.server.tags;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.tiomadre.foragersinsight.core.registry.FIBlocks;
import com.tiomadre.foragersinsight.core.registry.FIItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;
import static com.tiomadre.foragersinsight.data.server.tags.FITags.ItemTag.*;

public class FIItemTags extends ItemTagsProvider {
    public FIItemTags(GatherDataEvent e, FIBlockTags blockTags) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), blockTags.contentsGetter());
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //Other
        this.tag(FITags.ItemTag.DOUGH).add(ModItems.WHEAT_DOUGH.get(), FIItems.ACORN_DOUGH.get());
        this.tag(FITags.ItemTag.ICE).add(Items.ICE, FIItems.CRUSHED_ICE.get())
        .addOptional(ResourceLocation.fromNamespaceAndPath("neapolitan", "ice_cubes"));
        this.tag(NUTS).add(FIItems.BLACK_ACORN.get());
        this.tag(NUTS_ACORN).add(FIItems.BLACK_ACORN.get());
        this.tag(SEEDS).add(FIItems.POPPY_SEEDS.get());
        this.tag(MILK).add(FIItems.SEED_MILK_BUCKET.get(), FIItems.SEED_MILK_BOTTLE.get());
        this.tag(MILK_BUCKET).add(FIItems.SEED_MILK_BUCKET.get());
        this.tag(MILK_BOTTLE).add(FIItems.SEED_MILK_BOTTLE.get());
        this.tag(ItemTags.SMALL_FLOWERS).add(FIBlocks.STOUT_BEACH_ROSE_BUSH.get().asItem(), FIBlocks.GHOST_PIPE.get().asItem());
        this.tag(ItemTags.TALL_FLOWERS).add(FIBlocks.ROSELLE_BUSH.get().asItem(), FIBlocks.TALL_BEACH_ROSE_BUSH.get().asItem());
        this.tag(ItemTags.FLOWERS).add(FIBlocks.ROSELLE_BUSH.get().asItem(), FIBlocks.STOUT_BEACH_ROSE_BUSH.get().asItem(), FIBlocks.TALL_BEACH_ROSE_BUSH.get().asItem(), FIBlocks.GHOST_PIPE.get().asItem());
        this.tag(WOLF_PREY).add(FIItems.RAW_RABBIT_LEG.get());

        this.tag(WILD_FLOWER_DROPS).add(FIItems.ROSE_HIP.get(), FIItems.LILAC_BLOOM.get(), FIItems.POPPY_SEEDS.get(), FIItems.DANDELION_ROOT.get(), FIItems.ROSELLE_CALYX.get());

        var foragersInsightItems = this.tag(FORAGERS_INSIGHT_ITEMS);
        BuiltInRegistries.ITEM.stream()
                .filter(item -> ForagersInsight.MOD_ID.equals(BuiltInRegistries.ITEM.getKey(item).getNamespace()))
                .forEach(foragersInsightItems::add);

        //Crops
        this.tag(APPLE).add(FIItems.APPLE_SLICE.get(),Items.APPLE);
        this.tag(POPPY_SEEDS).add(FIItems.POPPY_SEEDS.get(), FIItems.POPPY_SEED_PASTE.get());
        this.tag(ACORN).add(FIItems.BLACK_ACORN.get(), FIItems.ACORN_MEAL.get());
        this.tag(WHEAT).add(FIItems.WHEAT_FLOUR.get(), Items.WHEAT);
        this.tag(LILAC).add(FIItems.LILAC_BLOOM.get(), Items.LILAC);
        this.tag(COCOA).add(Items.COCOA_BEANS, FIItems.COCOA_POWDER.get());
        this.tag(ROOTS).add(Items.CARROT, Items.BEETROOT, FIItems.DANDELION_ROOT.get());
        this.tag(MUSHROOM).add(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, FIItems.BLEWIT_MUSHROOM.get());

        this.tag(CROPS).addTags(APPLE,POPPY_SEEDS,ACORN,WHEAT,COCOA,ROOTS,MUSHROOM,LILAC).add(FIItems.TINDER_CONK.get());
        //Food
        this.tag(BLEWIT_STUFFING).addTags(SEEDS,NUTS,VEGETABLES,RAW_MEATS,RAW_FISHES);

        // Handbasket support tags
        this.tag(RAW_MEATS).add(Items.RABBIT, Items.CHICKEN, Items.PORKCHOP, Items.BEEF, Items.MUTTON, FIItems.RAW_RABBIT_LEG.get())
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "chicken_cuts"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "minced_beef"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "bacon"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "mutton_chops"));
        this.tag(COOKED_MEATS).add(Items.COOKED_RABBIT, Items.COOKED_CHICKEN, Items.COOKED_PORKCHOP, Items.COOKED_BEEF,
                        Items.COOKED_MUTTON, FIItems.COOKED_RABBIT_LEG.get())
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "cooked_bacon"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "cooked_mutton_chops"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "ham"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "beef_patty"));
        this.tag(RAW_FISHES).add(Items.COD, Items.SALMON)
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "cod_slice"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "salmon_slice"));
        this.tag(COOKED_FISHES).add(Items.COOKED_COD, Items.COOKED_SALMON)
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "cooked_cod_slice"))
                .addOptional(ResourceLocation.fromNamespaceAndPath("farmersdelight", "cooked_salmon_slice"));
        this.tag(FRUITS).add(FIItems.ROSE_HIP.get(), FIItems.ROSELLE_CALYX.get(), FIItems.APPLE_SLICE.get())
                .addTag(APPLE);
        this.tag(VEGETABLES).add(FIItems.SPRUCE_TIPS.get())
                .addTag(ROOTS);
        this.tag(FLOUR).add(FIItems.ACORN_MEAL.get(), FIItems.WHEAT_FLOUR.get());
        this.tag(STRAW).add(ModItems.STRAW.get());
        this.tag(TREE_BARK).add(ModItems.TREE_BARK.get());
        this.tag(EGGS).add(Items.EGG);
        this.tag(LEATHER).add(Items.LEATHER, Items.RABBIT_HIDE);

        this.tag(HANDBASKET_OTHER)
        .add(Items.INK_SAC, Items.GLOW_INK_SAC, Items.RABBIT_FOOT, Items.SUGAR,Items.SUGAR_CANE, Items.FEATHER, Items.KELP, Items.DRIED_KELP, Items.BAMBOO,
         Items.CACTUS, Items.VINE, Items.MOSS_BLOCK, Items.AZALEA, Items.FLOWERING_AZALEA, Items.HONEYCOMB)
        .add(FIItems.ROSE_PETALS.get(), FIItems.ROSELLE_PETALS.get(), FIItems.BIRCH_SAP_BOTTLE.get(), FIItems.BIRCH_SYRUP_BOTTLE.get(), FIItems.ROSELLE_BUSH_ITEM.get(),
         FIItems.STOUT_BEACH_ROSE_BUSH_ITEM.get(),FIItems.TALL_BEACH_ROSE_BUSH_ITEM.get(), FIItems.LILAC_BLOOM.get(),FIItems.TINDER_CONK_SPORES.get(),FIItems.AMADOU.get())
        .add(ModItems.WILD_CABBAGES.get(), ModItems.WILD_BEETROOTS.get(), ModItems.WILD_POTATOES.get(), ModItems.WILD_TOMATOES.get(), ModItems.WILD_CARROTS.get(),
        ModItems.RICE_PANICLE.get(), ModItems.PUMPKIN_SLICE.get(), ModItems.CABBAGE_LEAF.get(), ModItems.MILK_BOTTLE.get());

     //Handbasket
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> handbasketAllowedTag = this.tag(HANDBASKET_ALLOWED);
        addOptionalTags(handbasketAllowedTag, "c",
                "raw_meats", "cooked_meats", "raw_fishes", "cooked_fishes", "eggs", "leather",
                "feathers", "nuts", "seeds", "crops", "fruits", "vegetables", "crops/mushroom",
                "flour", "straw", "tree_bark", "ice", "sugar", "milk/milk", "milk/milk_bottle", "honey_bottle"
        );
        addOptionalTags(handbasketAllowedTag, "minecraft", "flowers", "saplings");
        handbasketAllowedTag.addTag(HANDBASKET_OTHER);

        // Mallet
        this.tag(FITags.ItemTag.MALLETS).add(FIItems.FLINT_MALLET.get(),FIItems.IRON_MALLET.get(),
        FIItems.GOLD_MALLET.get(),FIItems.DIAMOND_MALLET.get(),FIItems.NETHERITE_MALLET.get());
        //Shears
        this.tag(SHEARS).add(FIItems.FLINT_SHEARS.get());

        //Wood
        this.tag(ItemTags.SIGNS).add(FIItems.LILAC_SIGN.get());
        this.tag(ItemTags.HANGING_SIGNS).add(FIItems.LILAC_HANGING_SIGN.get());
        this.tag(ItemTags.BOATS).add(FIItems.LILAC_BOAT.get());
        this.tag(ItemTags.CHEST_BOATS).add(FIItems.LILAC_CHEST_BOAT.get());
        this.tag(ItemTags.PLANKS).add(FIBlocks.LILAC_PLANKS.get().asItem()
        );

        registerNeoTags();
    }
    protected void registerNeoTags() {
        tag(STORAGE_BLOCK_ROSE_HIP).add(FIBlocks.ROSE_HIP_SACK.get().asItem());
        tag(STORAGE_BLOCK_ROSELLE_CALYX).add(FIBlocks.ROSELLE_CALYX_SACK.get().asItem());
        tag(STORAGE_BLOCK_POPPY_SEEDS).add(FIBlocks.POPPY_SEEDS_SACK.get().asItem());
        tag(STORAGE_BLOCK_DANDELION_ROOT).add(FIBlocks.DANDELION_ROOT_SACK.get().asItem());
        tag(STORAGE_BLOCK_SPRUCE_TIPS).add(FIBlocks.SPRUCE_TIPS_SACK.get().asItem());
        tag(STORAGE_BLOCK_BLACK_ACORNS).add(FIBlocks.BLACK_ACORN_SACK.get().asItem());
        tag(STORAGE_BLOCK_BLEWIT_MUSHROOM).add(FIBlocks.BLEWIT_CRATE.get().asItem());
        tag(STORAGE_BLOCK_LILAC_BLOOM).add(FIBlocks.LILAC_BLOOM_CRATE.get().asItem());
        tag(STORAGE_BLOCK_TINDER_CONK).add(FIBlocks.TINDER_CONK_CRATE.get().asItem());
    }
    private void addOptionalTags(IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> tagAppender, String namespace, String... paths) {
        for (String path : paths) {
            tagAppender.addOptionalTag(ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
    }


}
