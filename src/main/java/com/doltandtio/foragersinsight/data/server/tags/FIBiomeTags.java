package com.doltandtio.foragersinsight.data.server.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public class FIBiomeTags extends BiomeTagsProvider {
    public FIBiomeTags(@NotNull GatherDataEvent e) {
        super(e.getGenerator().getPackOutput(), e.getLookupProvider());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(FITags.BiomeTag.HAS_APPLE_TREES).add(Biomes.FOREST, Biomes.FLOWER_FOREST);
        this.tag(FITags.BiomeTag.HAS_ACORN_TREES).add(Biomes.DARK_FOREST);
    }
}
