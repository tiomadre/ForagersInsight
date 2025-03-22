package com.doltandtio.naturesdelight.data.server.tags;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class NDTags {
    public static class ItemTag {
        public static final TagKey<net.minecraft.world.item.Item> ICE = TagUtil.itemTag("forge", "ice");
        public static final TagKey<net.minecraft.world.item.Item> SEEDS = TagUtil.itemTag("forge", "seeds");
        public static final TagKey<net.minecraft.world.item.Item> MILK_BUCKET = TagUtil.itemTag("forge", "milk/milk");
        public static final TagKey<net.minecraft.world.item.Item> MILK_BOTTLE = TagUtil.itemTag("forge", "milk/milk_bottle");


        private static TagKey<Item> modTag(String namespace) {
            return TagUtil.itemTag(NaturesDelight.MOD_ID, namespace);
        }
        public static final TagKey<Item> STORAGE_BLOCK_ROSE_HIP = storageTag("rose_hip");
        public static final TagKey<Item> STORAGE_BLOCK_SPRUCE_TIPS = storageTag("spruce_tips");
        public static final TagKey<Item> STORAGE_BLOCK_DANDELION_ROOT = storageTag("dandelion_root");
        public static final TagKey<Item> STORAGE_BLOCK_POPPY_SEEDS = storageTag("poppy_seeds");
        public static final TagKey<Item> STORAGE_BLOCK_BLACK_ACORNS = storageTag("black_acorns");

        public static TagKey<Item> storageTag(String thing) {
            return TagUtil.itemTag("forge", "storage_blocks/" + thing);
        }
    }

    public static class BlockTag {
        public static final TagKey<Block> TAPPABLE = blockTag("tappable");

        public static final TagKey<Block> STORAGE_BLOCK_ROSE_HIP = storageTag("rose_hip");
        public static final TagKey<Block> STORAGE_BLOCK_SPRUCE_TIPS = storageTag("spruce_tips");
        public static final TagKey<Block> STORAGE_BLOCK_DANDELION_ROOT = storageTag("dandelion_root");
        public static final TagKey<Block> STORAGE_BLOCK_POPPY_SEEDS = storageTag("poppy_seeds");
        public static final TagKey<Block> STORAGE_BLOCK_BLACK_ACORNS = storageTag("black_acorns");

        public static TagKey<Block> blockTag(String namespace) {
            return TagUtil.blockTag(NaturesDelight.MOD_ID, namespace);
        }

        public static TagKey<Block> storageTag(String thing) {
            return TagUtil.blockTag("forge", "storage_blocks/" + thing);
        }
    }

    public static class BiomeTag {
        public static final TagKey<Biome> HAS_APPLE_TREES = hasFeature("apple_trees");
        public static final TagKey<Biome> HAS_ACORN_TREES = hasFeature("acorn_trees");

        private static TagKey<Biome> modTag(String namespace) {
            return TagUtil.biomeTag(NaturesDelight.MOD_ID, namespace);
        }

        private static TagKey<Biome> hasFeature(String feature) {
            return modTag("has_feature/" + feature);
        }

    }
}
