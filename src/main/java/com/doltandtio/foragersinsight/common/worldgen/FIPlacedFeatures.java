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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class FIPlacedFeatures {
    public static final ResourceKey<PlacedFeature> APPLE_TREE_PLACED_KEY = registerKey("apple_tree_placed");
    public static final ResourceKey<PlacedFeature> ACORN_DARK_OAK_TREE_PLACED_KEY = registerKey("acorn_dark_oak_placed");
    public static final ResourceKey<PlacedFeature> SPRUCE_TIP_TREE_PLACED_KEY = registerKey("spruce_tip_tree_placed");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ForagersInsight.rl(name));
    }

    public static void bootstap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, APPLE_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.APPLE_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.OAK_SAPLING));
        register(context, ACORN_DARK_OAK_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.ACORN_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.DARK_OAK_SAPLING));
        register(context, SPRUCE_TIP_TREE_PLACED_KEY, configuredFeatures.getOrThrow(FIConfiguredFeatures.SPRUCE_TIP_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1), Blocks.SPRUCE_SAPLING));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}