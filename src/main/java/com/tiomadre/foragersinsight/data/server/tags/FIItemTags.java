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
import vectorwing.farmersdelight.common.tag.CommonTags;

import static com.tiomadre.foragersinsight.data.server.tags.FITags.ItemTag.*;
import static net.neoforged.neoforge.common.Tags.Items.*;

public class FIItemTags extends ItemTagsProvider {
    public FIItemTags(GatherDataEvent e, FIBlockTags blockTags) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider(), blockTags.contentsGetter());
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //Other
        this.tag(CommonTags.Items.FOODS_DOUGH).add(FIItems.ACORN_DOUGH.get());
        this.tag(FITags.ItemTag.ICE).add(Items.ICE, FIItems.CRUSHED_ICE.get())
        .addOptional(ResourceLocation.fromNamespaceAndPath("neapolitan", "ice_cubes"));
        this.tag(NUTS).add(FIItems.BLACK_ACORN.get());
        this.tag(NUTS_ACORN).add(FIItems.BLACK_ACORN.get());
        this.tag(SEEDS).add(FIItems.POPPY_SEEDS.get());
        this.tag(BUCKETS_MILK).add(FIItems.SEED_MILK_BUCKET.get());
        this.tag(DRINKS_MILK).add(FIItems.SEED_MILK_BOTTLE.get());
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
        this.tag(FOODS_APPLE).add(FIItems.APPLE_SLICE.get(), Items.APPLE);
        this.tag(FOODS_POPPY_SEEDS).add(FIItems.POPPY_SEEDS.get(), FIItems.POPPY_SEED_PASTE.get());
        this.tag(FOODS_ACORN).add(FIItems.BLACK_ACORN.get(), FIItems.ACORN_MEAL.get());
        this.tag(FOODS_LILAC).add((FIItems.LILAC_BLOOM.get()), Items.LILAC);
        this.tag(FOODS_ROOTS).add(Items.CARROT, Items.BEETROOT, FIItems.DANDELION_ROOT.get());
        this.tag(FOODS_MUSHROOM).add(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM, FIItems.BLEWIT_MUSHROOM.get());
        this.tag(FOODS_COCOA).add(Items.COCOA_BEANS, FIItems.COCOA_POWDER.get());

        this.tag(FOODS).addTags(FOODS_APPLE,FOODS_POPPY_SEEDS,FOODS_ACORN,FOODS_ROOTS,FOODS_MUSHROOM,FOODS_LILAC).add(FIItems.TINDER_CONK.get());
        //Food
        this.tag(BLEWIT_STUFFING).addTags(SEEDS,NUTS,FOODS_VEGETABLE,FOODS_RAW_MEAT,FOODS_RAW_FISH);

        // Handbasket support tags
        this.tag(FOODS_RAW_MEAT).add(FIItems.RAW_RABBIT_LEG.get());
        this.tag(FOODS_COOKED_MEAT).add(FIItems.COOKED_RABBIT_LEG.get());
        this.tag(FOODS_FRUIT).add(FIItems.ROSE_HIP.get(), FIItems.ROSELLE_CALYX.get(), FIItems.APPLE_SLICE.get());
        this.tag(FOODS_VEGETABLE).add(FIItems.SPRUCE_TIPS.get(), FIItems.DANDELION_ROOT.get());
        this.tag(FLOURS).add(FIItems.ACORN_MEAL.get(), FIItems.WHEAT_FLOUR.get());
        this.tag(FLOURS_WHEAT).add(FIItems.WHEAT_FLOUR.get());
        this.tag(STRAW).add(ModItems.STRAW.get());
        this.tag(TREE_BARK).add(ModItems.TREE_BARK.get());
        this.tag(EGGS).add(Items.EGG);
        this.tag(LEATHERS).add(Items.LEATHER, Items.RABBIT_HIDE);

        this.tag(HANDBASKET_OTHER)
        .add(Items.INK_SAC, Items.GLOW_INK_SAC, Items.RABBIT_FOOT, Items.SUGAR,Items.SUGAR_CANE, Items.FEATHER, Items.KELP, Items.DRIED_KELP, Items.BAMBOO,
         Items.CACTUS, Items.VINE, Items.MOSS_BLOCK, Items.AZALEA, Items.FLOWERING_AZALEA, Items.HONEYCOMB)
        .add(FIItems.ROSE_PETALS.get(), FIItems.ROSELLE_PETALS.get(), FIItems.BIRCH_SAP_BOTTLE.get(), FIItems.BIRCH_SYRUP_BOTTLE.get(), FIItems.ROSELLE_BUSH_ITEM.get(),
         FIItems.STOUT_BEACH_ROSE_BUSH_ITEM.get(),FIItems.TALL_BEACH_ROSE_BUSH_ITEM.get(), FIItems.LILAC_BLOOM.get(),FIItems.TINDER_CONK_SPORES.get(),FIItems.AMADOU.get())
        .add(ModItems.WILD_CABBAGES.get(), ModItems.WILD_BEETROOTS.get(), ModItems.WILD_POTATOES.get(), ModItems.WILD_TOMATOES.get(), ModItems.WILD_CARROTS.get(),
        ModItems.RICE_PANICLE.get(), ModItems.PUMPKIN_SLICE.get(), ModItems.CABBAGE_LEAF.get(), ModItems.MILK_BOTTLE.get(), Items.HONEY_BOTTLE);

     //Handbasket
        IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> handbasketAllowedTag = this.tag(HANDBASKET_ALLOWED);
        addOptionalTags(handbasketAllowedTag, "c",
                "raw_meats", "cooked_meat", "raw_fish", "cooked_fish", "egg", "leather",
                "feather", "seeds", "crops", "crops/mushroom",
                "flour", "straw", "tree_bark", "ice", "sugar");
        addOptionalTags(handbasketAllowedTag, "minecraft", "flowers", "saplings");
        handbasketAllowedTag.addTag(HANDBASKET_OTHER);

        //Amadou cap
        this.tag(ItemTags.DYEABLE).add(FIItems.AMADOU_CAP.get());

        // Mallet
        this.tag(FITags.ItemTag.MALLETS).add(FIItems.FLINT_MALLET.get(),FIItems.IRON_MALLET.get(),
        FIItems.GOLD_MALLET.get(),FIItems.DIAMOND_MALLET.get(),FIItems.NETHERITE_MALLET.get());
        //Shears
        this.tag(TOOLS_SHEAR).add(FIItems.FLINT_SHEARS.get());

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
