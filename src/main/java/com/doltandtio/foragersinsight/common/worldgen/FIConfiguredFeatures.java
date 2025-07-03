package com.doltandtio.foragersinsight.common.worldgen;

import com.doltandtio.foragersinsight.core.ForagersInsight;
import com.doltandtio.foragersinsight.core.registry.FIBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import com.doltandtio.foragersinsight.common.worldgen.trees.foliage.SpruceTipTreeFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.OptionalInt;
import java.util.function.Supplier;

public class FIConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_KEY = registerKey("apple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ACORN_TREE_KEY = registerKey("acorn_dark_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_TIP_TREE_KEY = registerKey("spruce_tip_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAPPY_BIRCH_TREE_KEY = registerKey("sappy_birch_tree");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ForagersInsight.rl(name));
    }

    public static void bootstap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, APPLE_TREE_KEY, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                bountifulLeafStateProvider(Blocks.OAK_LEAVES, FIBlocks.BOUNTIFUL_OAK_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)
        ).build());

        register(context, ACORN_TREE_KEY, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.DARK_OAK_LOG),
                new DarkOakTrunkPlacer(6, 2, 1),
                bountifulLeafStateProvider(Blocks.DARK_OAK_LEAVES, FIBlocks.BOUNTIFUL_DARK_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                new ThreeLayersFeatureSize(1, 1, 0, 1,2, OptionalInt.empty())
        ).ignoreVines().build());

        register(context, SPRUCE_TIP_TREE_KEY, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new StraightTrunkPlacer(5, 2, 1),
                bountifulLeafStateProvider(Blocks.SPRUCE_LEAVES, FIBlocks.BOUNTIFUL_SPRUCE_LEAVES),
                new SpruceTipTreeFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 3),
                new TwoLayersFeatureSize(2, 0, 2)
        ).build());

        register(context, SAPPY_BIRCH_TREE_KEY, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FIBlocks.SAPPY_BIRCH_LOG.get()),
                new StraightTrunkPlacer(5, 2, 0),
                BlockStateProvider.simple(Blocks.BIRCH_LEAVES),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                new TwoLayersFeatureSize(1, 0, 2)
        ).build());
    }


    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key,
                                                                                          FC configuration) {
        context.register(key, new ConfiguredFeature<>((F) Feature.TREE, configuration));
    }

    private static WeightedStateProvider bountifulLeafStateProvider(Block leaf, Supplier<Block> bountifulLeaf) {
        return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                .add(leaf.defaultBlockState(), 2)
                .add(bountifulLeaf.get().defaultBlockState(), 1));
    }

}