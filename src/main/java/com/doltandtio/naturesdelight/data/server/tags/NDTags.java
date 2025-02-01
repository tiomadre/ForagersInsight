package com.doltandtio.naturesdelight.data.server.tags;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public class NDTags {
    public static class ItemTag {
        public static final TagKey<net.minecraft.world.item.Item> ICE = TagUtil.itemTag("forge", "ice");

        private static TagKey<Item> modTag(String namespace) {
            return TagUtil.itemTag(NaturesDelight.MOD_ID, namespace);
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
