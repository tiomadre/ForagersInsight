package com.tiomadre.foragersinsight.data.server.tags;

import com.tiomadre.foragersinsight.core.ForagersInsight;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class FITags {
        public static class ItemTag {
            public static final TagKey<Item> ICE = TagUtil.itemTag("c","ice");
            public static final TagKey<Item> NUTS = TagUtil.itemTag("c", "nuts");
            public static final TagKey<Item> NUTS_ACORN = TagUtil.itemTag("c", "nuts/acorn");
            public static final TagKey<Item> FLOURS = TagUtil.itemTag("c", "flours");
            public static final TagKey<Item> FLOURS_WHEAT = TagUtil.itemTag("c", "flours/wheat");
            public static final TagKey<Item> STRAW = TagUtil.itemTag("c", "straw");
            public static final TagKey<Item> TREE_BARK = TagUtil.itemTag("c", "tree_bark");
            public static final TagKey<Item> WOLF_PREY = TagUtil.itemTag("c", "wolf_prey");

            //Mallet
            public static final TagKey<net.minecraft.world.item.Item> MALLETS = TagUtil.itemTag("c", "tools/mallets");
            //Foods
            public static final TagKey<net.minecraft.world.item.Item> FOODS_APPLE = TagUtil.itemTag("c", "crops/apple");
            public static final TagKey<net.minecraft.world.item.Item> FOODS_POPPY_SEEDS = TagUtil.itemTag("c", "foods/poppy_seeds");
            public static final TagKey<net.minecraft.world.item.Item> FOODS_ACORN = TagUtil.itemTag("c", "foods/acorn");
            public static final TagKey<net.minecraft.world.item.Item> FOODS_LILAC = TagUtil.itemTag("c", "foods/lilac");
            public static final TagKey<net.minecraft.world.item.Item> FOODS_ROOTS = TagUtil.itemTag("c", "foods/root_vegetable");
            public static final TagKey<net.minecraft.world.item.Item> FOODS_MUSHROOM = TagUtil.itemTag("c", "foods/mushroom");
            public static final TagKey<net.minecraft.world.item.Item> FOODS_COCOA = TagUtil.itemTag("c", "foods/cocoa");
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
