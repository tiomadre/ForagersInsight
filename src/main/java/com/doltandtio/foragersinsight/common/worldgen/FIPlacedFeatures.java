package com.doltandtio.foragersinsight.common.worldgen;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.tags.BiomeTags;

import java.util.List;

import vectorwing.farmersdelight.common.world.filter.BiomeTagFilter;

public class FIPlacedFeatures {
    public static final ResourceKey<PlacedFeature> APPLE_TREE_PLACED_KEY = registerKey("apple_tree_placed");
    public static final ResourceKey<PlacedFeature> ACORN_DARK_OAK_TREE_PLACED_KEY = registerKey("acorn_dark_oak_placed");
    public static final ResourceKey<PlacedFeature> SPRUCE_TIP_TREE_PLACED_KEY = registerKey("spruce_tip_tree_placed");
    public static final ResourceKey<PlacedFeature> SAPPY_BIRCH_TREE_PLACED_KEY = registerKey("sappy_birch_tree_placed");
    public static final ResourceKey<PlacedFeature> ROSELLE_PATCH_PLACED_KEY = registerKey("roselle_patch_placed");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ForagersInsight.rl(name));
    }

    public static void bootstap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        //Trees
        register(context, APPLE_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.APPLE_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.OAK_SAPLING));
        register(context, ACORN_DARK_OAK_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.ACORN_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.DARK_OAK_SAPLING));
        register(context, SPRUCE_TIP_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.SPRUCE_TIP_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.SPRUCE_SAPLING));
        register(context, SAPPY_BIRCH_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.SAPPY_BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.BIRCH_SAPLING));
        //Wild Flower
        register(context, ROSELLE_PATCH_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.ROSELLE_BUSH_PATCH_KEY), List.of(
            RarityFilter.onAverageOnceEvery(90), InSquarePlacement.spread(), HeightmapPlacement.
            onHeightmap(Heightmap.Types.MOTION_BLOCKING), BiomeFilter.biome(), BiomeTagFilter.biomeIsInTag(BiomeTags.IS_OVERWORLD)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
