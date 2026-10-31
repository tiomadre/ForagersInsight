package com.tiomadre.foragersinsight.data.server.tags;

import com.tiomadre.foragersinsight.common.worldgen.FIBiomes;
import com.tiomadre.foragersinsight.core.ForagersInsight;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class FIBiomeTags extends BiomeTagsProvider {
    public FIBiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, provider, ForagersInsight.MOD_ID, helper);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider pProvider) {


        this.tag(BiomeTags.IS_OVERWORLD).add(FIBiomes.DARK_WOODLANDS);
        this.tag(BiomeTags.IS_FOREST).add(FIBiomes.DARK_WOODLANDS);
        this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(FIBiomes.DARK_WOODLANDS);
        this.tag(BiomeTags.HAS_TRIAL_CHAMBERS).add(FIBiomes.DARK_WOODLANDS);
        this.tag(Tags.Biomes.IS_TEMPERATE_OVERWORLD).add(FIBiomes.DARK_WOODLANDS);
        this.tag(Tags.Biomes.IS_DECIDUOUS_TREE).add(FIBiomes.DARK_WOODLANDS);


        //Flora
            //Trees
        this.tag(FITags.BiomeTag.HAS_APPLE_TREES).add(Biomes.FOREST, Biomes.FLOWER_FOREST);
        this.tag(FITags.BiomeTag.HAS_ACORN_TREES).add(Biomes.DARK_FOREST);
        this.tag(FITags.BiomeTag.HAS_LILAC_TREES).add(Biomes.FLOWER_FOREST);
        this.tag(FITags.BiomeTag.HAS_SPRUCE_TIP_TREES).add(Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA,Biomes.SNOWY_TAIGA);
        this.tag(FITags.BiomeTag.HAS_SAPPY_BIRCH_TREES).add(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST,Biomes.FOREST);
            //Plants
        this.tag(FITags.BiomeTag.HAS_ROSELLE_BUSHES).add(Biomes.SAVANNA,Biomes.SAVANNA_PLATEAU,Biomes.WINDSWEPT_SAVANNA);
        this.tag(FITags.BiomeTag.HAS_BEACH_ROSES).add(Biomes.BEACH);
        this.tag(FITags.BiomeTag.HAS_WOODLAND_FERNS).add(Biomes.FOREST, Biomes.DARK_FOREST, Biomes.BIRCH_FOREST,Biomes.OLD_GROWTH_BIRCH_FOREST,FIBiomes.DARK_WOODLANDS);

        //Biome Features
            //Suspicious Litter + Fallen Logs
        this.tag(FITags.BiomeTag.HAS_OAK_FOREST_LITTER).add(Biomes.FOREST);
        this.tag(FITags.BiomeTag.HAS_BIRCH_FOREST_LITTER).add(Biomes.BIRCH_FOREST,Biomes.OLD_GROWTH_BIRCH_FOREST);
        this.tag(FITags.BiomeTag.HAS_SPRUCE_FOREST_LITTER).add(Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        this.tag(FITags.BiomeTag.HAS_DARK_OAK_FOREST_LITTER).add(Biomes.DARK_FOREST).addOptional(ForagersInsight.rl("dark_woodlands"));
        this.tag(FITags.BiomeTag.HAS_FLOWER_FOREST_LITTER).add(Biomes.FLOWER_FOREST);
            //Puddles
        this.tag(FITags.BiomeTag.HAS_PUDDLES).add(Biomes.FOREST, Biomes.DARK_FOREST, Biomes.BIRCH_FOREST,Biomes.OLD_GROWTH_BIRCH_FOREST,Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA
        ,Biomes.FLOWER_FOREST, FIBiomes.DARK_WOODLANDS);

    }



}
