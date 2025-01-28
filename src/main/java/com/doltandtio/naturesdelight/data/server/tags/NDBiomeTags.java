package com.doltandtio.naturesdelight.data.server.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public class NDBiomeTags extends BiomeTagsProvider {
    public NDBiomeTags(@NotNull GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(NDTags.BiomeTag.HAS_APPLE_TREES).add(Biomes.FOREST, Biomes.FLOWER_FOREST);
    }
}
