package com.doltandtio.naturesdelight.data.server.tags;

import com.doltandtio.naturesdelight.core.NaturesDelight;
import com.teamabnormals.blueprint.core.util.TagUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NDTags {
    public static class ItemTag {
        public static final TagKey<net.minecraft.world.item.Item> ICE = TagUtil.itemTag("forge", "ice");

        public static TagKey<Item> modTag(String namespace) {
            return TagUtil.itemTag(NaturesDelight.MOD_ID, namespace);
        }
    }
}
