package com.tiomadre.foragersinsight.data.server.tags;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class FITags {
        public static class ItemTag {
            public static final TagKey<Item> ICE = TagUtil.itemTag("c", "ice");
            public static final TagKey<Item> SEEDS = TagUtil.itemTag("c", "seeds");
            public static final TagKey<Item> NUTS = TagUtil.itemTag("c", "nuts");
            public static final TagKey<Item> NUTS_ACORN = TagUtil.itemTag("c", "nuts/acorn");
            public static final TagKey<Item> CROPS = TagUtil.itemTag("c", "crops");
            public static final TagKey<Item> RAW_MEATS = TagUtil.itemTag("c", "raw_meats");
            public static final TagKey<Item> COOKED_MEATS = TagUtil.itemTag("c", "cooked_meats");
            public static final TagKey<Item> RAW_FISHES = TagUtil.itemTag("c", "raw_fishes");
            public static final TagKey<Item> COOKED_FISHES = TagUtil.itemTag("c", "cooked_fishes");
            public static final TagKey<Item> FRUITS = TagUtil.itemTag("c", "fruits");
            public static final TagKey<Item> VEGETABLES = TagUtil.itemTag("c", "vegetables");
            public static final TagKey<Item> LEATHER = TagUtil.itemTag("c", "leather");
            public static final TagKey<Item> EGGS = TagUtil.itemTag("c", "eggs");
            public static final TagKey<Item> MILK = TagUtil.itemTag("c", "milk");
            public static final TagKey<Item> FLOUR = TagUtil.itemTag("c", "flour");
            public static final TagKey<Item> STRAW = TagUtil.itemTag("c", "straw");
            public static final TagKey<Item> TREE_BARK = TagUtil.itemTag("c", "tree_bark");
            public static final TagKey<Item> DOUGH = TagUtil.itemTag("c", "dough");
            public static final TagKey<Item> WOLF_PREY = TagUtil.itemTag("c", "wolf_prey");
            public static final TagKey<Item> SHEARS = TagUtil.itemTag("c", "tools/shears");
            public static final TagKey<Item> TOOLS_AXES = TagUtil.itemTag("c", "tools/axes");
            public static final TagKey<Item> TOOLS_KNIVES = TagUtil.itemTag("c", "tools/knives");
            public static final TagKey<Item> RODS_WOODEN = TagUtil.itemTag("c", "rods/wooden");
            public static final TagKey<Item> CHESTS_WOODEN = TagUtil.itemTag("c", "chests/wooden");

            //Mallet
            public static final TagKey<net.minecraft.world.item.Item> MALLETS = TagUtil.itemTag("c", "tools/mallets");
            //Milk
            public static final TagKey<net.minecraft.world.item.Item> MILK_BUCKET = TagUtil.itemTag("c", "milk/milk");
            public static final TagKey<net.minecraft.world.item.Item> MILK_BOTTLE = TagUtil.itemTag("c", "milk/milk_bottle");
            //Crops
            public static final TagKey<net.minecraft.world.item.Item> APPLE = TagUtil.itemTag("c", "crops/apple");
            public static final TagKey<net.minecraft.world.item.Item> POPPY_SEEDS = TagUtil.itemTag("c", "crops/poppy_seeds");
            public static final TagKey<net.minecraft.world.item.Item> ACORN = TagUtil.itemTag("c", "crops/acorn");
            public static final TagKey<net.minecraft.world.item.Item> WHEAT = TagUtil.itemTag("c", "crops/wheat");
            public static final TagKey<net.minecraft.world.item.Item> COCOA = TagUtil.itemTag("c", "crops/cocoa");
            public static final TagKey<net.minecraft.world.item.Item> LILAC = TagUtil.itemTag("c", "crops/lilac");
            public static final TagKey<net.minecraft.world.item.Item> ROOTS = TagUtil.itemTag("c", "crops/root_vegetable");
            public static final TagKey<net.minecraft.world.item.Item> MUSHROOM = TagUtil.itemTag("c", "crops/mushroom");
            public static final TagKey<net.minecraft.world.item.Item> ONION = TagUtil.itemTag("c", "crops/onion");
            public static final TagKey<net.minecraft.world.item.Item> TOMATO = TagUtil.itemTag("c", "crops/tomato");
            public static final TagKey<net.minecraft.world.item.Item> RAW_COD = TagUtil.itemTag("c", "raw_fishes/cod");
            public static final TagKey<net.minecraft.world.item.Item> COOKED_SALMON = TagUtil.itemTag("c", "cooked_fishes/salmon");
        //Food
        public static final TagKey<Item> BLEWIT_STUFFING = TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "blewit_stuffing"));
        //Advancement Tags

        public static final TagKey<Item> FORAGERS_INSIGHT_ITEMS = TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "foragers_insight_items"));
        public static final TagKey<Item> WILD_FLOWER_DROPS = TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "wild_flower_drops"));
        public static final TagKey<Block> RICH_SOIL_TREE_STARTERS = TagKey.create(Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "rich_soil_tree_starters"));
        //Handbasket
        public static final TagKey<Item> HANDBASKET_ALLOWED = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("foragersinsight","handbasket_allowed"));
        public static final TagKey<Item> HANDBASKET_OTHER = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ForagersInsight.MOD_ID, "handbasket/other"));

        public static final TagKey<Item> STORAGE_BLOCK_ROSE_HIP = storageTag("rose_hip");
        public static final TagKey<Item> STORAGE_BLOCK_SPRUCE_TIPS = storageTag("spruce_tips");
        public static final TagKey<Item> STORAGE_BLOCK_DANDELION_ROOT = storageTag("dandelion_root");
        public static final TagKey<Item> STORAGE_BLOCK_ROSELLE_CALYX = storageTag("roselle_calyx");
        public static final TagKey<Item> STORAGE_BLOCK_POPPY_SEEDS = storageTag("poppy_seeds");
        public static final TagKey<Item> STORAGE_BLOCK_BLACK_ACORNS = storageTag("black_acorns");
        public static final TagKey<Item> STORAGE_BLOCK_BLEWIT_MUSHROOM = storageTag("blewit_mushroom");
        public static final TagKey<Item> STORAGE_BLOCK_LILAC_BLOOM = storageTag("lilac_bloom");
        public static final TagKey<Item> STORAGE_BLOCK_TINDER_CONK = storageTag("tinder_conk");

            public static TagKey<Item> storageTag(String thing) {
                return TagUtil.itemTag("c", "storage_blocks/" + thing);
            }
        }

    public static class BlockTag {
        public static final TagKey<Block> STORAGE_BLOCK_APPLE = storageTag("apple");
        public static final TagKey<Block> STORAGE_BLOCK_ROSE_HIP = storageTag("rose_hip");
        public static final TagKey<Block> STORAGE_BLOCK_SPRUCE_TIPS = storageTag("spruce_tips");
        public static final TagKey<Block> STORAGE_BLOCK_DANDELION_ROOT = storageTag("dandelion_root");
        public static final TagKey<Block> STORAGE_BLOCK_POPPY_SEEDS = storageTag("poppy_seeds");
        public static final TagKey<Block> STORAGE_BLOCK_BLACK_ACORNS = storageTag("black_acorns");
        public static final TagKey<Block> STORAGE_BLOCK_BLEWIT_MUSHROOM = storageTag("blewit_mushroom");
        public static final TagKey<Block> STORAGE_BLOCK_TINDER_CONK = storageTag("tinder_conk");


        public static final TagKey<Block> FORAGING = blockTag("foraging");
        public static final TagKey<Block> SHEARABLE_CROPS = blockTag("shearable_crops");
        public static final TagKey<Block> RICH_SOIL_TREE_SAPLINGS = blockTag("rich_soil_tree_saplings");

        public static TagKey<Block> blockTag(String namespace) {
            return TagUtil.blockTag(ForagersInsight.MOD_ID, namespace);
        }

        public static TagKey<Block> storageTag(String thing) {
            return TagUtil.blockTag("c", "storage_blocks/" + thing);
        }
    }

    public static class BiomeTag {
        public static final TagKey<Biome> HAS_APPLE_TREES = hasFeature("apple_trees");
        public static final TagKey<Biome> HAS_ACORN_TREES = hasFeature("acorn_trees");
        public static final TagKey<Biome> HAS_LILAC_TREES = hasFeature("lilac_trees");
        public static final TagKey<Biome> HAS_SPRUCE_TIP_TREES = hasFeature("spruce_tip_trees");
        public static final TagKey<Biome> HAS_SAPPY_BIRCH_TREES = hasFeature("sappy_birch_trees");
        public static final TagKey<Biome> HAS_ROSELLE_BUSHES = hasFeature("roselle_bushes");
        public static final TagKey<Biome> HAS_BEACH_ROSES = hasFeature("beach_roses");
        public static final TagKey<Biome> HAS_OAK_FOREST_LITTER = hasFeature("oak_forest_litter");
        public static final TagKey<Biome> HAS_BIRCH_FOREST_LITTER = hasFeature("birch_forest_litter");
        public static final TagKey<Biome> HAS_SPRUCE_FOREST_LITTER = hasFeature("spruce_forest_litter");
        public static final TagKey<Biome> HAS_DARK_OAK_FOREST_LITTER = hasFeature("dark_oak_forest_litter");
        public static final TagKey<Biome> HAS_FLOWER_FOREST_LITTER = hasFeature("flower_forest_litter");
        public static final TagKey<Biome> HAS_WOODLAND_FERNS = hasFeature("woodland_ferns");
        public static final TagKey<Biome> HAS_GHOST_PIPE = hasFeature("ghost_pipe");
        public static final TagKey<Biome> HAS_PUDDLES = hasFeature("puddles");

        private static TagKey<Biome> modTag(String namespace) {
            return TagUtil.biomeTag(ForagersInsight.MOD_ID, namespace);
        }
        private static TagKey<Biome> hasFeature(String feature) {
            return modTag("has_feature/" + feature);
        }

    }
}
